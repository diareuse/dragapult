package dragapult.app.v2.android

import dragapult.app.v2.TranslationKeyIR
import dragapult.app.v2.TranslationReader
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

    val out = mutableMapOf<String, AndroidIR>()

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
                        val ir = out.getOrPut(string.name) {
                            AndroidIR(
                                key = string.name,
                                metadata = AndroidIR.Metadata(
                                    comment = string.comment?.takeIf { it.isNotBlank() },
                                    properties = string.parameters?.takeIf { it.isNotEmpty() }
                                ).takeIf { it.comment != null || it.properties != null },
                                translations = mapOf(locale to string.content.contentString)
                            )
                        }
                        val translatable = string.translatable
                        val properties = if (!translatable) {
                            val map = ir.metadata?.properties.orEmpty().toMutableMap()
                            map["translatable"] = "false"
                            map
                        } else {
                            ir.metadata?.properties
                        }
                        val metadata =
                            if (properties != null) ir.metadata?.copy(properties = properties) ?: AndroidIR.Metadata(
                                null,
                                properties
                            ) else null
                        val translations = ir.translations.toMutableMap()
                        translations[locale] = string.content.contentString
                        out[string.name] = ir.copy(translations = translations, metadata = metadata)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
    }

    data class AndroidIR(
        override val key: String,
        override val metadata: Metadata?,
        override val translations: Map<Locale, String>
    ) : TranslationKeyIR {

        data class Metadata(
            override val comment: String?,
            override val properties: Map<String, String>?
        ) : TranslationKeyIR.Metadata
    }

    val iter = out.values.iterator()

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
