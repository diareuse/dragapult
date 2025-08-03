package app.dragapult.apple

import app.dragapult.TranslationKeyIR
import app.dragapult.TranslationReader
import nl.adaptivity.xmlutil.core.impl.multiplatform.Language
import java.io.File
import java.util.*

class ReaderApple(
    dir: File
) : TranslationReader {

    private val out = mutableMapOf<String, TranslationKeyIR>()

    @Language("RegExp")
    private val patternMeta = """(?<metadata>(?:/\*@\s*\w+\s*=\s*"?.+"?\s+\*/\s)+\s*)?"""

    @Language("RegExp")
    private val patternComment = """(?:/\*\s*(?<comment>[\s\S]*?)\s*\*/\s*)?"""

    @Language("RegExp")
    private val patternKey = """['"](?<key>.+)['"]"""

    @Language("RegExp")
    private val patternValue = """['"](?<value>.*)['"]"""

    init {
        dir.walk().filter { it.isFile }.forEach { file ->
            val regex = Regex("""${patternMeta}${patternComment}${patternKey}\s*=\s*${patternValue}\s*;""")
            regex.findAll(file.readText()).forEach {
                val metadata = it.groups["metadata"]?.value?.let {
                    val map = mutableMapOf<String, String>()
                    Regex("""/\*\s*@\s*(?<key>.+)\s*=\s*"?(?<value>.+)"?\s+\*/""").findAll(it).forEach {
                        map[it.groups["key"]?.value?.trim() ?: ""] = it.groups["value"]?.value ?: ""
                    }
                    map.takeIf { it.isNotEmpty() }
                }
                val comment = it.groups["comment"]?.value?.trim()
                val key = it.groups["key"]?.value
                val value = it.groups["value"]?.value
                if (key != null && value != null) {
                    val locale = Locale.forLanguageTag(file.parentFile.name.substringBefore(".lproj"))
                    val ir = out.getOrPut(key) { TranslationKeyIR(key) }
                    ir.metadata.comment = ir.metadata.comment ?: comment
                    ir.metadata.properties.putAll(metadata.orEmpty())
                    ir.translations[locale] = value
                }
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