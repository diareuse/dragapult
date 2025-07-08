package app.dragapult.harness

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.io.InputStream
import java.net.InetSocketAddress
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
        prepare: File.(HttpServer) -> Unit,
        test: GradleRunner.() -> GradleRunner,
        verify: File.(BuildResult) -> Unit
    ) {
        val server = HttpServer.create(InetSocketAddress(8081), 0)
        prepare(testDir, server)
        server.start()

        try {
            val result = GradleRunner.create()
                .withProjectDir(testDir)
                .withPluginClasspath()
                .test()
                .build()

            testDir.verify(result)
        } finally {
            server.stop(0)
        }
    }

    // ---

    protected fun resourceAsStream(name: String): InputStream {
        val loader = this@GradleTestHarness::class.java.classLoader
        return loader.getResourceAsStream(name)!!
    }

    protected fun File.writeContentOf(name: String) {
        outputStream().use { output ->
            resourceAsStream(name).use { input ->
                input.copyTo(output)
            }
        }
    }

    // ---

    protected fun HttpServer.addHandler(path: String, block: HttpExchange. () -> Unit) = createContext(path) {
        block(it)
    }

    protected fun HttpExchange.get(handler: () -> Unit) = when (requestMethod.lowercase()) {
        "get" -> handler()
        else -> respond(405)
    }

    protected fun HttpExchange.respond(code: Int, body: String? = null) {
        respond(code, body?.byteInputStream(), body?.length?.toLong() ?: 0)
    }

    protected fun HttpExchange.respond(
        code: Int,
        stream: InputStream?,
        responseLength: Long = if (stream == null) -1 else 0
    ) {
        sendResponseHeaders(code, responseLength)
        if (stream != null) responseBody.use { output ->
            stream.use { input -> input.copyTo(output) }
            output.flush()
        }
    }

}