package com.svaboda.telegram.fileresources

import com.svaboda.telegram.bot.MessageProcessor
import com.svaboda.telegram.commands.CommandTestUtils.cyrillicCommand
import com.svaboda.telegram.commands.CommandTestUtils.topicsCommand
import com.svaboda.telegram.commands.CommandsConfiguration
import com.svaboda.telegram.commands.CommandsProperties
import com.svaboda.telegram.domain.ResourcesProperties
import com.svaboda.telegram.fileresources.FileResourcesUtils.resourceProperties
import com.svaboda.telegram.fileresources.FileResourcesUtils.topicsContent
import com.svaboda.telegram.statistics.Statistics
import com.svaboda.telegram.statistics.StatisticsConfiguration
import com.svaboda.telegram.statistics.StatisticsHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class MessageProcessingIT {

    private lateinit var statisticsConfiguration: StatisticsConfiguration
    private lateinit var statisticsHandler: StatisticsHandler

    private lateinit var simpleMessageProcessor: MessageProcessor

    private lateinit var message: Message
    private lateinit var update: Update
    private lateinit var telegramBot: TelegramLongPollingBot

    @BeforeEach
    fun setup() {
        val commandsProperties = CommandsProperties(listOf(topicsCommand(), cyrillicCommand()))
        val commands = CommandsConfiguration().commands(commandsProperties)
        val resourceProperties: ResourcesProperties = resourceProperties()
        val resourceProvider = CachedFileResourceProvider(
                TextFileResourceReader(resourceProperties), TextTransformer(resourceProperties, commandsProperties)
        )
        statisticsConfiguration = StatisticsConfiguration()
        statisticsHandler = statisticsConfiguration.statisticsHandler()
        simpleMessageProcessor = SimpleMessageProcessor(resourceProvider, statisticsHandler, commands)

        message = Mockito.mock(Message::class.java)
        update = Mockito.mock(Update::class.java)
        telegramBot = Mockito.mock(TelegramLongPollingBot::class.java)
    }

    @Test
    fun `should successfully process message when no failure on underlying telegram dependency occurred`() {
        //given
        val command = topicsCommand()
        val chatId = 1L
        val sendMessage = SendMessage(chatId, topicsContent())
        Mockito.`when`(message.text).thenReturn(command.name())
        Mockito.`when`(message.chatId).thenReturn(chatId)
        Mockito.`when`(update.message).thenReturn(message)
        Mockito.`when`(telegramBot.execute(sendMessage)).thenReturn(Mockito.mock(Message::class.java))

        //when
        val result = simpleMessageProcessor.processWith(update, telegramBot)

        //then
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `should return default resource when asked with unknown command`() {
        //given
        val commandName = "unknown"
        val chatId = 1L
        val expectedMessageToSend = SendMessage(chatId, topicsContent())
        Mockito.`when`(message.text).thenReturn(commandName)
        Mockito.`when`(message.chatId).thenReturn(chatId)
        Mockito.`when`(update.message).thenReturn(message)

        //when
        simpleMessageProcessor.processWith(update, telegramBot)

        //then
        Mockito.verify(telegramBot, Mockito.times(1)).execute(expectedMessageToSend)
    }

    @Test
    fun `should handle underlying telegram failure response`() {
        //given
        val command = topicsCommand()
        val chatId = 1L
        val sendMessage = SendMessage(chatId, topicsContent())
        val terribleError = TelegramApiException("Boom!")
        Mockito.`when`(message.text).thenReturn(command.name())
        Mockito.`when`(message.chatId).thenReturn(chatId)
        Mockito.`when`(update.message).thenReturn(message)
        Mockito.`when`(telegramBot.execute(sendMessage)).thenThrow(terribleError)

        //when
        val result = simpleMessageProcessor.processWith(update, telegramBot)

        //then
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `should store statistics while processing messages`() {
        //given
        val command = topicsCommand()
        val chatId = 1L
        val sendMessage = SendMessage(chatId, topicsContent())
        Mockito.`when`(message.text).thenReturn(command.name())
        Mockito.`when`(message.chatId).thenReturn(chatId)
        Mockito.`when`(update.message).thenReturn(message)
        Mockito.`when`(telegramBot.execute(sendMessage)).thenReturn(Mockito.mock(Message::class.java))
        val callsNumber = 10
        for (call in 1..callsNumber) { simpleMessageProcessor.processWith(update, telegramBot) }
        val expectedStatistic = Statistics.CommandCallCount(command.name(), callsNumber.toLong())

        //when
        val result = statisticsHandler.provide().get()

        //then
        assertThat(result.statistics().size).isOne()
        assertThat(result.statistics().first()).isEqualTo(expectedStatistic)
        assertThat(result.uniqueChats()).isOne()
    }

}
