package dragapult.app

fun main(args: Array<out String>) {
    val app = DaggerApp.factory().create(args)
    app.command.execute()
}
