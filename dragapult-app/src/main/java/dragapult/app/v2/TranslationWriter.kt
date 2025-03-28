package dragapult.app.v2

interface TranslationWriter : AutoCloseable {
    fun append(ir: TranslationKeyIR)
}