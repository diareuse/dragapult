package dev.chainmail.dragapult.parse

import dev.chainmail.dragapult.model.KeyedTranslation
import dev.chainmail.dragapult.model.Translation
import dev.chainmail.dragapult.model.with
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CsvFileParser(
    private val separator: String
) : AbstractFileParser() {

    override suspend fun tryParse(file: File): List<KeyedTranslation> {
        val lines = withContext(Dispatchers.IO) {
            file.readLines()
        }
        return parse(lines)
    }

    private suspend fun parse(lines: List<String>): List<KeyedTranslation> = withContext(Dispatchers.IO) {
        val columns = lines.first().split(separator)
        val languages = columns.takeLast(columns.size - 1)
        lines
            .takeLast(lines.size - 1)
            .asSequence()
            .map { it.split(separator) }
            .map { it.first() with languages.asTranslation(it) }
            .toList()
    }

    private fun List<String>.asTranslation(translations: List<String>) =
        mapIndexedNotNull { index, language ->
            val translation = translations.getOrNull(index + 1)

            if (translation != null) {
                Translation(
                    language = language,
                    translation = translation
                )
            } else {
                null
            }
        }

}
