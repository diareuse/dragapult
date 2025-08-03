package app.dragapult.unity

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.util.*

class ReaderUnity(
    dir: File,
    format: CSVFormat,
    private val prefs: UnityPreferences
) : TranslationReader {

    private val data by lazy {
        val data = mutableListOf<TranslationKeyIR>()
        val files = dir.walk().filter { it.isFile }.filter { it.extension == "csv" }
        for (file in files) {
            file.inputStream().use { input ->
                format.parse(input.reader()).forEach { record ->
                    data += TranslationKeyIR(record.get(0)).apply {
                        metadata.comment = record.getOrNull(prefs.sharedCommentsLabel)?.takeUnless { it.isBlank() }
                        record.getOrNull(prefs.propertiesLabel)
                            ?.split("\n")
                            ?.filter { it.isNotBlank() }
                            ?.associate {
                                val pair = it.split("=", limit = 2)
                                pair[0] to pair.getOrNull(1).orEmpty()
                            }
                            ?.takeUnless { it.isEmpty() }
                            ?.toMap(metadata.properties)
                        record.toMap().toMutableMap()
                            .apply {
                                remove(prefs.keyLabel)
                                remove(record.parser.headerNames.find { it.lowercase().endsWith(prefs.commentsLabel) })
                                remove(prefs.sharedCommentsLabel)
                                remove(prefs.propertiesLabel)
                            }
                            .mapKeys { (k, v) ->
                                val tag = k.substringAfter('(').substringBefore(')')
                                Locale.forLanguageTag(tag)
                            }
                            .filter { it.value.isNotBlank() }
                            .toSortedMap(compareBy { it.toLanguageTag() })
                            .toMap(translations)
                    }
                }
            }
        }
        data.iterator()
    }

    private fun CSVRecord.getOrNull(key: String) = if (isSet(key)) get(key) else null

    override fun hasNext(): Boolean {
        return data.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return data.next()
    }

}