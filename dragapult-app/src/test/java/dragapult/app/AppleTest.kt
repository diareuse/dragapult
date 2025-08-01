package dragapult.app

import dragapult.app.harness.CommandLineHarness
import kotlin.test.Test
import kotlin.test.assertEquals

class AppleTest : CommandLineHarness() {

    @Test
    fun `read from apple matches json IR`() = test(
        prepare = {
            arrayOf(
                "consume",
                "-i", inputResDir("output/apple"),
                "-o", outputFile(),
                "-t", "apple",
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
    fun `read from json IR matches apple`() = test(
        prepare = {
            arrayOf(
                "generate",
                "-i", inputResFile("ir/keys.json.ir"),
                "-o", outputDir(),
                "-t", "apple",
                "-s", "json"
            )
        },
        verify = { (_, output) ->
            output.walk().filter { it.isFile }.forEach {
                val expected = resourceFileAsString("output/apple/${it.relativeTo(output)}.out")
                val actual = it.reader().readText()
                assertEquals(
                    expected = expected,
                    actual = actual,
                    message = "${it.absolutePath}"
                )
            }
        }
    )

}