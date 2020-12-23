package dev.chainmail.dragapult.format

import dev.chainmail.dragapult.args.Argument
import dev.chainmail.dragapult.model.KeyedTranslation
import dev.chainmail.dragapult.model.OutputFileFormat

interface FileFormatter {

    suspend fun format(lines: List<KeyedTranslation>): Map<Language, List<FileInput>>

    companion object {

        operator fun invoke(
            format: Argument.OutputFormat,
            comment: Argument.OutputCommentEnabled
        ) = when (format.format) {
            OutputFileFormat.ANDROID -> AndroidFileFormatter(comment.isEnabled)
            OutputFileFormat.APPLE -> AppleFileFormatter(comment.isEnabled)
            OutputFileFormat.JSON -> JsonFileFormatter()
        }

    }

}