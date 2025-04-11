package dragapult.app.harness

import dragapult.app.DaggerApp
import java.io.File
import java.nio.file.Files
import kotlin.io.path.absolutePathString

abstract class CommandLineHarness {

    private val loader get() = this::class.java.classLoader

    fun resourceFile(name: String) = loader.getResourceAsStream(name)!!
    fun resourceFileAsString(name: String) = resourceFile(name).reader().readText()

    fun test(
        prepare: TestSetupScope.() -> Array<out String>,
        verify: (TestSetup) -> Unit
    ) {
        var input: File? = null
        var output: File? = null
        val scope = object : TestSetupScope {
            override fun input(path: String) = path.apply {
                input = File(this)
            }

            override fun output(path: String) = path.apply {
                output = File(this)
            }
        }
        val app = DaggerApp.factory().create(scope.prepare())
        val setup = TestSetup(
            input = checkNotNull(input),
            output = checkNotNull(output)
        )
        val command = app.command
        command.execute()
        verify(setup)
    }

    fun TestSetupScope.inputResDir(path: String) = input("src/test/resources/$path")
    fun TestSetupScope.inputResFile(path: String) = inputResDir(path)
    fun TestSetupScope.outputFile() = output(Files.createTempFile("output", "").absolutePathString())
    fun TestSetupScope.outputDir() = output(Files.createTempDirectory("output").absolutePathString())

    interface TestSetupScope {
        fun input(path: String): String
        fun output(path: String): String
    }

    data class TestSetup(
        val input: File,
        val output: File
    )

}