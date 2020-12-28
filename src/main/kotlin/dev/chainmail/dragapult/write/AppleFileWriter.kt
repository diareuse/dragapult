package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.args.OutputDirectory
import dev.chainmail.dragapult.model.Translation
import java.io.File

class AppleFileWriter(
    private val output: OutputDirectory
) : AbstractFileWriter() {

    private val files = mutableMapOf<String, File>()

    override fun getFileName(language: String): String {
        return "Localizable.strings"
    }

    override suspend fun write(translation: Translation) {
        val file = files.find(translation)
        val line = translation.asApple()

        file.appendText(line)
    }

    override fun open(file: File) = Unit
    override fun close() = Unit

    private fun MutableMap<String, File>.find(translation: Translation) = getOrPut(translation.language) {
        val parent = File(output.dir, getFolderName(translation.language)).ensureDirExists()
        getOrCreateFile(parent, translation.language)
    }

    private fun getFolderName(language: String): String {
        return "${language}.lproj"
    }

}

private fun Translation.asApple(): String {
    return "\"%s\" = \"%s\";%s".format(key, translation, System.lineSeparator())
}
