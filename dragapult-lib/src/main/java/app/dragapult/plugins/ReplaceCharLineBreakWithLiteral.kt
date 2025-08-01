package app.dragapult.plugins

import app.dragapult.FileKind
import app.dragapult.Platform
import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationPlugin
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ReplaceCharLineBreakWithLiteral @Inject constructor() : TranslationPlugin {
    override fun modify(
        source: FileKind,
        target: FileKind,
        key: TranslationKeyIR
    ) {
        if (target !is Platform.Apple)
            return
        for ((k, v) in key.translations) {
            key.translations[k] = v.replace("\n", "\\n")
        }
    }
}