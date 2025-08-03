package app.dragapult

import app.dragapult.android.AndroidPreferences
import app.dragapult.apple.ApplePreferences
import app.dragapult.ir.csv.CsvIRPreferences
import app.dragapult.ir.json.JsonIRPreferences
import app.dragapult.ir.yaml.YamlIRPreferences
import java.util.*

interface Preferences {
    val android: AndroidPreferences
    val apple: ApplePreferences
    val csvIR: CsvIRPreferences
    val jsonIR: JsonIRPreferences
    val yamlIR: YamlIRPreferences

    companion object {
        fun static(
            android: AndroidPreferences = android(),
            apple: ApplePreferences = apple(),
            csvIR: CsvIRPreferences = csvIR(),
            jsonIR: JsonIRPreferences = jsonIR(),
            yamlIR: YamlIRPreferences = yamlIR()
        ): Preferences = PreferencesStatic(
            android = android,
            apple = apple,
            csvIR = csvIR,
            jsonIR = jsonIR,
            yamlIR = yamlIR
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

        fun jsonIR(
            prettyPrint: Boolean? = null,
            prettyPrintIndent: String? = null,
            explicitNulls: Boolean? = null
        ): JsonIRPreferences = PreferencesStatic.JsonIR(
            prettyPrint = prettyPrint,
            prettyPrintIndent = prettyPrintIndent,
            explicitNulls = explicitNulls
        )

        fun yamlIR(): YamlIRPreferences = PreferencesStatic.YamlIR()
    }
}