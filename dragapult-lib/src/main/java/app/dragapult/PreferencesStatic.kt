package app.dragapult

import app.dragapult.android.AndroidPreferences
import app.dragapult.apple.ApplePreferences
import java.util.*

internal class PreferencesStatic(
    override val android: AndroidPreferences,
    override val apple: ApplePreferences
) : Preferences {

    class Android(
        defaultLocale: Locale?,
        outputFileName: String?,
        setDefaultLocaleExplicitly: Boolean?
    ) : AndroidPreferences {
        override val defaultLocale = defaultLocale ?: super.defaultLocale
        override val outputFileName = outputFileName ?: super.outputFileName
        override val setDefaultLocaleExplicitly = setDefaultLocaleExplicitly ?: super.setDefaultLocaleExplicitly
    }

    class Apple(
        outputFileName: String?,
    ) : ApplePreferences {
        override val outputFileName = outputFileName ?: super.outputFileName
    }

}