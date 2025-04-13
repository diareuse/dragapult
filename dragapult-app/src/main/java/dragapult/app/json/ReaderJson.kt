package dragapult.app.json

import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationReader
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
class ReaderJson(
    dir: File
) : TranslationReader {
    private val json = Json {
        explicitNulls = false
        encodeDefaults = false
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val out = sortedMapOf<String, TranslationKeyIR>()

    init {
        dir.walk().filter { it.isFile }.forEach {
            val locale = Locale.forLanguageTag(it.parentFile.name)
            val data = json.decodeFromStream<Map<String, Value>>(it.inputStream()).toSortedMap()
            for ((key, value) in data) {
                val ir = out.getOrPut(key) { TranslationKeyIR(key) }
                ir.metadata.comment = ir.metadata.comment ?: value.comment
                ir.metadata.properties.putAll(value.parameters.orEmpty())
                ir.translations.put(locale, value.value)
            }
        }
    }

    private val iter = out.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return iter.next().value
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