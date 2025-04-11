package dragapult.app

interface TranslationPlugin {
    val priority: Int
    fun modify(key: TranslationKeyIR): TranslationKeyIR
}