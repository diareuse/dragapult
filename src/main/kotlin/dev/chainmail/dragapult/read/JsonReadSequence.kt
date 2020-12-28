package dev.chainmail.dragapult.read

import dev.chainmail.dragapult.model.Translation
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File

class JsonReadSequence(
    private val file: File
) : ReadSequence {

    override fun getSequence(): CloseableSequence<Translation> {
        if (!file.isFile) {
            return CloseableSequence(null, sequenceOf())
        }

        val reader = file.reader()
        val tokener = JSONTokener(reader)
        val sequence = JSONObject(tokener).asSequence().filterNot { it.isComment }

        return CloseableSequence(reader, sequence)
    }

    private fun JSONObject.asSequence() = keys().asSequence()
        .flatMap { getJSONObject(it).asTranslations(it) }

    private fun JSONObject.asTranslations(key: String) = keys().asSequence()
        .map { Translation(key, it, getString(it)) }

}