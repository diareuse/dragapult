package dev.chainmail.dragapult.format

import dev.chainmail.dragapult.model.KeyedTranslation

abstract class AbstractFileFormatter : FileFormatter {

    final override suspend fun format(lines: List<KeyedTranslation>): Map<Language, List<FileInput>> {
        return lines.asSequence()
            .map { it.toFileInputs() }
            .map { it.entries }
            .flatten()
            .map { it.key to it.value }
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
            )
    }

    /**
     * This function expects to output to look like:
     *
     * ## Android:
     *
     * `en` | `<string name="key_foo_bar">foo bar foo foo</string>`
     *
     * ## iOS:
     *
     * `en` | `"key_foo_bar" = "foo bar foo foo"`
     *
     * ## Json: (web)
     *
     * `en` | `"key_foo_bar": "foo bar foo foo"`
     * */
    abstract fun KeyedTranslation.toFileInputs(): Map<Language, FileInput>

}

typealias Language = String
typealias FileInput = String