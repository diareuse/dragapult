package app.dragapult.android

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import app.dragapult.android.model.Resources
import app.dragapult.util.parseLocale
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.core.KtXmlReader
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File
import java.util.*

@OptIn(ExperimentalXmlUtilApi::class)
internal class ReaderAndroid(
    dir: File,
    xml: XML,
    private val defaultLocale: Locale = Locale.ENGLISH
) : TranslationReader {

    private val resourceFiles = dir.walk().filter { it.isFile }
    private val out by lazy {
        val out = mutableMapOf<String, TranslationKeyIR>()
        for (it in resourceFiles) {
            val res = it.inputStream().use { input ->
                xml.decodeFromReader<Resources>(KtXmlReader(input))
            }
            val folder = it.parentFile
            checkNotNull(folder) {
                "Parent file for resource is null, we need it to determine locale"
            }
            val locale = folder.parseLocale(defaultLocale)
            for (string in res.strings) out.getOrPut(string.name) { TranslationKeyIR(string.name) }.apply {
                metadata.comment = metadata.comment ?: string.comment
                metadata.properties.putAll(string.parameters.orEmpty())
                if (!string.translatable)
                    metadata.properties["translatable"] = "false"
                translations[locale] = string.content.contentString
            }
        }
        out.values.iterator()
    }

    override fun hasNext(): Boolean {
        return out.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return out.next()
    }

}
