package dragapult.app.plugins

import dragapult.app.FileKind
import dragapult.app.Platform
import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationPlugin
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

