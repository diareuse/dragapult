package dev.chainmail.dragapult.args

object Flags {

    var isPerformance: Boolean = false
        private set

    var isDebug: Boolean = false
        private set

    var isHelp: Boolean = false
        private set

    operator fun invoke(args: Array<String>) {
        isPerformance = args.contains("-fPerf")
        isDebug = args.contains("-fDebug")
        isHelp = args.contains("-h")
    }

}