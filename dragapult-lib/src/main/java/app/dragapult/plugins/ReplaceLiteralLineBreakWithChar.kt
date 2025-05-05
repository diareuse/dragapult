package app.dragapult.plugins

import app.dragapult.FileKind
import app.dragapult.Platform
import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationPlugin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReplaceLiteralLineBreakWithChar @Inject constructor() : TranslationPlugin {
    override fun modify(
        source: FileKind,
        target: FileKind,
        key: TranslationKeyIR
    ) {
        if (source !is Platform.Apple)
            return
        for ((k, v) in key.translations) {
            key.translations[k] = v.replace("\\n", "\n")
        }
    }
}

