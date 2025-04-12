package dragapult.app

import dragapult.app.harness.CommandLineHarness
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonTest : CommandLineHarness() {

    @Test
    fun `read from json matches json IR`() = test(
        prepare = {
            arrayOf(
                "consume",
                "-i", inputResDir("output/json"),
                "-o", outputFile(),
                "-t", "json",
                "-r", "json"
            )
        },
        verify = { (_, output) ->
            assertEquals(
                expected = resourceFileAsString("ir/keys.json.ir"),
                actual = output.readText()
            )
        }
    )

    @Test
    fun `read from json IR matches json`() = test(
        prepare = {
            arrayOf(
                "generate",
                "-i", inputResFile("ir/keys.json.ir"),
                "-o", outputDir(),
                "-t", "json",
                "-s", "json"
            )
        },
        verify = { (_, output) ->
            output.walk().filter { it.isFile }.forEach {
                val expected = resourceFileAsString("output/json/${it.relativeTo(output)}.out")
                val actual = it.reader().readText()
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    )

}