package dev.chainmail.dragapult.args

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Arguments(
    val input: Argument.InputFile,
    val inputFormat: Argument.InputFormat,
    val inputSeparator: Argument.InputSeparator,
    val output: Argument.OutputDirectory,
    val outputComment: Argument.OutputCommentEnabled,
    val outputFormat: Argument.OutputFormat,
    val help: Argument.Help?
) {

    companion object {

        suspend fun findIn(args: Array<String>): Arguments = withContext(Dispatchers.Default) {
            Arguments(
                input = Argument.InputFile.getInstance(args),
                inputFormat = Argument.InputFormat.getInstance(args),
                inputSeparator = Argument.InputSeparator.getInstance(args),
                output = Argument.OutputDirectory.getInstance(args),
                outputComment = Argument.OutputCommentEnabled.getInstance(args),
                outputFormat = Argument.OutputFormat.getInstance(args),
                help = Argument.Help.getInstance(args)
            )
        }

    }

}