package dev.chainmail.dragapult.format

import dev.chainmail.dragapult.model.KeyedTranslation

class JsonFileFormatter : AbstractFileFormatter() {

    override fun KeyedTranslation.toFileInputs(): Map<Language, FileInput> {
        return translations
            .asSequence()
            // json does not support comments
            .filter { !it.isComment }
            .map { it.language to it.translation.asFileInput(key) }
            .toMap()
    }

    private fun String.asFileInput(key: String): FileInput {
        return "\"$key\": \"$this\""
    }

}