package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.PrintHelpCollector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Arguments(
    val input: InputFile,
    val inputFormat: InputFormat,
    val inputSeparator: InputSeparator,
    val output: OutputDirectory,
    val outputComment: OutputCommentEnabled,
    val outputFormat: OutputFormat
) {

    companion object {

        suspend fun findIn(args: Array<String>): Arguments = withContext(Dispatchers.Default) {
            Flags(args)

            if (Flags.isHelp) {
                PrintHelpCollector()
            }

            Arguments(
                input = InputFile.getInstance(args),
                inputFormat = InputFormat.getInstance(args),
                inputSeparator = InputSeparator.getInstance(args),
                output = OutputDirectory.getInstance(args),
                outputComment = OutputCommentEnabled.getInstance(args),
                outputFormat = OutputFormat.getInstance(args)
            )
        }

    }

}