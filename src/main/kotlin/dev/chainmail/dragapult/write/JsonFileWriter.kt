package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.args.OutputDirectory
import dev.chainmail.dragapult.model.Translation
import java.io.File

class JsonFileWriter(
    private val output: OutputDirectory
) : AbstractFileWriter() {

    private val files = mutableMapOf<String, File>()
    private var isFirst = true

    override fun getFileName(language: String): String {
        return "$language.json"
    }

    override suspend fun write(translation: Translation) {
        val file = files.find(translation)
        val line = translation.asJson()

        if (!isFirst) {
            file.appendText("," + System.lineSeparator())
        }
        file.appendText(line)

        isFirst = false
    }

    override fun open(file: File) {
        file.writeText("{" + System.lineSeparator())
    }

    override fun close() {
        files.values.forEach {
            it.appendText("}")
        }
    }

    private fun MutableMap<String, File>.find(translation: Translation) = getOrPut(translation.language) {
        getOrCreateFile(output.dir, translation.language)
    }

}

private const val linePrefix = "    "
private fun Translation.asJson(): String {
    return "%s\"%s\": \"%s\"%s".format(linePrefix, key, translation, System.lineSeparator())
}