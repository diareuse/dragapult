package app.dragapult.ir.yaml

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import com.charleskorn.kaml.decodeFromStream
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.InputStream
import java.util.*

class ReaderYamlIR(
    input: InputStream
) : TranslationReader {
    @OptIn(ExperimentalSerializationApi::class)
    private val yaml = Yaml(
        configuration = YamlConfiguration(
            encodeDefaults = false,
            strictMode = false,
            codePointLimit = Int.MAX_VALUE
        )
    )

    private val map = yaml.decodeFromStream<List<Key>>(input)
    private val iter = map.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        val key = iter.next()
        val ir = TranslationKeyIR(key.name)
        ir.metadata.comment = key.comment
        ir.metadata.properties.putAll(key.properties?.associate { it.name to it.value }.orEmpty())
        ir.translations.putAll(key.translations.mapKeys { Locale.forLanguageTag(it.key) })
        return ir
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