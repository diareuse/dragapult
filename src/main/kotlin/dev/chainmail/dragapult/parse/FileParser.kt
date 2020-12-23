package dev.chainmail.dragapult.parse

import dev.chainmail.dragapult.args.Argument
import dev.chainmail.dragapult.model.InputFileFormat
import dev.chainmail.dragapult.model.KeyedTranslation
import java.io.File

interface FileParser {

    suspend fun parse(file: File): List<KeyedTranslation>

    companion object {

        operator fun invoke(
            format: Argument.InputFormat,
            separator: Argument.InputSeparator
        ): FileParser = when (format.format) {
            InputFileFormat.CSV -> CsvFileParser(separator.separator)
            InputFileFormat.JSON -> JsonFileParser()
            InputFileFormat.TWINE -> TwineFileParser()
        }

    }

}