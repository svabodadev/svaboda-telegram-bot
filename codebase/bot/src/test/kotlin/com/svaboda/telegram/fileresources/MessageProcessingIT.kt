package com.svaboda.telegram.fileresources

import com.svaboda.telegram.bot.MessageProcessor
import com.svaboda.telegram.commands.Commands
import com.svaboda.telegram.commands.CommandsConfiguration
import com.svaboda.telegram.commands.CommandsProperties
import com.svaboda.telegram.domain.ResourceProvider
import com.svaboda.telegram.fileresources.FileResourcesUtils.MAIN
import com.svaboda.telegram.fileresources.FileResourcesUtils.TEXTS_RESOURCE_PATH
import com.svaboda.telegram.fileresources.FileResourcesUtils.cyrillicCommand
import com.svaboda.telegram.fileresources.FileResourcesUtils.mainCommand
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

    private lateinit var commandsProperties: CommandsProperties
    private lateinit var commandsConfiguration: CommandsConfiguration
    private lateinit var commands: Commands

    private lateinit var fileResourcesProperties: FileResourcesProperties
    private lateinit var fileResourcesConfiguration: FileResourcesConfiguration
    private lateinit var resourceProvider: ResourceProvider<String>

    private lateinit var simpleMessageProcessor: MessageProcessor

    private lateinit var message: Message
    private lateinit var update: Update
    private lateinit var telegramBot: TelegramLongPollingBot

    @BeforeEach
    fun setup() {
        commandsConfiguration = CommandsConfiguration()
        commandsProperties = CommandsProperties(listOf(mainCommand(), cyrillicCommand()))
        commands = commandsConfiguration.commands(commandsProperties)

        fileResourcesProperties = FileResourcesProperties(TEXTS_RESOURCE_PATH)
        fileResourcesConfiguration = FileResourcesConfiguration()
        resourceProvider = CachedFileResourceProvider(TextFileResourceReader(TEXTS_RESOURCE_PATH))

        simpleMessageProcessor = SimpleMessageProcessor(resourceProvider, commands)

        message = Mockito.mock(Message::class.java)
        update = Mockito.mock(Update::class.java)
        telegramBot = Mockito.mock(TelegramLongPollingBot::class.java)
    }

    @Test
    fun `should successfully process message when no failure on underlying telegram dependency occurred`() {
        //given
        val command = mainCommand()
        val chatId = 1L
        val sendMessage = SendMessage(chatId, MAIN)
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
        val expectedMessageToSend = SendMessage(chatId, MAIN)
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
        val command = mainCommand()
        val chatId = 1L
        val sendMessage = SendMessage(chatId, MAIN)
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

}
