package app.dragapult.ir.csv

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationWriter
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.OutputStream

class WriterCsvIR(
    private val output: OutputStream,
    private val format: CSVFormat
) : TranslationWriter {

    private val items = mutableListOf<TranslationKeyIR>()

    override fun append(ir: TranslationKeyIR) {
        items += ir
    }

    override fun close() {
        val keys = items.flatMap { it.translations.keys }.distinct()
        output.writer().use { writer ->
            val printer = CSVPrinter(writer, format)
            printer.printRecord(buildList {
                add("keys")
                addAll(keys.map { it.toLanguageTag() })
                add("properties")
            })
            for (item in items) {
                val comment = item.metadata.comment
                if (comment != null)
                    printer.printComment(comment)
                printer.printRecord(buildList {
                    add(item.key)
                    addAll(keys.map { item.translations[it] })
                    add(item.metadata.properties.entries.joinToString("\n") { "${it.key}=${it.value}" })
                })
            }
            writer.flush()
        }
    }

}