package app.dragapult.plugins

import app.dragapult.FileKind
import app.dragapult.Platform
import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationPlugin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefixAmpersand @Inject constructor() : TranslationPlugin {

    override fun modify(
        source: FileKind,
        target: FileKind,
        key: TranslationKeyIR
    ) {
        for ((k, v) in key.translations) {
            key.translations[k] = when (target) {
                is Platform.Android -> v.replace("&", "\\u0026")
                else -> v.replace("\\u0026", "&")
            }
        }
    }

}