package dev.chainmail.dragapult

import dev.chainmail.dragapult.args.Arguments
import dev.chainmail.dragapult.args.Flags
import dev.chainmail.dragapult.read.ReadSequence
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

        val reader = ReadSequence(arguments.input, arguments.inputFormat, arguments.inputSeparator)
        val sequence = measureTimedValue { reader.getSequence() }
            .getAndPrint("fetching sequence")

        val writer = FileWriter(arguments.outputFormat, arguments.output)

        writer.use { _ ->
            sequence.use { translations ->
                measureTimedValue { translations.forEach { writer.write(it) } }
                    .getAndPrint("writing files")
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