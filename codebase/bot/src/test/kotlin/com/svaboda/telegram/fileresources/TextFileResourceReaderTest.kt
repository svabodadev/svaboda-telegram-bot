package com.svaboda.telegram.fileresources

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TextFileResourceReaderTest {

    @Test
    fun `should properly read text file with cyrillic content`() {
        //given
        val resourcePath = "texts/"
        val filename = "cyrillic"
        val expectedOutput = "Ада́м Берна́рд Мицке́вич (польск. Adam Bernard Mickiewicz, белор. Адам Бернард Міцкевіч; 24 декабря 1798, фольварк Заосье, Новогрудский уезд, Виленская губерния, Российская империя — 26 ноября 1855, Константинополь, Османская империя) — польский[7] поэт белорусского происхождения[8][9], политический публицист, деятель польского национального движения, член общества филоматов. Оказал большое влияние на становление польской и белорусской литературы в XIX века[10]. В Польше считается одним из трёх величайших польских поэтов эпохи романтизма (наряду с Юлиушем Словацким и Зигмунтом Красинским). Некоторыми из белорусских литераторов Мицкевич выделяется как один из родоначальников новой белорусской литературы[11] и белорусским польскоговорящим поэтом[12]."
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