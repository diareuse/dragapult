package dev.chainmail.dragapult.log

import dev.chainmail.dragapult.args.Flags

class ExpectationLog(
    @JvmField val name: String,
    @JvmField val expectation: Any?
) {

    infix fun given(value: Any?) {
        if (Flags.isDebug) {
            println("Expected $name -> $expectation, but got $value")
        }
    }

}

infix fun String.expects(value: Any?) = ExpectationLog(this, value)