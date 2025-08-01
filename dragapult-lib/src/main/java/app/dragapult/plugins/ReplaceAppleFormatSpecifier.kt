package app.dragapult.plugins

import app.dragapult.FileKind
import app.dragapult.Platform
import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationPlugin
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ReplaceAppleFormatSpecifier @Inject constructor() : TranslationPlugin {

    override fun modify(
        source: FileKind,
        target: FileKind,
        key: TranslationKeyIR
    ) {
        for ((k, v) in key.translations) {
            key.translations[k] = when (target) {
                is Platform.Android -> v.replace("%@", "%s")
                is Platform.Apple -> v.replace("%s", "%@")
                else -> v
            }
        }
    }

}