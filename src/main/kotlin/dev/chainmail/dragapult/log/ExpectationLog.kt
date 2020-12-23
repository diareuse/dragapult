package dev.chainmail.dragapult.log

class ExpectationLog(
    @JvmField val name: String,
    @JvmField val expectation: Any?
) {

    infix fun given(value: Any?) {
        println("Expected $name -> $expectation, but got $value")
    }

}

infix fun String.expects(value: Any?) = ExpectationLog(this, value)