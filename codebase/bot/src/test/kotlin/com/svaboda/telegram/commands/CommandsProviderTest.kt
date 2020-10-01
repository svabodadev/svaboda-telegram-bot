package com.svaboda.telegram.commands

import com.svaboda.telegram.commands.Commands.TOPICS_COMMAND_NAME
import com.svaboda.telegram.commands.Commands.TOPICS_COMMAND_RESOURCE_ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CommandsProviderTest {

    @Test
    fun `should return default command with proper values`() {
        //given
        val commandsProvider = CommandsContainer(emptyList())
        val expectedResult = Command(TOPICS_COMMAND_NAME, TOPICS_COMMAND_RESOURCE_ID)


        //when
        val defaultCommand = commandsProvider.topicsCommand()

        //then
        assertThat(defaultCommand).isEqualTo(expectedResult)
    }

    @Test
    fun `should return default command when no commands provided on creation`() {
        //given
        val commandsProvider = CommandsContainer(emptyList())
        val defaultCommand = commandsProvider.topicsCommand()

        //when
        val command = commandsProvider.byName(defaultCommand.name())

        //then
        assertThat(command).isEqualTo(defaultCommand)
    }

    @Test
    fun `should return all commands provided on creation`() {
        // given
        val commands = listOf(
                Command("first", "anyFirst"),
                Command("second", "any-second"),
                Command("third", "any-third")
        )

        //when
        val result = CommandsContainer(commands)

        //then
        commands.forEach {
            assertThat(result.byName(it.name())).isEqualTo(it)
        }
    }
}