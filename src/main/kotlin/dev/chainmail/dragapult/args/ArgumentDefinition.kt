package dev.chainmail.dragapult.args

interface ArgumentDefinition<Instance> {

    val callSign: String

    fun getInstance(args: Array<String>): Instance?

}