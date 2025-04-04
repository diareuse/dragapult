package dragapult.app

import dragapult.app.v2.android.ReaderAndroid
import dragapult.app.v2.android.WriterAndroid
import dragapult.app.v2.ir.json.ReaderJsonIR
import dragapult.app.v2.ir.json.WriterJsonIR
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidTest : ConversionHarness() {

    @Test
    fun `read from android matches json IR`() = test(
        prepare = {
            val output = ByteArrayOutputStream()
            val reader = ReaderAndroid(resourceDir("android/res"))
            val writer = WriterJsonIR(output)
            TestSetup.ToIR(reader, writer, output)
        },
        test = { (reader, writer, output) ->
            reader.copyTo(writer)
            output.toString("UTF-8")
        },
        verify = { actual ->
            assertEquals(
                expected = resourceFileAsString("ir/keys.json.ir"),
                actual = actual
            )
        }
    )

    @Test
    fun `read from json IR matches android`() = test(
        prepare = {
            val reader = ReaderJsonIR(resourceFile("ir/keys.json.ir"))
            val outputDir = Files.createTempDirectory("android-test").toFile()
            val writer = WriterAndroid(outputDir)
            TestSetup.FromIR(reader, writer, outputDir)
        },
        test = { (reader, writer, outputDir) ->
            reader.copyTo(writer)
            outputDir
        },
        verify = { dir ->
            dir.walk().filter { it.isFile }.forEach {
                val expected = resourceFileAsString("output/android/res/${it.relativeTo(dir)}.out")
                val actual = it.reader().readText()
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    )

}