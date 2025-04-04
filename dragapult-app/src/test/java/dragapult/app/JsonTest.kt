package dragapult.app

import dragapult.app.v2.ir.json.ReaderJsonIR
import dragapult.app.v2.ir.json.WriterJsonIR
import dragapult.app.v2.json.ReaderJson
import dragapult.app.v2.json.WriterJson
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonTest : ConversionHarness() {

    @Test
    fun `read from json matches json IR`() = test(
        prepare = {
            val output = ByteArrayOutputStream()
            val reader = ReaderJson(resourceDir("json"))
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
    fun `read from json IR matches json`() = test(
        prepare = {
            val reader = ReaderJsonIR(resourceFile("ir/keys.json.ir"))
            val outputDir = Files.createTempDirectory("json-test").toFile()
            val writer = WriterJson(outputDir)
            TestSetup.FromIR(reader, writer, outputDir)
        },
        test = { (reader, writer, outputDir) ->
            reader.copyTo(writer)
            outputDir
        },
        verify = { dir ->
            dir.walk().filter { it.isFile }.forEach {
                val expected = resourceFileAsString("output/json/${it.relativeTo(dir)}.out")
                val actual = it.reader().readText()
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    )

}