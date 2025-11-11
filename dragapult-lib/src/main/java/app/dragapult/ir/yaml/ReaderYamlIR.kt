package app.dragapult.ir.yaml

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import app.dragapult.ir.yaml.model.Key
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import java.io.InputStream
import java.util.*

class ReaderYamlIR(
    input: InputStream,
    private val yaml: Yaml
) : TranslationReader {

    private val keys by lazy { yaml.decodeFromStream<List<Key>>(input).iterator() }

    override fun hasNext(): Boolean {
        return keys.hasNext()
    }

    override fun next(): TranslationKeyIR {
        val key = keys.next()
        val ir = TranslationKeyIR(key.name)
        ir.metadata.comment = key.comment
        ir.metadata.properties.putAll(key.properties?.associate { it.name to it.value }.orEmpty())
        ir.translations.putAll(key.translations.mapKeys { Locale.forLanguageTag(it.key) })
        return ir
    }

}