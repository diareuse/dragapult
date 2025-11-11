package app.dragapult.util

import java.io.File
import java.util.*

private val LOCALES by lazy { Locale.getAvailableLocales() }

fun File.parseLocale(default: Locale): Locale {
    val language = name.findBetween('-', 0, default.language)
    val locale = Locale.forLanguageTag(language)
    if (locale == null || LOCALES.none { it.language == locale.language }) {
        System.err.println("Locale with ISO-639 language tag '$language' not known to this version of Java, this will become an error in v3")
    }
    return locale
}

// in the case of very long char-sequences,
// this will make it much faster with zero additional allocations
// to parse a text in between characters
fun String.findBetween(
    char: Char,
    startAt: Int = 0,
    default: String = this
): String {
    val substring = StringBuilder()
    var dashes = 0
    for (i in startAt..lastIndex) {
        val c = get(i)
        if (c == char) {
            if (++dashes == 2)
                break
            continue
        }
        if (dashes >= 1)
            substring.append(c)
    }
    return if (substring.isEmpty()) default else substring.toString()
}