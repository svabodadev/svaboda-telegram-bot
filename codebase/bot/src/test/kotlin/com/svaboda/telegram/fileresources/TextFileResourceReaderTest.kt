package com.svaboda.telegram.fileresources

import com.svaboda.telegram.commands.CommandTestUtils.cyrillicCommand
import com.svaboda.telegram.domain.ResourcesProperties
import com.svaboda.telegram.fileresources.FileResourcesUtils.GO_TO_ARTICLE_LINE
import com.svaboda.telegram.fileresources.FileResourcesUtils.HEADER
import com.svaboda.telegram.fileresources.FileResourcesUtils.MAX_RESOURCE_SIZE
import com.svaboda.telegram.fileresources.FileResourcesUtils.TEXTS_FILE_EXTENSION
import com.svaboda.telegram.fileresources.FileResourcesUtils.TOPICS_ENRICHMENT_LINE
import com.svaboda.telegram.fileresources.FileResourcesUtils.cyrillicContent
import com.svaboda.telegram.fileresources.FileResourcesUtils.resourceProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TextFileResourceReaderTest {

    @Test
    fun `should properly read text file with cyrillic content`() {
        //given
        val resourcesProperties = resourceProperties()
        val filename = cyrillicCommand().name()
        val expectedOutput = cyrillicContent()
        val textFileResourceReader = TextFileResourceReader(resourcesProperties)

        //when
        val result = textFileResourceReader.readFrom(filename).get()

        //then
        assertThat(result).isEqualTo(expectedOutput)
    }

    @Test
    fun `should return failure when error occurred on reading file`() {
        //given
        val invalidPath = "invalid/"
        val invalidProperties = ResourcesProperties(invalidPath, TEXTS_FILE_EXTENSION, MAX_RESOURCE_SIZE,
                TOPICS_ENRICHMENT_LINE, GO_TO_ARTICLE_LINE, HEADER)
        val filename = cyrillicCommand().name()
        val textFileResourceReader = TextFileResourceReader(invalidProperties)

        //when
        val result = textFileResourceReader.readFrom(filename)

        //then
        assertThat(result.isFailure).isTrue()
    }

}