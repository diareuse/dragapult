package app.dragapult

interface TranslationWriter : AutoCloseable {
    fun append(ir: TranslationKeyIR)
}