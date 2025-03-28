package dragapult.app

import dragapult.app.v2.reader.ReaderAndroid
import dragapult.app.v2.reader.ReaderJsonIR
import dragapult.app.v2.reader.WriterAndroid
import dragapult.app.v2.reader.WriterJsonIR
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidTest {

    @Test
    fun `read from android matches json IR`() {
        val reader = ReaderAndroid(File("src/test/resources/android/res"))
        val output = ByteArrayOutputStream()
        WriterJsonIR(output).use { writer ->
            for (item in reader) {
                writer.append(item)
            }
        }
        assertEquals(
            expected = output.toString("UTF-8"),
            actual = this::class.java.classLoader.getResourceAsStream("ir/keys.json")!!.bufferedReader().readText()
        )
    }

    @Test
    fun `read from json IR matches android`() {
        val reader = ReaderJsonIR(this::class.java.classLoader.getResourceAsStream("ir/keys.json"))
        val outputDir = Files.createTempDirectory("android-test").toFile()
        outputDir.deleteOnExit()
        WriterAndroid(outputDir).use { writer ->
            for (item in reader) {
                writer.append(item)
            }
        }
        outputDir.walk().filter { it.isFile }.forEach {
            val expected =
                this::class.java.classLoader.getResourceAsStream("output/android/res/" + it.relativeTo(outputDir).path)
                    .reader().readText()
            val actual = it.reader().readText()
            assertEquals(expected, actual)
        }
    }

}