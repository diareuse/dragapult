package dragapult.app

fun main(args: Array<out String>) {
    DaggerApp.factory().create(args).command.execute()
}
