package dragapult.app

interface TranslationWriter : AutoCloseable {
    fun append(ir: TranslationKeyIR)
}