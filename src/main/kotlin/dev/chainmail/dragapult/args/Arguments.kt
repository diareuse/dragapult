package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.PrintExamples
import dev.chainmail.dragapult.PrintHelpCollector

data class Arguments(
    val input: InputFile,
    val inputFormat: InputFormat,
    val inputSeparator: InputSeparator,
    val output: OutputDirectory,
    val outputFormat: OutputFormat
) {

    companion object {

        fun findIn(args: Array<String>): Arguments {
            Flags(args)

            when {
                Flags.isHelp -> PrintHelpCollector()
                Flags.isHelpExample -> PrintExamples()
            }

            return Arguments(
                input = InputFile.getInstance(args),
                inputFormat = InputFormat.getInstance(args),
                inputSeparator = InputSeparator.getInstance(args),
                output = OutputDirectory.getInstance(args),
                outputFormat = OutputFormat.getInstance(args)
            )
        }

    }

}