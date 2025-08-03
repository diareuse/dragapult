package app.dragapult.json

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationWriter
import app.dragapult.json.model.Value
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.io.File
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
class WriterJson(
    private val dir: File,
    private val json: Json,
    private val prefs: JsonPreferences
) : TranslationWriter {

    private val files = mutableMapOf<Locale, MutableMap<String, Value>>()

    override fun append(ir: TranslationKeyIR) {
        for ((locale, translation) in ir.translations) {
            val map = files.getOrPut(locale) { sortedMapOf() }
            map[ir.key] = Value(
                comment = ir.metadata.comment,
                parameters = ir.metadata.properties.takeUnless { it.isEmpty() },
                value = translation
            )
        }
    }

    override fun close() {
        for ((locale, map) in files) {
            val file = File(dir, "${locale.toLanguageTag()}/${prefs.outputFileName}")
            file.parentFile?.mkdirs()
            file.outputStream().use {
                json.encodeToStream(map, it)
                it.flush()
            }
        }
    }

}