package app.dragapult.unity

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationWriter
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File

class WriterUnity(
    private val dir: File,
    private val format: CSVFormat,
    private val prefs: UnityPreferences,
) : TranslationWriter {

    private val items = mutableListOf<TranslationKeyIR>()

    override fun append(ir: TranslationKeyIR) {
        items += ir
    }

    override fun close() {
        val keys = items.flatMap { it.translations.keys }.distinct()
        val output = File(dir, prefs.outputFileName).apply {
            createNewFile()
        }
        output.writer().use { writer ->
            val printer = CSVPrinter(writer, format)
            printer.printRecord(buildList {
                add(prefs.keyLabel)
                add(prefs.sharedCommentsLabel)
                addAll(keys.map { "${it.displayLanguage}(${it.toLanguageTag()})" })
                add(prefs.propertiesLabel)
            })
            for (item in items) {
                val comment = item.metadata.comment
                if (comment != null)
                    printer.printComment(comment)
                printer.printRecord(buildList {
                    add(item.key)
                    add(item.metadata.comment.orEmpty())
                    addAll(keys.map { item.translations[it] })
                    add(item.metadata.properties.entries.joinToString("\n") { "${it.key}=${it.value}" })
                })
            }
            writer.flush()
        }
    }

}