import dev.chainmail.dragapult.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    launch(Dispatchers.Default) {
        val app = App(args)
        app.start()
    }
    Unit
}