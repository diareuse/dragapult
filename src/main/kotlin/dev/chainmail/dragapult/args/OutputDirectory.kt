package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.log.expects
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

data class OutputDirectory(val dir: File) {

    constructor(dir: String) : this(dir = File(Paths.get(".").toAbsolutePath().normalize().toFile(), dir))

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