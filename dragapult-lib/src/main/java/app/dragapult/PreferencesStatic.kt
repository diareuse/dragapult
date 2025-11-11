package app.dragapult

import app.dragapult.android.AndroidPreferences
import app.dragapult.apple.ApplePreferences
import app.dragapult.ir.csv.CsvIRPreferences
import app.dragapult.ir.json.JsonIRPreferences
import app.dragapult.ir.yaml.YamlIRPreferences
import app.dragapult.json.JsonPreferences
import app.dragapult.unity.UnityPreferences
import java.util.*

internal class PreferencesStatic(
    override val android: AndroidPreferences,
    override val apple: ApplePreferences,
    override val csvIR: CsvIRPreferences,
    override val jsonIR: JsonIRPreferences,
    override val yamlIR: YamlIRPreferences,
    override val json: JsonPreferences,
    override val unity: UnityPreferences,
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

    class JsonIR(
        prettyPrint: Boolean?,
        prettyPrintIndent: String?,
        explicitNulls: Boolean?
    ) : JsonIRPreferences {
        override val prettyPrint = prettyPrint ?: super.prettyPrint
        override val prettyPrintIndent = prettyPrintIndent ?: super.prettyPrintIndent
        override val explicitNulls = explicitNulls ?: super.explicitNulls
    }

    class YamlIR : YamlIRPreferences

    class Json(
        explicitNulls: Boolean?,
        isLenient: Boolean?,
        outputFileName: String?
    ) : JsonPreferences {
        override val explicitNulls = explicitNulls ?: super.explicitNulls
        override val isLenient = isLenient ?: super.isLenient
        override val outputFileName = outputFileName ?: super.outputFileName
    }

    class Unity(
        sharedCommentsLabel: String?,
        commentsLabel: String?,
        propertiesLabel: String?,
        keyLabel: String?,
        outputFileName: String?,
    ) : UnityPreferences {
        override val sharedCommentsLabel = sharedCommentsLabel ?: super.sharedCommentsLabel
        override val commentsLabel = commentsLabel ?: super.commentsLabel
        override val propertiesLabel = propertiesLabel ?: super.propertiesLabel
        override val keyLabel = keyLabel ?: super.keyLabel
        override val outputFileName = outputFileName ?: super.outputFileName
    }

}