package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.log.expects
import kotlin.system.exitProcess

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
                exitProcess(1)
            }

            return InputSeparator(separator)
        }
    }

}