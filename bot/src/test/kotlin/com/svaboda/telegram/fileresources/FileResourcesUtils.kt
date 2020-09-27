package com.svaboda.telegram.fileresources

import com.svaboda.telegram.commands.Command

object FileResourcesUtils {

    const val TEXTS_RESOURCE_PATH = "texts/"
    const val CYRILLIC = "source=https://ru.wikipedia.org/wiki/%D0%9C%D0%B8%D1%86%D0%BA%D0%B5%D0%B2%D0%B8%D1%87,_%D0%90%D0%B4%D0%B0%D0%BC деятель польского национального движения, член общества филоматов. Оказал большое влияние на становление польской и белорусской литературы в XIX века[10]. В Польше считается одним из трёх величайших польских поэтов эпохи романтизма (наряду с Юлиушем Словацким и Зигмунтом Красинским). Некоторыми из белорусских литераторов Мицкевич выделяется как один из родоначальников новой белорусской литературы[11] и белорусским польскоговорящим поэтом[12]."
    const val MAIN = """test test
test testtest test
test testtest testtest test

test testtest testtest testtest test ///
test testtest test
...

"""
    fun mainCommand() = Command("main", "main")
    fun cyrillicCommand() = Command("cyrillic", "cyrillic")
}