package dragapult.app

import app.dragapult.Preferences
import app.dragapult.ir.csv.ReaderCsvIR
import app.dragapult.ir.csv.WriterCsvIR
import app.dragapult.ir.csv.di.CsvDepIRModule
import app.dragapult.ir.json.ReaderJsonIR
import app.dragapult.ir.json.WriterJsonIR
import app.dragapult.ir.json.di.JsonDepIRModule
import dragapult.app.harness.ConversionHarness
import java.io.ByteArrayOutputStream
import kotlin.test.Test
import kotlin.test.assertEquals

class CsvIRTest : ConversionHarness() {

    @Test
    fun `read from csv IR matches json IR`() = test(
        prepare = {
            val output = ByteArrayOutputStream()
            val format = CsvDepIRModule().format(Preferences.csvIR())
            val reader = ReaderCsvIR(resourceFile("ir/keys.csv.ir"), format)
            val json = JsonDepIRModule().json(Preferences.jsonIR())
            val writer = WriterJsonIR(output, json)
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
    fun `read from json IR matches csv IR`() = test(
        prepare = {
            val output = ByteArrayOutputStream()
            val json = JsonDepIRModule().json(Preferences.jsonIR())
            val reader = ReaderJsonIR(resourceFile("ir/keys.json.ir"), json)
            val format = CsvDepIRModule().format(Preferences.csvIR())
            val writer = WriterCsvIR(output, format)
            TestSetup.ToIR(reader, writer, output)
        },
        test = { (reader, writer, output) ->
            reader.copyTo(writer)
            output.toString("UTF-8")
        },
        verify = { actual ->
            assertEquals(
                expected = resourceFileAsString("ir/keys.csv.ir"),
                actual = actual
            )
        }
    )

}