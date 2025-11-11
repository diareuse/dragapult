package app.dragapult.apple

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationWriter
import java.io.File
import java.io.OutputStreamWriter
import java.util.*

class WriterApple(
    private val dir: File,
    private val prefs: ApplePreferences
) : TranslationWriter {

    private val writers = mutableMapOf<Locale, OutputStreamWriter>()

    override fun append(ir: TranslationKeyIR) {
        for ((lang, value) in ir.translations) {
            val out = writers.getOrPut(lang) {
                val file = File(dir, "${lang.toLanguageTag()}.lproj/${prefs.outputFileName}")
                file.parentFile?.mkdirs()
                file.createNewFile()
                file.outputStream().writer()
            }

            for ((k, v) in ir.metadata.properties) {
                out.appendLine("/*@ $k = $v */")
            }
            val c = ir.metadata.comment
            if (c != null) {
                out.appendLine("/* $c */")
            }
            out.appendLine("\"${ir.key}\" = \"$value\";")
        }
    }

    override fun close() {
        for (writer in writers.values) {
            writer.flush()
            writer.close()
        }
    }

}