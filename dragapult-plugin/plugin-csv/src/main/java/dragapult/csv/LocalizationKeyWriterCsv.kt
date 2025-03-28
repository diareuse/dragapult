package dragapult.csv

import dragapult.core.LocalizationKeyWriter
import dragapult.core.LocalizationKeyWriterBuffered
import dragapult.core.LocalizationType
import java.io.File
import java.util.*

class LocalizationKeyWriterCsv(
    private val file: File
) : LocalizationKeyWriterBuffered() {

    private val locales
        get() = lines.entries.asSequence().flatMap { it.value }.map { it.locale }.distinct()

    private val headers
        get() = buildList {
            this += "key"
            addAll(locales.map { it.language })
        }

    // ---

    override fun close() {
        TODO()
        super.close()
    }

    // ---

    private fun Map.Entry<String, MutableSet<Translation>>.asRecord(headers: List<String>): List<String> {
        return List(headers.size) { index ->
            when (index) {
                0 -> key
                else -> value.valueFor(Locale.forLanguageTag(headers[index])).orEmpty()
            }
        }
    }

    private fun Set<Translation>.valueFor(locale: Locale): String? {
        return firstOrNull { it.locale == locale }?.value
    }

    // ---

    class Factory : LocalizationKeyWriter.Factory {

        override val type: LocalizationType
            get() = LocalizationTypeCsv

        override fun create(file: File): LocalizationKeyWriter {
            return LocalizationKeyWriterCsv(file)
        }

    }

}