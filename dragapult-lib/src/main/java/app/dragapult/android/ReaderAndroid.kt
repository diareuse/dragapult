package app.dragapult.android

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import app.dragapult.android.model.Resources
import nl.adaptivity.xmlutil.core.KtXmlReader
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File
import java.util.*

internal class ReaderAndroid(
    dir: File,
    xml: XML
) : TranslationReader {

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

}
