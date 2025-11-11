package app.dragapult.ir.yaml

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationWriter
import app.dragapult.ir.yaml.model.Key
import app.dragapult.ir.yaml.model.Property
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.encodeToStream
import java.io.OutputStream

class WriterYamlIR(
    private val out: OutputStream,
    private val yaml: Yaml
) : TranslationWriter {

    private val items = mutableListOf<Key>()

    override fun append(ir: TranslationKeyIR) {
        items += Key(
            name = ir.key,
            comment = ir.metadata.comment,
            properties = ir.metadata.properties.map { Property(it.key, it.value) }.takeUnless { it.isEmpty() },
            translations = ir.translations.mapKeys { it.key.toLanguageTag() }.toSortedMap(compareBy { it })
        )
    }

    override fun close() {
        yaml.encodeToStream(items, out)
    }

}