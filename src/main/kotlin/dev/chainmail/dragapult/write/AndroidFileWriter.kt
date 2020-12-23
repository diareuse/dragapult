package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.args.Argument
import dev.chainmail.dragapult.format.FileInput
import dev.chainmail.dragapult.format.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class AndroidFileWriter(
    private val output: Argument.OutputDirectory
) : AbstractFileWriter() {

    override fun getFileName(language: Language): String {
        return "values.xml"
    }

    override suspend fun write(language: Language, lines: List<FileInput>): File {
        val parentDir = File(output.dir, getFolderName(language)).ensureDirExists()
        val file = getOrCreateFile(parentDir, language)
        val contents = lines.joinToString(
            prefix = "<resources>" + System.lineSeparator(),
            separator = System.lineSeparator(),
            postfix = System.lineSeparator() + "</resources>"
        ) {
            linePrefix + it
        }

        withContext(Dispatchers.IO) {
            file.writeText(contents)
        }

        return file
    }

    private fun getFolderName(language: Language): String {
        return when (language) {
            "en" -> "values"
            else -> "values-$language"
        }
    }

    companion object {
        // 4 spaces
        private const val linePrefix = "    "
    }

}