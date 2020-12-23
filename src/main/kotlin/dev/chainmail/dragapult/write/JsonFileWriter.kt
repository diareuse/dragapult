package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.args.OutputDirectory
import dev.chainmail.dragapult.format.FileInput
import dev.chainmail.dragapult.format.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class JsonFileWriter(
    private val output: OutputDirectory
) : AbstractFileWriter() {

    override fun getFileName(language: Language): String {
        return "$language.json"
    }

    override suspend fun write(language: Language, lines: List<FileInput>): File {
        val file = getOrCreateFile(output.dir, language)
        val contents = lines.joinToString(
            prefix = "{" + System.lineSeparator(),
            separator = "," + System.lineSeparator(),
            postfix = System.lineSeparator() + "}"
        ) {
            linePrefix + it
        }

        withContext(Dispatchers.IO) {
            file.writeText(contents)
        }

        return file
    }

    companion object {
        // 4 spaces
        private const val linePrefix = "    "
    }

}