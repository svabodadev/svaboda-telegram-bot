package com.svaboda.telegram.commands

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
            assertThrows<IllegalArgumentException> { Command(it, it) }
        }
    }

    @Test
    fun `should add prefix to command name`() {
        //given
        val prefix = "/"
        val name = "any-name"

        //when
        val result = Command(name, "any")

        //then
        assertThat(result.name()).isEqualTo("$prefix$name")
    }

}
