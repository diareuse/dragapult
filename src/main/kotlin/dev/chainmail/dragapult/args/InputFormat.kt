package dev.chainmail.dragapult.args

import dev.chainmail.dragapult.log.expects
import dev.chainmail.dragapult.model.InputFileFormat
import kotlin.system.exitProcess

data class InputFormat(val format: InputFileFormat) {

    companion object : ArgumentDefinition<InputFormat> {
        override val callSign = "-iF"
        override fun getInstance(args: Array<String>): InputFormat {
            val index = args.indexOf(callSign)
            val stringFormat = args.getOrNull(index + 1)
            val format = InputFileFormat.findValue(stringFormat)

            if (index < 0) {
                return InputFormat(InputFileFormat.CSV)
            }

            if (format == null) {
                "format" expects "!= null" given format
                exitProcess(1)
            }

            return InputFormat(format)
        }
    }
}