package app.dragapult.ir.json

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import app.dragapult.ir.json.model.Key
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream

@OptIn(ExperimentalSerializationApi::class)
class ReaderJsonIR(
    input: InputStream,
    json: Json
) : TranslationReader {

    private val iterator by lazy {
        json.decodeFromStream<Map<String, Key>>(input).toSortedMap().iterator()
    }

    override fun hasNext(): Boolean {
        return iterator.hasNext()
    }

    override fun next(): TranslationKeyIR {
        val (key, value) = iterator.next()
        val ir = TranslationKeyIR(key)
        ir.metadata.comment = value.comment
        ir.metadata.properties.putAll(value.properties.orEmpty())
        ir.translations.putAll(value.translations)
        return ir
    }

}