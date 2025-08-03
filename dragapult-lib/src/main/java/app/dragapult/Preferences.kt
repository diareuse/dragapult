package app.dragapult

import app.dragapult.android.AndroidPreferences
import java.util.*

interface Preferences {
    val android: AndroidPreferences

    companion object {
        fun static(
            android: AndroidPreferences = android()
        ): Preferences = PreferencesStatic(
            android = android
        )

        fun android(
            defaultLocale: Locale? = null,
            outputFileName: String? = null,
            setDefaultLocaleExplicitly: Boolean? = null
        ): AndroidPreferences = PreferencesStatic.Android(
            defaultLocale = defaultLocale,
            outputFileName = outputFileName,
            setDefaultLocaleExplicitly = setDefaultLocaleExplicitly
        )
    }
}