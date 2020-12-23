package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.args.Argument
import dev.chainmail.dragapult.format.FileInput
import dev.chainmail.dragapult.format.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class AppleFileWriter(
    private val output: Argument.OutputDirectory
) : AbstractFileWriter() {

    override fun getFileName(language: Language): String {
        return "Localizable.strings"
    }

    override suspend fun write(language: Language, lines: List<FileInput>): File {
        val parentDir = File(output.dir, getFolderName(language)).ensureDirExists()
        val file = getOrCreateFile(parentDir, language)
        val contents = lines.joinToString(
            separator = ";" + System.lineSeparator(),
        )

        withContext(Dispatchers.IO) {
            file.writeText(contents)
        }

        return file
    }

    private fun getFolderName(language: Language): String {
        return "${language}.lproj"
    }

}