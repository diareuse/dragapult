package app.dragapult.harness

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.nio.file.Files

abstract class GradleTestHarness {

    private lateinit var testDir: File

    @BeforeEach
    fun prepareInternal() {
        testDir = Files.createTempDirectory("dragapult").toFile()
    }

    @AfterEach
    fun tearDownInternal() {
        testDir.deleteRecursively()
    }

    fun test(
        prepare: File.() -> Unit,
        test: GradleRunner.() -> GradleRunner,
        verify: File.(BuildResult) -> Unit
    ) {
        prepare(testDir)

        val result = GradleRunner.create()
            .withProjectDir(testDir)
            .withPluginClasspath()
            .test()
            .build()

        testDir.verify(result)
    }

    // ---

    protected fun File.writeContentOf(name: String) {
        val loader = this@GradleTestHarness::class.java.classLoader
        outputStream().use { output ->
            loader.getResourceAsStream(name)!!.use { input ->
                input.copyTo(output)
            }
        }
    }

}