package dragapult.app.ir.json

import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationReader
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

class ReaderJsonIR(
    private val input: InputStream
) : TranslationReader {
    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        prettyPrint = true
        prettyPrintIndent = "\t"
        explicitNulls = false
        encodeDefaults = false
    }

    val map = json.decodeFromStream<Map<String, Key>>(input).toSortedMap()
    val iter = map.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return TranslationKeyIRFromKey(iter.next())
    }

    @JvmInline
    private value class TranslationKeyIRFromKey(private val entry: Map.Entry<String, Key>) : TranslationKeyIR {
        override val key: String
            get() = entry.key
        override val metadata: TranslationKeyIR.Metadata?
            get() = object : TranslationKeyIR.Metadata {
                override val comment: String?
                    get() = entry.value.comment
                override val properties: Map<String, String>?
                    get() = entry.value.properties
            }.takeIf { it.comment != null || it.properties != null }
        override val translations: Map<Locale, String>
            get() = entry.value.translations
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