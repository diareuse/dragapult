package dragapult.app.ir.csv

import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationReader
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.InputStream
import java.util.*

class ReaderCsvIR(
    private val input: InputStream
) : TranslationReader {

    private val data = mutableListOf<TranslationKeyIR>()

    init {
        val format = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .setCommentMarker(Char(3))
            .get()
        format.parse(input.reader()).forEach {
            data += TranslationKeyCsv(it)
        }
    }

    val iter = data.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return iter.next()
    }

    data class TranslationKeyCsv(
        private val record: CSVRecord
    ) : TranslationKeyIR {
        override val key: String
            get() = record.get(0)
        override val metadata: TranslationKeyIR.Metadata?
            get() = object : TranslationKeyIR.Metadata {
                override val comment: String?
                    get() = record.comment?.takeUnless { it.isBlank() }
                override val properties: Map<String, String>?
                    get() = record.getOrNull("properties")?.split("\n")?.filter { it.isNotBlank() }?.associate {
                        val pair = it.split("=", limit = 2)
                        pair[0] to pair.getOrNull(1).orEmpty()
                    }?.takeUnless { it.isEmpty() }
            }.takeIf { it.comment != null || it.properties != null }
        override val translations: Map<Locale, String>
            get() = record.toMap().toMutableMap().apply {
                remove(record.parser.headerNames[0])
                remove("properties")
            }.mapKeys { Locale.forLanguageTag(it.key) }.filter { it.value.isNotBlank() }
                .toSortedMap(compareBy { it.toLanguageTag() })

        private fun CSVRecord.getOrNull(key: String) = if (isSet(key)) get(key) else null
    }

}