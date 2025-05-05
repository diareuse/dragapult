package app.dragapult

interface TranslationPlugin {

    val priority: Int get() = Int.MAX_VALUE / 2
    fun modify(
        source: FileKind,
        target: FileKind,
        key: TranslationKeyIR
    )

    companion object {
        operator fun invoke(
            priority: Int = Int.MAX_VALUE / 2,
            body: (FileKind, FileKind, TranslationKeyIR) -> Unit
        ): TranslationPlugin = InlineTranslationPlugin(
            priority = priority,
            body = body
        )
    }

}

private class InlineTranslationPlugin(
    override val priority: Int,
    private val body: (FileKind, FileKind, TranslationKeyIR) -> Unit
) : TranslationPlugin {
    override fun modify(
        source: FileKind,
        target: FileKind,
        key: TranslationKeyIR
    ) {
        body(source, target, key)
    }
}