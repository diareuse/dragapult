package dragapult.app

import app.dragapult.ir.csv.ReaderCsvIR
import app.dragapult.ir.csv.WriterCsvIR
import app.dragapult.ir.json.ReaderJsonIR
import app.dragapult.ir.json.WriterJsonIR
import dragapult.app.harness.ConversionHarness
import java.io.ByteArrayOutputStream
import kotlin.test.Test
import kotlin.test.assertEquals

class CsvIRTest : ConversionHarness() {

    @Test
    fun `read from csv IR matches json IR`() = test(
        prepare = {
            val output = ByteArrayOutputStream()
            val reader = ReaderCsvIR(resourceFile("ir/keys.csv.ir"))
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
    fun `read from json IR matches csv IR`() = test(
        prepare = {
            val output = ByteArrayOutputStream()
            val reader = ReaderJsonIR(resourceFile("ir/keys.json.ir"))
            val writer = WriterCsvIR(output)
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