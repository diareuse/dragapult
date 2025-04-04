package dragapult.app.v2.reader

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import com.charleskorn.kaml.encodeToStream
import dragapult.app.v2.TranslationKeyIR
import dragapult.app.v2.TranslationWriter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.OutputStream

class WriterYamlIR(
    private val out: OutputStream,
) : TranslationWriter {

    val yaml = Yaml(
        configuration = YamlConfiguration(
            encodeDefaults = false,
            strictMode = false,
            codePointLimit = Int.MAX_VALUE
        )
    )

    private val items = mutableListOf<Key>()

    override fun append(ir: TranslationKeyIR) {
        items += Key(
            name = ir.key,
            comment = ir.metadata?.comment,
            properties = ir.metadata?.properties?.map { Property(it.key, it.value) },
            translations = ir.translations.mapKeys { it.key.toLanguageTag() }.toSortedMap(compareBy { it })
        )
    }

    override fun close() {
        yaml.encodeToStream(items, out)
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