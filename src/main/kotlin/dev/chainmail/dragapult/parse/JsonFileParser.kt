package dev.chainmail.dragapult.parse

import dev.chainmail.dragapult.model.KeyedTranslation
import dev.chainmail.dragapult.model.Translation
import dev.chainmail.dragapult.model.with
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File

class JsonFileParser : AbstractFileParser() {

    override suspend fun tryParse(file: File): List<KeyedTranslation> {
        val json = withContext(Dispatchers.IO) { JSONObject(file.readText()) }
        val output = mutableListOf<KeyedTranslation>()
        json.keys().forEach {
            output += json.getJSONObject(it).toKeyedTranslation(it)
        }
        return output
    }

    private fun JSONObject.toKeyedTranslation(key: String): KeyedTranslation {
        val translations = mutableListOf<Translation>()
        keys().forEach {
            translations += Translation(
                language = it,
                translation = getString(it).replace("\n", "\\n")
            )
        }

        return key with translations
    }

}
