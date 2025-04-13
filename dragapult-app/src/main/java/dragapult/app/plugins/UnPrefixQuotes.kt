package dragapult.app.plugins

import dragapult.app.FileKind
import dragapult.app.Platform
import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationPlugin
import dragapult.app.util.removePrefix
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