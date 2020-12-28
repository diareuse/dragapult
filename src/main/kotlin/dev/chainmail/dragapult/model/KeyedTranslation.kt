package dev.chainmail.dragapult.model

@Deprecated("")
data class KeyedTranslation(
    val key: String,
    val translations: List<Translation>
)

@Deprecated("")
infix fun String.with(translations: List<Translation>) =
    KeyedTranslation(this, translations)