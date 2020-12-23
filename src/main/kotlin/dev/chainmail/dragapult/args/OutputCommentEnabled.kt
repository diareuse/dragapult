package dev.chainmail.dragapult.args

data class OutputCommentEnabled(val isEnabled: Boolean = false) {

    companion object : ArgumentDefinition<OutputCommentEnabled> {
        override val callSign = "-oC"
        override fun getInstance(args: Array<String>): OutputCommentEnabled {
            val isEnabled = args.contains(callSign)
            return OutputCommentEnabled(isEnabled = isEnabled)
        }
    }

}