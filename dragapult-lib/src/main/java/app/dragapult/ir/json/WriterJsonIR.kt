package app.dragapult.ir.json

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationWriter
import app.dragapult.ir.json.model.Key
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.io.OutputStream

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalSerializationApi::class)
class WriterJsonIR(
    private val output: OutputStream,
    private val json: Json
) : TranslationWriter {

    private val keys = sortedMapOf<String, Key>(compareBy { it.lowercase() })

    override fun append(ir: TranslationKeyIR) {
        keys[ir.key] = Key(
            comment = ir.metadata.comment,
            properties = ir.metadata.properties.takeUnless { it.isEmpty() },
            translations = ir.translations.toSortedMap(compareBy { it.toLanguageTag() })
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun close() {
        json.encodeToStream<Map<String, Key>>(keys, output)
        output.flush()
        output.close()
    }

}