package dragapult.app.apple

import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationReader
import nl.adaptivity.xmlutil.core.impl.multiplatform.Language
import java.io.File
import java.util.*

class ReaderApple(
    dir: File
) : TranslationReader {

    private val out = mutableMapOf<String, AppleIR>()

    @Language("RegExp")
    private val patternMeta = """(?<metadata>(?:/\*@\s*\w+\s*=\s*"?.+"?\s+\*/\s)+\s*)?"""

    @Language("RegExp")
    private val patternComment = """(?:/\*\s*(?<comment>[\s\S]*)\s*\*/\s*)?"""

    @Language("RegExp")
    private val patternKey = """['"](?<key>.+)['"]"""

    @Language("RegExp")
    private val patternValue = """['"](?<value>.*)['"]"""

    init {
        dir.walk().filter { it.isFile }.filter { it.extension == "strings" }.forEach { file ->
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
                val value =
                    it.groups["value"]?.value?.replace("%@", "%s")?.replace("\\\\n", "\\p")?.replace("\\n", "\n")
                        ?.replace("\\p", "\\n")
                if (key != null && value != null) {
                    val locale = Locale.forLanguageTag(file.parentFile.name.substringBefore(".lproj"))
                    val ir = out.getOrPut(key) {
                        AppleIR(
                            key = key,
                            metadata = AppleIR.Metadata(
                                comment = comment,
                                properties = metadata
                            ).takeIf { it.comment != null || it.properties != null },
                            translations = mapOf(locale to value)
                        )
                    }
                    out[key] = ir.add(locale, value).update(comment, metadata)
                }
            }
        }
    }

    data class AppleIR(
        override val key: String,
        override val metadata: Metadata?,
        override val translations: Map<Locale, String>
    ) : TranslationKeyIR {
        data class Metadata(
            override val comment: String? = null,
            override val properties: Map<String, String>? = null
        ) : TranslationKeyIR.Metadata

        fun update(comment: String?, properties: Map<String, String>?): AppleIR {
            return AppleIR(
                key = key,
                metadata = metadata?.copy(comment = comment, properties = properties) ?: Metadata(
                    comment = comment,
                    properties = properties
                ),
                translations = translations
            )
        }

        fun add(locale: Locale, translation: String): AppleIR {
            return copy(
                translations = translations + (locale to translation)
            )
        }
    }

    private val iter = out.values.iterator()

    override fun hasNext(): Boolean {
        return iter.hasNext()
    }

    override fun next(): TranslationKeyIR {
        return iter.next().run {
            copy(translations = translations.toSortedMap(compareBy { it.toLanguageTag() }))
        }
    }

}