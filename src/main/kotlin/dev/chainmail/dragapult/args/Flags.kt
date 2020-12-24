package dev.chainmail.dragapult.args

object Flags {

    var isPerformance: Boolean = false
        private set

    var isDebug: Boolean = false
        private set

    var isHelp: Boolean = false
        private set

    var isHelpExample: Boolean = false
        private set

    operator fun invoke(args: Array<String>) {
        isPerformance = args.contains("-fPerf")
        isDebug = args.contains("-fDebug")
        isHelp = args.contains("-h")
        isHelpExample = args.contains("-hE")
    }

    override fun toString(): String {
        return """
            -fPerf  Enables performance metrics to be printed out
            -fDebug Enables debug messages
            -h      Prints this help message
            -hE     Prints example usage for all platforms
        """.trimIndent()
    }

}