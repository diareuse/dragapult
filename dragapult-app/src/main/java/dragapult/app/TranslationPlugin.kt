package dragapult.app

interface TranslationPlugin {

    val priority: Int
    fun modify(key: TranslationKeyIR): TranslationKeyIR

    companion object {
        operator fun invoke(
            priority: Int = Int.MAX_VALUE / 2,
            body: (TranslationKeyIR) -> TranslationKeyIR
        ): TranslationPlugin = InlineTranslationPlugin(
            priority = priority,
            body = body
        )
    }

}

private class InlineTranslationPlugin(
    override val priority: Int,
    private val body: (TranslationKeyIR) -> TranslationKeyIR
) : TranslationPlugin {
    override fun modify(key: TranslationKeyIR) = body(key)
}