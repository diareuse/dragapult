package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.model.InputFileFormat
import dev.chainmail.dragapult.model.OutputFileFormat
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

        private val availableArguments = listOf(
            Argument.InputFile.Companion,
            Argument.InputFormat.Companion,
            Argument.InputSeparator.Companion,
            Argument.OutputDirectory.Companion,
            Argument.OutputCommentEnabled.Companion,
            Argument.OutputFormat.Companion,
            Argument.Help.Companion,
        )

        suspend fun findIn(args: Array<String>): Arguments = withContext(Dispatchers.Default) {
            val arguments = availableArguments.mapNotNull { it.getInstance(args) }
            Arguments(
                input = arguments.find<Argument.InputFile>(),
                inputFormat = arguments.findOrElse { Argument.InputFormat(InputFileFormat.CSV) },
                inputSeparator = arguments.findOrElse { Argument.InputSeparator(",") },
                output = arguments.find<Argument.OutputDirectory>(),
                outputComment = arguments.findOrElse { Argument.OutputCommentEnabled() },
                outputFormat = arguments.findOrElse { Argument.OutputFormat(OutputFileFormat.JSON) },
                help = arguments.findOrNull<Argument.Help>()
            )
        }

        private inline fun <reified T> List<Argument>.find() =
            filterIsInstance<T>().first()

        private inline fun <reified T> List<Argument>.findOrNull() =
            filterIsInstance<T>().firstOrNull()

        private inline fun <reified T> List<Argument>.findOrElse(body: () -> T) =
            findOrNull<T>() ?: body()

    }

}