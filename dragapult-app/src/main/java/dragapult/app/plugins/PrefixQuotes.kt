package dragapult.app.plugins

import dragapult.app.FileKind
import dragapult.app.Platform
import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationPlugin
import dragapult.app.util.ensurePrefix
import javax.inject.Inject
import javax.inject.Singleton

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
