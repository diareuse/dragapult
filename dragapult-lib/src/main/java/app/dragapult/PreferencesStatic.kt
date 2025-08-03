package app.dragapult

import app.dragapult.android.AndroidPreferences
import java.util.*

internal class PreferencesStatic(
    override val android: AndroidPreferences
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

}