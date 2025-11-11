package app.dragapult.json

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import app.dragapult.json.model.Value
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
class ReaderJson(
    dir: File,
    json: Json
) : TranslationReader {

    private val out by lazy {
        val out = sortedMapOf<String, TranslationKeyIR>()
        dir.walk().filter { it.isFile }.forEach {
            val locale = Locale.forLanguageTag(it.parentFile.name)
            val data = json.decodeFromStream<Map<String, Value>>(it.inputStream()).toSortedMap()
            for ((key, value) in data) {
                val ir = out.getOrPut(key) { TranslationKeyIR(key) }
                ir.metadata.comment = ir.metadata.comment ?: value.comment
                ir.metadata.properties.putAll(value.parameters.orEmpty())
                ir.translations[locale] = value.value
            }
        }
        out.values.iterator()
    }

    override fun hasNext(): Boolean {
        return out.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return out.next()
    }

}