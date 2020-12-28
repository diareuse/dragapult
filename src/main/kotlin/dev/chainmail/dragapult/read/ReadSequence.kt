package dev.chainmail.dragapult.read

import dev.chainmail.dragapult.args.InputFile
import dev.chainmail.dragapult.args.InputFormat
import dev.chainmail.dragapult.args.InputSeparator
import dev.chainmail.dragapult.model.InputFileFormat
import dev.chainmail.dragapult.model.Translation

interface ReadSequence {

    fun getSequence(): CloseableSequence<Translation>

    companion object {

        operator fun invoke(
            file: InputFile,
            format: InputFormat,
            separator: InputSeparator
        ): ReadSequence = when (format.format) {
            InputFileFormat.CSV -> CSVReadSequence(file.file, separator.separator)
            InputFileFormat.JSON -> JsonReadSequence(file.file)
            InputFileFormat.TWINE -> TODO()
        }

    }

}