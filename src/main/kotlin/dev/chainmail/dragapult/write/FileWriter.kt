package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.args.OutputDirectory
import dev.chainmail.dragapult.args.OutputFormat
import dev.chainmail.dragapult.model.OutputFileFormat
import dev.chainmail.dragapult.model.Translation

interface FileWriter : AutoCloseable {

    suspend fun write(translation: Translation)

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