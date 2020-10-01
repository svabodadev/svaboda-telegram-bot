package com.svaboda.telegram.fileresources

import com.svaboda.telegram.fileresources.FileResourcesUtils.TEXTS_FILE_EXTENSION
import com.svaboda.telegram.fileresources.FileResourcesUtils.TEXTS_PATH
import com.svaboda.telegram.fileresources.FileResourcesUtils.cyrillicCommand
import com.svaboda.telegram.fileresources.FileResourcesUtils.cyrillicContent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TextFileResourceReaderTest {

    @Test
    fun `should properly read text file with cyrillic content`() {
        //given
        val fileResourcesProperties = FileResourcesProperties(TEXTS_PATH, TEXTS_FILE_EXTENSION)
        val filename = cyrillicCommand().name()
        val expectedOutput = cyrillicContent()
        val textFileResourceReader = TextFileResourceReader(fileResourcesProperties)

        //when
        val result = textFileResourceReader.readFrom(filename).get()

        //then
        assertThat(result).isEqualTo(expectedOutput)
    }

    @Test
    fun `should return failure when error occurred on reading file`() {
        //given
        val invalidPath = "invalid/"
        val invalidProperties = FileResourcesProperties(invalidPath, TEXTS_FILE_EXTENSION)
        val filename = cyrillicCommand().name()
        val textFileResourceReader = TextFileResourceReader(invalidProperties)

        //when
        val result = textFileResourceReader.readFrom(filename)

        //then
        assertThat(result.isFailure).isTrue()
    }

}