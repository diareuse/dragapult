package app.dragapult.android

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationWriter
import app.dragapult.android.model.Resources
import app.dragapult.android.model.StringDefinition
import nl.adaptivity.xmlutil.core.KtXmlWriter
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.util.CompactFragment
import java.io.File
import java.util.*

class WriterAndroid(
    private val outputDirectory: File,
    private val xml: XML,
    private val prefs: AndroidPreferences
) : TranslationWriter {

    private val resources = mutableMapOf<Locale, MutableList<StringDefinition>>()

    override fun append(ir: TranslationKeyIR) {
        for ((locale, translation) in ir.translations) {
            val it = StringDefinition(
                name = ir.key,
                translatable = ir.metadata.properties["translatable"] != "false",
                comment = ir.metadata.comment,
                parameters = ir.metadata.properties.toMutableMap().apply { remove("translatable") }.toSortedMap(),
                content = CompactFragment(translation)
            )
            resources.getOrPut(locale) { mutableListOf() }.add(it)
        }
    }

    override fun close() {
        for (locale in resources.keys) {
            val res = Resources(strings = resources[locale]!!)
            val fileName = when {
                prefs.setDefaultLocaleExplicitly -> "values-${locale.toLanguageTag()}/${prefs.outputFileName}"
                prefs.defaultLocale.toLanguageTag() == locale.toLanguageTag() -> "values/${prefs.outputFileName}"
                else -> "values-${locale.toLanguageTag()}/${prefs.outputFileName}"
            }
            val file = File(outputDirectory, fileName)
            file.parentFile?.mkdirs()
            file.outputStream().writer().use { output ->
                xml.encodeToWriter(
                    target = KtXmlWriter(
                        writer = output,
                        xmlDeclMode = xml.config.xmlDeclMode,
                        xmlVersion = xml.config.xmlVersion
                    ),
                    value = res,
                    prefix = null
                )
                output.flush()
            }
        }
    }

}