package app.dragapult

import app.dragapult.android.AndroidPreferences
import app.dragapult.apple.ApplePreferences
import app.dragapult.ir.csv.CsvIRPreferences
import java.util.*

internal class PreferencesStatic(
    override val android: AndroidPreferences,
    override val apple: ApplePreferences,
    override val csvIR: CsvIRPreferences,
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

    class CsvIR(
        commentMarker: Char?,
        recordSeparator: String?
    ) : CsvIRPreferences {
        override val commentMarker = commentMarker ?: super.commentMarker
        override val recordSeparator = recordSeparator ?: super.recordSeparator
    }

}