package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.Exit
import dev.chainmail.dragapult.log.expects

data class InputSeparator(val separator: String) {

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
                Exit.expectedArgumentNotFound()
            }

            return InputSeparator(separator)
        }

        override fun toString(): String {
            return """
                -iS     Specifies input separator. This parameter is taken in account only when -iF csv. Any non whitespace character is permitted. Default is "," (without quotes)
            """.trimIndent()
        }

    }

}