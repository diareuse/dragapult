package app.dragapult.android

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.core.KtXmlReader
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue
import nl.adaptivity.xmlutil.util.CompactFragment
import java.io.File
import java.util.*

class ReaderAndroid(
    dir: File
) : TranslationReader {

    private val xml = XML {
        recommended {
            ignoreUnknownChildren()
            this.pedantic = false
        }
        this.xmlDeclMode = XmlDeclMode.Charset
    }

    val out = mutableMapOf<String, TranslationKeyIR>()

    init {
        dir.walk()
            .filter { it.isFile }
            .forEach {
                try {
                    val res = xml.decodeFromReader<Resources>(KtXmlReader(it.inputStream()))
                    // todo parse manually, this is inefficient
                    val tags = it.parentFile!!.name.splitToSequence("-").drop(1)
                    val langRegion = tags.take(1).firstOrNull() ?: "en"
                    val locale = Locale.forLanguageTag(langRegion)
                    for (string in res.strings) {
                        val ir = out.getOrPut(string.name) { TranslationKeyIR(string.name) }
                        ir.metadata.comment = ir.metadata.comment ?: string.comment
                        ir.metadata.properties.putAll(string.parameters.orEmpty())
                        if (!string.translatable)
                            ir.metadata.properties.put("translatable", "false")
                        ir.translations.put(locale, string.content.contentString)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
    }

    private val iter = out.values.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return iter.next()
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
