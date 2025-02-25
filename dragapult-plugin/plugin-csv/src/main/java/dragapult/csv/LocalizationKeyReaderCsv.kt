package dragapult.csv

import com.google.auto.service.AutoService
import dragapult.core.LocalizationKeyReader
import dragapult.core.LocalizationType
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.File
import java.util.*

class LocalizationKeyReaderCsv(
    private val file: File
) : LocalizationKeyReader {

    override val keys: Sequence<String>
        get() = sequence {
            file.csvParser().use {
                val lines = it.dropWhile { key -> keyRegex.matches(key.firstOrNull().orEmpty()) }
                for (line in lines) {
                    yield(line[0])
                }
            }
        }

    override fun read(key: String): Sequence<Pair<Locale, String>> {
        return sequence {
            file.csvParser().use {
                yieldAll(it.asTranslationSequence(key = key))
            }
        }
    }

    private fun File.csvParser(): CSVParser {
        val reader = reader()
        val format = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .get()
        return format.parse(reader)
    }

    private fun CSVParser.asTranslationSequence(key: String): Sequence<Pair<Locale, String>> {
        return sequence {
            val languages = headerMap.entries.dropWhile { keyRegex.matches(it.key) }
            val translations = find { it[0] == key } ?: return@sequence
            for ((language, index) in languages) {
                val locale = Locale.forLanguageTag(language)
                val value = translations.get(index)
                yield(locale to value)
            }
        }
    }

    @AutoService(LocalizationKeyReader.Factory::class)
    class Factory : LocalizationKeyReader.Factory {

        override val type: LocalizationType
            get() = LocalizationTypeCsv

        override fun create(file: File): LocalizationKeyReader {
            return LocalizationKeyReaderCsv(file)
        }

    }

    companion object {

        private val keyRegex = Regex("^[kK][eE][yY][sS]?")

    }

}