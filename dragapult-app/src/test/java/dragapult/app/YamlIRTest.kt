package dragapult.app

import app.dragapult.Preferences
import app.dragapult.ir.json.ReaderJsonIR
import app.dragapult.ir.json.WriterJsonIR
import app.dragapult.ir.json.di.JsonDepIRModule
import app.dragapult.ir.yaml.ReaderYamlIR
import app.dragapult.ir.yaml.WriterYamlIR
import dragapult.app.harness.ConversionHarness
import java.io.ByteArrayOutputStream
import kotlin.test.Test
import kotlin.test.assertEquals

class YamlIRTest : ConversionHarness() {

    @Test
    fun `read from yaml IR matches json IR`() = test(
        prepare = {
            val output = ByteArrayOutputStream()
            val reader = ReaderYamlIR(resourceFile("ir/keys.yaml.ir"))
            val format = JsonDepIRModule().json(Preferences.jsonIR())
            val writer = WriterJsonIR(output, format)
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
    fun `read from json IR matches yaml IR`() = test(
        prepare = {
            val json = JsonDepIRModule().json(Preferences.jsonIR())
            val reader = ReaderJsonIR(resourceFile("ir/keys.json.ir"), json)
            val output = ByteArrayOutputStream()
            val writer = WriterYamlIR(output)
            TestSetup.ToIR(reader, writer, output)
        },
        test = { (reader, writer, output) ->
            reader.copyTo(writer)
            output.toString("UTF-8")
        },
        verify = { actual ->
            assertEquals(
                expected = resourceFileAsString("ir/keys.yaml.ir"),
                actual = actual
            )
        }
    )

}