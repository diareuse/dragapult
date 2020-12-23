package dev.chainmail.dragapult

import dev.chainmail.dragapult.args.Arguments
import dev.chainmail.dragapult.args.Flags
import dev.chainmail.dragapult.format.FileFormatter
import dev.chainmail.dragapult.parse.FileParser
import dev.chainmail.dragapult.write.FileWriter
import java.lang.management.ManagementFactory
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
class App constructor(
    private val args: Array<String>
) {

    suspend fun start() {
        val arguments = measureTimedValue { Arguments.findIn(args) }
            .getAndPrint("arguments")

        val parser = FileParser(arguments.inputFormat, arguments.inputSeparator)
        val translations = measureTimedValue { parser.parse(arguments.input.file) }
            .getAndPrint("parsing")

        val formatter = FileFormatter(arguments.outputFormat, arguments.outputComment)
        val lines = measureTimedValue { formatter.format(translations) }
            .getAndPrint("formatting")

        val writer = FileWriter(arguments.outputFormat, arguments.output)
        val files = measureTimedValue { writer.write(lines) }
            .getAndPrint("writing files")

        if (Flags.isDebug) {
            println()
            println("Generated these files:")
            files.forEach {
                println(it.absolutePath)
            }
        }
    }

    private fun <T> TimedValue<T>.getAndPrint(name: String) = value.also {
        if (Flags.isPerformance) {
            println(">> Execution of \"$name\" took: ${duration.inMilliseconds} ms")
            val memory = ManagementFactory.getMemoryMXBean().heapMemoryUsage
            println(">> Memory:\n\tUsed ${memory.used / 1000.0 / 1000.0} MB\n\tAvailable ${(memory.max - memory.used) / 1000.0 / 1000.0} MB")
        }
    }

}