package dragapult.app.v2.reader

import dragapult.app.v2.TranslationKeyIR
import dragapult.app.v2.TranslationReader
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
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