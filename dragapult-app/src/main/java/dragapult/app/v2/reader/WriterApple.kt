package dragapult.app.v2.reader

import dragapult.app.v2.TranslationKeyIR
import dragapult.app.v2.TranslationWriter
import java.io.File
import java.io.OutputStreamWriter
import java.util.*

class WriterApple(
    private val dir: File,
    private val fileName: String = "Localizable.strings"
) : TranslationWriter {

    private val writers = mutableMapOf<Locale, OutputStreamWriter>()

    override fun append(ir: TranslationKeyIR) {
        for ((lang, value) in ir.translations) {
            val out = writers.getOrPut(lang) {
                val file = File(dir, "${lang.toLanguageTag()}.lproj/${fileName}")
                file.parentFile?.mkdirs()
                file.createNewFile()
                file.outputStream().writer()
            }

            val props = ir.metadata?.properties
            if (props != null) {
                for ((k, v) in props) {
                    out.appendLine("/*@ $k = $v */")
                }
            }
            val c = ir.metadata?.comment
            if (c != null) {
                out.appendLine("/* $c */")
            }
            out.appendLine(
                "\"${ir.key}\" = \"${
                    value.replace("%s", "%@").replace("\\n", "\\\\n").replace("\n", "\\n")
                }\";"
            )
        }
    }

    override fun close() {
        for (writer in writers.values) {
            writer.flush()
            writer.close()
        }
    }

}