package com.svaboda.telegram.fileresources

import com.svaboda.telegram.commands.CommandTestUtils.commandsProperties
import com.svaboda.telegram.commands.CommandTestUtils.hugeCommand
import com.svaboda.telegram.commands.CommandTestUtils.noWhitespaceCommand
import com.svaboda.telegram.domain.ResourceTransformer
import com.svaboda.telegram.fileresources.FileResourcesUtils.MIN_RESOURCE_SIZE
import com.svaboda.telegram.fileresources.FileResourcesUtils.resourceProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TextTransformerSpec {

    private val resourcesProperties = resourceProperties()
    private val commandsProperties = commandsProperties()
    private val resourceProvider = TextFileResourceReader(resourcesProperties)

    private lateinit var textTransformer: ResourceTransformer<String>

    @Test
    fun `should cut text on new line according to max size property`() {
        //given
        textTransformer = TextTransformer(resourcesProperties, commandsProperties)
        val hugeCommand = hugeCommand()
        val resource = resourceProvider.readFrom(hugeCommand.resourceId()).get()

        //when
        val result = textTransformer.asContent(hugeCommand, resource).get()

        //then
        assertThat(result.length).isLessThan(resourcesProperties.maxResourceSize())
        assertThat(result.length).isGreaterThanOrEqualTo(MIN_RESOURCE_SIZE)
    }

    @Test
    fun `should cut text without any whitespace according to max size property`() {
        //given
        textTransformer = TextTransformer(resourcesProperties, commandsProperties)
        val noWhitespaceCommand = noWhitespaceCommand()
        val resource = resourceProvider.readFrom(noWhitespaceCommand.resourceId()).get()

        //when
        val result = textTransformer.asContent(noWhitespaceCommand, resource).get()

        //then
        assertThat(result.length).isEqualTo(resourcesProperties.maxResourceSize())
    }

}