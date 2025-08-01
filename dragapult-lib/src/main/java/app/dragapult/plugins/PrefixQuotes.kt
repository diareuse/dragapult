package app.dragapult.plugins

import app.dragapult.FileKind
import app.dragapult.Platform
import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationPlugin
import app.dragapult.util.ensurePrefix
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class PrefixQuotes @Inject constructor() : TranslationPlugin {

    override fun modify(
        source: FileKind,
        target: FileKind,
        key: TranslationKeyIR
    ) {
        if (target !is Platform.Android)
            return
        for ((k, value) in key.translations) {
            key.translations[k] = value.ensurePrefix('\'')
        }
    }

}
