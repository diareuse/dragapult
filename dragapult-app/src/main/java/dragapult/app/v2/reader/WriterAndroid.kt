package dragapult.app.v2.reader

import dragapult.app.v2.TranslationKeyIR
import dragapult.app.v2.TranslationWriter
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.core.KtXmlWriter
import nl.adaptivity.xmlutil.core.XmlVersion
import nl.adaptivity.xmlutil.serialization.*
import nl.adaptivity.xmlutil.util.CompactFragment
import java.io.File
import java.util.*

class WriterAndroid(
    private val outputDirectory: File
) : TranslationWriter {

    private val xml = XML {
        recommended {
            ignoreUnknownChildren()
            this.pedantic = false
            encodeDefault = XmlSerializationPolicy.XmlEncodeDefault.NEVER
        }
        this.xmlDeclMode = XmlDeclMode.Charset
    }
    private val resources = mutableMapOf<Locale, MutableList<StringDefinition>>()

    override fun append(ir: TranslationKeyIR) {
        for ((locale, translation) in ir.translations) {
            val it = StringDefinition(
                name = ir.key,
                translatable = ir.metadata?.properties?.get("translatable") != "false",
                comment = ir.metadata?.comment,
                parameters = null,
                content = CompactFragment(translation)
            )
            resources.getOrPut(locale) { mutableListOf() }.add(it)
        }
    }

    override fun close() {
        for (locale in resources.keys) {
            val res = Resources(strings = resources[locale]!!)
            val file = File(outputDirectory, "values-${locale.toLanguageTag()}/strings.xml")
            file.parentFile?.mkdirs()
            file.outputStream().writer().use { output ->
                xml.encodeToWriter(
                    target = KtXmlWriter(
                        output,
                        xmlDeclMode = XmlDeclMode.Minimal,
                        xmlVersion = XmlVersion.XML10
                    ),
                    value = res,
                    prefix = null
                )
                output.flush()
            }
        }
    }

    @Serializable
    @XmlSerialName("resources")
    private data class Resources(
        val strings: List<StringDefinition> = emptyList()
    )

    @Serializable
    @XmlSerialName("string")
    private data class StringDefinition(
        @XmlElement(false)
        @XmlSerialName("name")
        val name: String,
        @XmlSerialName("translatable")
        @XmlElement(false)
        val translatable: Boolean = true,
        @XmlSerialName("comment")
        @XmlElement(false)
        val comment: String?,
        @XmlElement(false)
        val parameters: Map<String, String>? = null,
        @XmlValue
        val content: CompactFragment = CompactFragment("")
    )

}