package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.args.OutputDirectory
import dev.chainmail.dragapult.args.OutputFormat
import dev.chainmail.dragapult.format.FileInput
import dev.chainmail.dragapult.format.Language
import dev.chainmail.dragapult.model.OutputFileFormat
import java.io.File

interface FileWriter {

    suspend fun write(files: Map<Language, List<FileInput>>): List<File>

    companion object {

        operator fun invoke(
            format: OutputFormat,
            output: OutputDirectory
        ): FileWriter = when (format.format) {
            OutputFileFormat.ANDROID -> AndroidFileWriter(output)
            OutputFileFormat.APPLE -> AppleFileWriter(output)
            OutputFileFormat.JSON -> JsonFileWriter(output)
        }

    }

}