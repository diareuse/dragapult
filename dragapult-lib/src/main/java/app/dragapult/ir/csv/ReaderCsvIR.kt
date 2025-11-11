package app.dragapult.ir.csv

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.InputStream
import java.util.*

class ReaderCsvIR(
    input: InputStream,
    format: CSVFormat
) : TranslationReader {

    private val data by lazy {
        val data = mutableListOf<TranslationKeyIR>()
        format.parse(input.reader()).forEach { record ->
            data += TranslationKeyIR(record.get(0)).apply {
                metadata.comment = record.comment?.takeUnless { it.isBlank() }
                record.getOrNull("properties")
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
                        remove(record.parser.headerNames[0])
                        remove("properties")
                    }
                    .mapKeys { Locale.forLanguageTag(it.key) }
                    .filter { it.value.isNotBlank() }
                    .toSortedMap(compareBy { it.toLanguageTag() })
                    .toMap(translations)
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