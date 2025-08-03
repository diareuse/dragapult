package app.dragapult

import app.dragapult.android.AndroidPreferences
import app.dragapult.apple.ApplePreferences
import java.util.*

interface Preferences {
    val android: AndroidPreferences
    val apple: ApplePreferences

    companion object {
        fun static(
            android: AndroidPreferences = android(),
            apple: ApplePreferences = apple()
        ): Preferences = PreferencesStatic(
            android = android,
            apple = apple()
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

        fun apple(
            outputFileName: String? = null,
        ): ApplePreferences = PreferencesStatic.Apple(
            outputFileName = outputFileName
        )
    }
}