package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.Exit
import dev.chainmail.dragapult.log.expects
import java.io.File

data class OutputDirectory(val dir: File) {

    constructor(dir: String) : this(dir = File(dir))

    init {
        if (Flags.isDebug) {
            println("Output dir: ${dir.absolutePath}")
        }
    }

    companion object : ArgumentDefinition<OutputDirectory> {

        override val callSign = "-o"

        override fun getInstance(args: Array<String>): OutputDirectory {
            val index = args.indexOf(callSign)
            val path = args.getOrNull(index + 1)

            if (index < 0) {
                Exit.requiredArgumentMissing()
            }

            if (path == null) {
                "path" expects "!= null" given path
                Exit.expectedArgumentNotFound()
            }

            return OutputDirectory(path)
        }

        override fun toString(): String {
            return """
                -o      Takes in an output dir with a relative location (example: -o app/src/main/res/)
            """.trimIndent()
        }
    }

}