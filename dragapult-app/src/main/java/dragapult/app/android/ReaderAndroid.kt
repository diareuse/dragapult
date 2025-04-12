package dragapult.app.android

import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationReader
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
                        var ir = out[string.name] ?: AndroidIR(
                            key = string.name,
                            metadata = AndroidIR.Metadata(
                                comment = string.comment?.takeIf { it.isNotBlank() },
                                properties = string.parameters?.takeIf { it.isNotEmpty() }
                            ).takeIf { it.comment != null || it.properties != null },
                            translations = mapOf(locale to string.content.contentString)
                        )
                        val translatable = string.translatable
                        val properties = buildMap {
                            var p = string.parameters
                            if (p != null) putAll(p)
                            p = ir.metadata?.properties
                            if (p != null) putAll(p)
                            if (!translatable)
                                put("translatable", "false")
                        }.takeUnless { it.isEmpty() }
                        ir = ir.copy(
                            metadata = ir.metadata?.copy(properties = properties) ?: AndroidIR.Metadata(
                                comment = string.comment,
                                properties = string.parameters.orEmpty().toMutableMap().apply {
                                    putAll(properties.orEmpty())
                                }.takeUnless { it.isEmpty() }
                            ),
                            translations = ir.translations.toMutableMap().apply {
                                put(locale, string.content.contentString)
                            }
                        )
                        out[string.name] = ir
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
