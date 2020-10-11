package com.svaboda.telegram.statistics

import com.svaboda.telegram.commands.Command
import com.svaboda.telegram.commands.CommandTestUtils.commandsProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CachedStatisticsTest {

    private lateinit var cachedStatistics: CachedStatistics

    @BeforeEach
    fun setup() {
        cachedStatistics = CachedStatistics()
    }

    @Test
    fun `should return empty statistics when there was no calls against any command`() {
        //given

        //when
        val result = cachedStatistics.provide().get()

        //then
        assertThat(result.statistics()).isEmpty()
        assertThat(result.uniqueChats()).isZero()
    }

    @Test
    fun `should collect statistics when there was call against command`() {
        //given
        val chatId = 1L
        val command = Command.TOPICS_INSTANCE
        cachedStatistics.register(command, chatId)

        //when
        val result = cachedStatistics.provide().get()

        //then
        assertThat(result.statistics().size).isEqualTo(1)
        assertThat(result.statistics().first().command()).isEqualTo(command.name())
        assertThat(result.statistics().first().hitCount()).isEqualTo(1)
        assertThat(result.uniqueChats()).isOne()
    }

    @Test
    fun `should return proper statistics when there were multiple calls against commands`() {
        //given
        val chatId = 1L
        val commands = commandsProperties().commands()
        val commandsCount = mutableMapOf<Command,Int>()
        val random = Random()
        val maxCalls = 100
        commands.forEach { commandsCount[it] = random.nextInt(maxCalls) }
        commandsCount.forEach { (command, count) ->
            for(call in 1..count) { cachedStatistics.register(command, chatId) }
        }

        //when
        val result = cachedStatistics.provide().get()

        //then
        commandsCount.forEach { (command, count) ->
            val statThatShouldBeInResult = Statistics.CommandCallCount(command.name(), count.toLong())
            assertThat(result.statistics()).contains(statThatShouldBeInResult)
            assertThat(result.uniqueChats()).isOne()
        }
    }

    @Test
    fun `should collect statistics for multiple chats`() {
        //given
        val chats = listOf(1L, 2L, 1L, 3L, 1L, 3L)
        val command = Command.TOPICS_INSTANCE
        chats.forEach { chatId ->
            for(call in 1..10) { cachedStatistics.register(command, chatId) }
        }
        val expectedResult = 3

        //when
        val result = cachedStatistics.provide().get()

        //then
        assertThat(result.uniqueChats()).isEqualTo(expectedResult)
    }
}