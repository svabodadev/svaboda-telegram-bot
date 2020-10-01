package com.svaboda.telegram.commands

object CommandTestUtils {

    const val ANY_EXTERNAL_LINK = "https://docs.google.com/document/d/1-r8kf1zSx1U9RjKIQEfAdL_TKsltG8dXFleMcU9lxbY/edit"

    fun topicsCommand(): Command = Command.TOPICS_INSTANCE
    fun cyrillicCommand(): Command = Command("cyrillic", "cyrillic", ANY_EXTERNAL_LINK)

}