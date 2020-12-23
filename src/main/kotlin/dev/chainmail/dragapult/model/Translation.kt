package dev.chainmail.dragapult.model

data class Translation(
    val language: String,
    val translation: String
) {

    val isComment
        inline get() = language.equals("comment", ignoreCase = false)

}
