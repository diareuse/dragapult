package dragapult.app.v2.reader

import dragapult.app.v2.TranslationKeyIR
import dragapult.app.v2.TranslationWriter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.io.File
import java.util.*

class WriterJson(
    private val dir: File,
    private val fileName: String = "strings.json"
) : TranslationWriter {

    private val json = Json {
        explicitNulls = false
        encodeDefaults = false
    }
    private val files = mutableMapOf<Locale, MutableMap<String, Value>>()

    override fun append(ir: TranslationKeyIR) {
        for ((locale, translation) in ir.translations) {
            val map = files.getOrPut(locale) { sortedMapOf() }
            map[ir.key] = Value(
                comment = ir.metadata?.comment,
                parameters = ir.metadata?.properties,
                value = translation
            )
        }
    }

    override fun close() {
        for ((locale, map) in files) {
            val file = File(dir, "${locale.toLanguageTag()}/$fileName")
            file.parentFile?.mkdirs()
            file.outputStream().use {
                json.encodeToStream(map, it)
                it.flush()
            }
        }
    }

    @Serializable
    data class Value(
        @SerialName("_c")
        val comment: String? = null,
        @SerialName("_p")
        val parameters: Map<String, String>? = null,
        @SerialName("value")
        val value: String
    )

}