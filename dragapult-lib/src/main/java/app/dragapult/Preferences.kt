package app.dragapult

import app.dragapult.android.AndroidPreferences
import app.dragapult.apple.ApplePreferences
import app.dragapult.ir.csv.CsvIRPreferences
import java.util.*

interface Preferences {
    val android: AndroidPreferences
    val apple: ApplePreferences
    val csvIR: CsvIRPreferences

    companion object {
        fun static(
            android: AndroidPreferences = android(),
            apple: ApplePreferences = apple(),
            csvIR: CsvIRPreferences = csvIR()
        ): Preferences = PreferencesStatic(
            android = android,
            apple = apple,
            csvIR = csvIR
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

        fun csvIR(
            commentMarker: Char? = null,
            recordSeparator: String? = null
        ): CsvIRPreferences = PreferencesStatic.CsvIR(
            commentMarker = commentMarker,
            recordSeparator = recordSeparator
        )
    }
}