package dragapult.app.v2.ir.yaml

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import com.charleskorn.kaml.decodeFromStream
import dragapult.app.v2.TranslationKeyIR
import dragapult.app.v2.TranslationReader
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.InputStream
import java.util.*

class ReaderYamlIR(
    private val input: InputStream
) : TranslationReader {
    @OptIn(ExperimentalSerializationApi::class)
    private val yaml = Yaml(
        configuration = YamlConfiguration(
            encodeDefaults = false,
            strictMode = false,
            codePointLimit = Int.MAX_VALUE
        )
    )

    val map = yaml.decodeFromStream<List<Key>>(input)
    val iter = map.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return TranslationKeyIRFromKey(iter.next())
    }

    @JvmInline
    private value class TranslationKeyIRFromKey(private val entry: Key) : TranslationKeyIR {
        override val key: String
            get() = entry.name
        override val metadata: TranslationKeyIR.Metadata?
            get() = object : TranslationKeyIR.Metadata {
                override val comment: String?
                    get() = entry.comment
                override val properties: Map<String, String>?
                    get() = entry.properties?.associate { it.name to it.value }
            }.takeIf { it.comment != null || it.properties != null }
        override val translations: Map<Locale, String>
            get() = entry.translations.mapKeys { Locale.forLanguageTag(it.key) }
    }


    @Serializable
    data class Key(
        @SerialName("key")
        val name: String,
        @SerialName("comment")
        val comment: String? = null,
        @SerialName("properties")
        val properties: List<Property>? = null,
        @SerialName("translations")
        val translations: Map<String, String>
    )

    @Serializable
    data class Property(
        @SerialName("name")
        val name: String,
        @SerialName("value")
        val value: String
    )

}