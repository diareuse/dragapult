package app.dragapult.ir.json

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
class ReaderJsonIR(
    input: InputStream
) : TranslationReader {
    private val json = Json {
        prettyPrint = true
        prettyPrintIndent = "\t"
        explicitNulls = false
        encodeDefaults = false
    }

    private val map = json.decodeFromStream<Map<String, Key>>(input).toSortedMap()
    private val iter = map.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        val (key, value) = iter.next()
        val ir = TranslationKeyIR(key)
        ir.metadata.comment = value.comment
        ir.metadata.properties.putAll(value.properties.orEmpty())
        ir.translations.putAll(value.translations)
        return ir
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

    class LocaleSerializer : KSerializer<Locale> {
        override val descriptor = PrimitiveSerialDescriptor("locale", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): Locale {
            return Locale.forLanguageTag(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, value: Locale) {
            encoder.encodeString(value.toLanguageTag())
        }
    }
}