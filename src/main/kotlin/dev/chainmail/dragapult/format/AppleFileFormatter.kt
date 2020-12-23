package dev.chainmail.dragapult.format

import dev.chainmail.dragapult.model.KeyedTranslation

class AppleFileFormatter(private val isCommentEnabled: Boolean) : AbstractFileFormatter() {

    override fun KeyedTranslation.toFileInputs(): Map<Language, FileInput> {
        val comments = if (!isCommentEnabled) "" else {
            val comments = translations.filter { it.isComment }
            if (comments.isEmpty()) {
                ""
            } else comments.joinToString(
                separator = System.lineSeparator(),
                postfix = System.lineSeparator()
            ) {
                "/* ${it.translation} */"
            }
        }

        return translations
            .asSequence()
            .filter { !it.isComment }
            .map { it.language to comments + it.translation.asFileInput(key) }
            .toMap()
    }

    private fun String.asFileInput(key: String): FileInput {
        return "\"$key\" = \"$this\""
    }

}