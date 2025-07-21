package dragapult.app

import dragapult.app.harness.CommandLineHarness
import kotlin.test.Test
import kotlin.test.assertEquals

class UnityIRTest : CommandLineHarness() {

    @Test
    fun `read from unity matches json IR`() = test(
        prepare = {
            arrayOf(
                "consume",
                "-i", inputResDir("output/unity"),
                "-o", outputFile(),
                "-t", "unity",
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
    fun `read from json IR matches unity`() = test(
        prepare = {
            arrayOf(
                "generate",
                "-i", inputResFile("ir/keys.json.ir"),
                "-o", outputDir(),
                "-t", "unity",
                "-s", "json"
            )
        },
        verify = { (_, output) ->
            output.walk().filter { it.isFile }.forEach {
                val expected = resourceFileAsString("output/unity/${it.relativeTo(output)}")
                val actual = it.reader().readText()
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    )

}