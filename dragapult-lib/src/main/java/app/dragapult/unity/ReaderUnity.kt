package app.dragapult.unity

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.util.*

class ReaderUnity(
    dir: File
) : TranslationReader {

    private val data = mutableListOf<TranslationKeyIR>()

    init {
        val format = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .get()
        dir.walk().filter { it.isFile }
            .filter { it.extension == "csv" }
            .forEach { file ->
                file.inputStream().use { input ->
                    format.parse(input.reader()).forEach { record ->
                        data += TranslationKeyIR(record.get(0)).apply {
                            metadata.comment = record.getOrNull("Shared Comments")?.takeUnless { it.isBlank() }
                            record.getOrNull("Properties")
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
                                    remove(record.parser.headerNames.find { it.lowercase() == "key" || it.lowercase() == "keys" })
                                    remove(record.parser.headerNames.find { it.lowercase().endsWith("comments") })
                                    remove("Properties")
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
    }

    private fun CSVRecord.getOrNull(key: String) = if (isSet(key)) get(key) else null
    private val iter = data.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return iter.next()
    }

}