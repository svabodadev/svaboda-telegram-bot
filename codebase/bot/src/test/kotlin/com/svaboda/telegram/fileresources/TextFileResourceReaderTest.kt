package com.svaboda.telegram.fileresources

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TextFileResourceReaderTest {

    @Test
    fun `should properly read text file with cyrillic content`() {
        //given
        val resourcePath = "texts/"
        val filename = "cyrillic"
        val expectedOutput = "source=https://ru.wikipedia.org/wiki/%D0%9C%D0%B8%D1%86%D0%BA%D0%B5%D0%B2%D0%B8%D1%87,_%D0%90%D0%B4%D0%B0%D0%BC деятель польского национального движения, член общества филоматов. Оказал большое влияние на становление польской и белорусской литературы в XIX века[10]. В Польше считается одним из трёх величайших польских поэтов эпохи романтизма (наряду с Юлиушем Словацким и Зигмунтом Красинским). Некоторыми из белорусских литераторов Мицкевич выделяется как один из родоначальников новой белорусской литературы[11] и белорусским польскоговорящим поэтом[12]."
        val textFileResourceReader = TextFileResourceReader(resourcePath)

        //when
        val result = textFileResourceReader.readFrom(filename).get()

        //then
        assertThat(result).isEqualTo(expectedOutput)
    }

    @Test
    fun `should return failure when error occurred on reading file`() {
        //given
        val invalidPath = "invalid/"
        val filename = "cyrillic"
        val textFileResourceReader = TextFileResourceReader(invalidPath)

        //when
        val result = textFileResourceReader.readFrom(filename)

        //then
        assertThat(result.isFailure).isTrue()
    }

}