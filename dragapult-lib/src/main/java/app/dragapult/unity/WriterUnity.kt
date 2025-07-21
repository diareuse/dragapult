package app.dragapult.unity

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationWriter
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File

class WriterUnity(
    private val dir: File,
    private val fileName: String = "strings.csv"
) : TranslationWriter {

    private val items = mutableListOf<TranslationKeyIR>()
    private val format = CSVFormat.DEFAULT.builder()
        .setRecordSeparator("\n")
        .get()

    override fun append(ir: TranslationKeyIR) {
        items += ir
    }

    override fun close() {
        val keys = items.flatMap { it.translations.keys }.distinct()
        val output = File(dir, fileName).apply {
            createNewFile()
        }
        output.writer().use { writer ->
            val printer = CSVPrinter(writer, format)
            printer.printRecord(buildList {
                add("Key")
                add("Shared Comments")
                addAll(keys.map { "${it.displayLanguage}(${it.toLanguageTag()})" })
                add("Properties")
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