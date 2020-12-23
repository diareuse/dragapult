package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.log.expects
import dev.chainmail.dragapult.model.InputFileFormat
import dev.chainmail.dragapult.model.OutputFileFormat
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

sealed class Argument {

    data class InputFile(val file: File) : Argument() {

        constructor(file: String) : this(file = File(Paths.get(".").toFile(), file))

        companion object : ArgumentDefinition<InputFile> {
            override val callSign = "-i"
            override fun getInstance(args: Array<String>): InputFile {
                val index = args.indexOf(callSign)
                val path = args.getOrNull(index + 1)

                if (index < 0) {
                    exitProcess(1)
                }

                if (path == null) {
                    "path" expects "!= null" given path
                    exitProcess(1)
                }

                return InputFile(path)
            }
        }

    }

    data class OutputDirectory(val dir: File) : Argument() {

        constructor(dir: String) : this(dir = File(Paths.get(".").toFile(), dir))

        companion object : ArgumentDefinition<OutputDirectory> {
            override val callSign = "-o"
            override fun getInstance(args: Array<String>): OutputDirectory {
                val index = args.indexOf(callSign)
                val path = args.getOrNull(index + 1)

                if (index < 0) {
                    exitProcess(1)
                }

                if (path == null) {
                    "path" expects "!= null" given path
                    exitProcess(1)
                }

                return OutputDirectory(path)
            }
        }

    }

    class Help : Argument() {

        override fun toString(): String {
            return "Help"
        }

        companion object : ArgumentDefinition<Help> {
            override val callSign = "-h"
            override fun getInstance(args: Array<String>): Help? {
                return if (args.contains(callSign)) Help() else null
            }
        }

    }

    data class InputFormat(val format: InputFileFormat) : Argument() {

        companion object : ArgumentDefinition<InputFormat> {
            override val callSign = "-iF"
            override fun getInstance(args: Array<String>): InputFormat {
                val index = args.indexOf(callSign)
                val stringFormat = args.getOrNull(index + 1)
                val format = InputFileFormat.findValue(stringFormat)

                if (index < 0) {
                    return InputFormat(InputFileFormat.CSV)
                }

                if (format == null) {
                    "format" expects "!= null" given format
                    exitProcess(1)
                }

                return InputFormat(format)
            }
        }
    }

    data class InputSeparator(val separator: String) : Argument() {

        companion object : ArgumentDefinition<InputSeparator> {
            override val callSign = "-iS"
            override fun getInstance(args: Array<String>): InputSeparator {
                val index = args.indexOf(callSign)
                val separator = args.getOrNull(index + 1)

                if (index < 0) {
                    return InputSeparator(",")
                }

                if (separator == null) {
                    "separator" expects "!= null" given separator
                    exitProcess(1)
                }

                return InputSeparator(separator)
            }
        }

    }

    data class OutputCommentEnabled(val isEnabled: Boolean = false) : Argument() {

        companion object : ArgumentDefinition<OutputCommentEnabled> {
            override val callSign = "-oC"
            override fun getInstance(args: Array<String>): OutputCommentEnabled {
                val isEnabled = args.contains(callSign)
                return OutputCommentEnabled(isEnabled = isEnabled)
            }
        }

    }

    data class OutputFormat(val format: OutputFileFormat) : Argument() {

        companion object : ArgumentDefinition<OutputFormat> {
            override val callSign = "-oF"
            override fun getInstance(args: Array<String>): OutputFormat {
                val index = args.indexOf(callSign)
                val formatString = args.getOrNull(index + 1)
                val format = OutputFileFormat.findValue(formatString)

                if (index < 0) {
                    return OutputFormat(OutputFileFormat.JSON)
                }

                if (format == null) {
                    "output format" expects "!= null" given format
                    exitProcess(1)
                }

                return OutputFormat(format)
            }
        }


    }

}

interface ArgumentDefinition<Instance> {

    val callSign: String

    fun getInstance(args: Array<String>): Instance?

}