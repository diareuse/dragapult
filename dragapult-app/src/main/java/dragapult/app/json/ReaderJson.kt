package dragapult.app.json

import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationReader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.util.*

class ReaderJson(
    private val dir: File
) : TranslationReader {
    private val json = Json {
        explicitNulls = false
        encodeDefaults = false
        ignoreUnknownKeys = true
        isLenient = true
    }

    val out = sortedMapOf<String, JsonIR>()

    init {
        dir.walk().filter { it.isFile }.filter { it.extension == "json" }.forEach {
            val locale = Locale.forLanguageTag(it.parentFile.name)
            val data = json.decodeFromStream<Map<String, Value>>(it.inputStream()).toSortedMap()
            for ((key, value) in data) {
                val ir = out.getOrPut(key) {
                    JsonIR(
                        key = key,
                        metadata = JsonIR.Metadata(
                            comment = value.comment,
                            properties = value.parameters
                        ).takeIf { it.comment != null || it.properties != null },
                        translations = mapOf(locale to value.value)
                    )
                }
                out[key] = ir.copy(translations = ir.translations + (locale to value.value))
            }
        }
    }

    val iter = out.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return iter.next().value
    }

    data class JsonIR(
        override val key: String,
        override val metadata: TranslationKeyIR.Metadata?,
        override val translations: Map<Locale, String>
    ) : TranslationKeyIR {
        data class Metadata(
            override val comment: String? = null,
            override val properties: Map<String, String>? = null
        ) : TranslationKeyIR.Metadata
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