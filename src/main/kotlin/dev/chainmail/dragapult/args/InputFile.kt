package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.log.expects
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

data class InputFile(val file: File) {

    constructor(file: String) : this(file = File(Paths.get(".").toAbsolutePath().normalize().toFile(), file))

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

        override fun toString(): String {
            return """
                -i      Takes in a input file with a relative location (example: -i common/translations.csv)
            """.trimIndent()
        }

    }

}