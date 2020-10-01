package com.svaboda.telegram.commands

import com.svaboda.telegram.commands.CommandTestUtils.ANY_EXTERNAL_LINK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CommandTest {

    @Test
    fun `should not create command with empty values`() {
        //given
        val invalidParams = listOf(null, "", " ")

        //then
        invalidParams.forEach {
            assertThrows<IllegalArgumentException> { Command(it, it, it) }
        }
    }

    @Test
    fun `should add prefix to command name when prefix is missing`() {
        //given
        val prefix = "/"
        val name = "any-name"

        //when
        val result = Command(name, "any", ANY_EXTERNAL_LINK)

        //then
        assertThat(result.name()).isEqualTo("$prefix$name")
    }

    @Test
    fun `should not add additional prefix to command name when prefix is present`() {
        //given
        val prefixedName = "/any-name"

        //when
        val result = Command(prefixedName, "any", ANY_EXTERNAL_LINK)

        //then
        assertThat(result.name()).isEqualTo(prefixedName)
    }

    @Test
    fun `should return false when command is not topics command`() {
        //given
        val command = Command("any", "any", ANY_EXTERNAL_LINK)

        //when
        val result = command.isTopicsCommand

        //then
        assertThat(result).isFalse()
    }

}
