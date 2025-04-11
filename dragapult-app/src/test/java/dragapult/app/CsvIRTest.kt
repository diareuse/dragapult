package dragapult.app

import dragapult.app.ir.csv.ReaderCsvIR
import dragapult.app.ir.csv.WriterCsvIR
import dragapult.app.ir.json.ReaderJsonIR
import dragapult.app.ir.json.WriterJsonIR
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