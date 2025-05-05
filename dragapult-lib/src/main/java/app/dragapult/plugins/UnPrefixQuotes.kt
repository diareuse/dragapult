package app.dragapult.plugins

import app.dragapult.FileKind
import app.dragapult.Platform
import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationPlugin
import app.dragapult.util.removePrefix
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnPrefixQuotes @Inject constructor() : TranslationPlugin {

    override fun modify(
        source: FileKind,
        target: FileKind,
        key: TranslationKeyIR
    ) {
        if (source !is Platform.Android)
            return
        for ((k, value) in key.translations) {
            key.translations[k] = value.removePrefix('\'')
        }
    }

}