package dragapult.app.util

import org.apache.commons.cli.Options
import kotlin.system.exitProcess

fun Options.printHelp(): Nothing {
    for (option in options) buildString {
        when {
            option.argName != null -> append(option.argName)
            else -> {
                append('-')
                append(option.opt)
            }
        }

        if (option.hasLongOpt()) {
            append('\t')
            append("--")
            appendLine(option.longOpt)
        } else {
            appendLine()
        }

        append('\t')
        appendLine(option.description)
    }.apply(::println)
    exitProcess(0)
}