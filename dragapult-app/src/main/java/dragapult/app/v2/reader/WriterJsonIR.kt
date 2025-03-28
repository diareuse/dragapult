package dragapult.app.v2.reader

import dragapult.app.v2.TranslationKeyIR
import dragapult.app.v2.TranslationWriter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.io.OutputStream
import java.util.*

class WriterJsonIR(
    private val output: OutputStream
) : TranslationWriter {

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        prettyPrint = true
        prettyPrintIndent = "\t"
        explicitNulls = false
        encodeDefaults = false
    }
    private val keys = sortedMapOf<String, Key>(compareBy { it.lowercase() })

    override fun append(ir: TranslationKeyIR) {
        keys[ir.key] = Key(
            comment = ir.metadata?.comment,
            properties = ir.metadata?.properties,
            translations = ir.translations
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun close() {
        json.encodeToStream<Map<String, Key>>(keys, output)
        output.flush()
        output.close()
    }

    @Serializable
    data class Key(
        @SerialName("comment")
        val comment: String? = null,
        @SerialName("properties")
        val properties: Map<String, String>? = null,
        @SerialName("keys")
        val translations: Map<@Serializable(with = LocaleSerializer::class) Locale, String>
    )

    class LocaleSerializer : kotlinx.serialization.KSerializer<Locale> {
        override val descriptor = PrimitiveSerialDescriptor("locale", PrimitiveKind.STRING)
        override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): Locale {
            return Locale.forLanguageTag(decoder.decodeString())
        }

        override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: Locale) {
            encoder.encodeString(value.toLanguageTag())
        }
    }

}