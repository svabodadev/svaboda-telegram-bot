package com.svaboda.telegram.fileresources

import com.svaboda.telegram.fileresources.FileResourcesUtils.CYRILLIC
import com.svaboda.telegram.fileresources.FileResourcesUtils.TEXTS_RESOURCE_PATH
import com.svaboda.telegram.fileresources.FileResourcesUtils.cyrillicCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TextFileResourceReaderTest {

    @Test
    fun `should properly read text file with cyrillic content`() {
        //given
        val filename = cyrillicCommand().name()
        val expectedOutput = CYRILLIC
        val textFileResourceReader = TextFileResourceReader(TEXTS_RESOURCE_PATH)

        //when
        val result = textFileResourceReader.readFrom(filename).get()

        //then
        assertThat(result).isEqualTo(expectedOutput)
    }

    @Test
    fun `should return failure when error occurred on reading file`() {
        //given
        val invalidPath = "invalid/"
        val filename = cyrillicCommand().name()
        val textFileResourceReader = TextFileResourceReader(invalidPath)

        //when
        val result = textFileResourceReader.readFrom(filename)

        //then
        assertThat(result.isFailure).isTrue()
    }

}