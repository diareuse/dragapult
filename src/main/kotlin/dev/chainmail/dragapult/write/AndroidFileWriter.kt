package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.args.OutputDirectory
import dev.chainmail.dragapult.model.Translation
import java.io.File

class AndroidFileWriter(
    private val output: OutputDirectory
) : AbstractFileWriter() {

    private val files = mutableMapOf<String, File>()

    override fun getFileName(language: String): String {
        return "values.xml"
    }

    override suspend fun write(translation: Translation) {
        val file = files.find(translation)
        val line = translation.asAndroid()

        file.appendText(line)
    }

    override fun open(file: File) {
        file.writeText("<resources>" + System.lineSeparator())
    }

    override fun close() {
        files.values.forEach {
            it.appendText("</resources>")
        }
    }

    private fun MutableMap<String, File>.find(translation: Translation) = getOrPut(translation.language) {
        val parent = File(output.dir, getFolderName(translation.language)).ensureDirExists()
        getOrCreateFile(parent, translation.language)
    }

    private fun getFolderName(language: String): String {
        return when (language) {
            "en" -> "values"
            else -> "values-$language"
        }
    }

}

private const val linePrefix = "    "
private fun Translation.asAndroid(): String {
    return "%s<string name=\"%s\">%s</string>%s".format(linePrefix, key, translation, System.lineSeparator())
}
