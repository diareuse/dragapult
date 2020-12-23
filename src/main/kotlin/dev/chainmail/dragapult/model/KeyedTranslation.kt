package dev.chainmail.dragapult.model

data class KeyedTranslation(
    val key: String,
    val translations: List<Translation>
)

infix fun String.with(translations: List<Translation>) =
    KeyedTranslation(this, translations)