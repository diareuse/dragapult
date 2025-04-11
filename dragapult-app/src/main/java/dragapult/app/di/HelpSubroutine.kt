package dragapult.app.di

import org.apache.commons.cli.Options

class HelpSubroutine(val options: Options) : Subroutine {

    fun print() {
        for (option in options.options) buildString {
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
    }

}