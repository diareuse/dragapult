package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.Exit
import dev.chainmail.dragapult.log.expects
import java.io.File

data class InputFile(val file: File) {

    constructor(file: String) : this(file = File(file))

    init {
        if (Flags.isDebug) {
            println("Input file: ${file.absolutePath}")
        }
    }

    companion object : ArgumentDefinition<InputFile> {

        override val callSign = "-i"

        override fun getInstance(args: Array<String>): InputFile {
            val index = args.indexOf(callSign)
            val path = args.getOrNull(index + 1)

            if (index < 0) {
                Exit.requiredArgumentMissing()
            }

            if (path == null) {
                "path" expects "!= null" given path
                Exit.expectedArgumentNotFound()
            }

            return InputFile(path)
        }

        override fun toString(): String {
            return """
                -i      Takes in a input file with a relative location (example: -i common/translations.csv)
            """.trimIndent()
        }

    }

}