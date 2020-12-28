package dev.chainmail.dragapult.model

data class Translation(
    val key: String,
    val language: String,
    private val value: String
) {

    val translation
        get() = value
            .replace("\n", "\\n")
            .replace("%@", "%s")

    val isComment
        inline get() = language.equals("comment", ignoreCase = false)

}
