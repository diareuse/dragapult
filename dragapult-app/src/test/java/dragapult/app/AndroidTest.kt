package dragapult.app

import dragapult.app.harness.CommandLineHarness
import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidTest : CommandLineHarness() {

    @Test
    fun `read from android matches json IR`() = test(
        prepare = {
            arrayOf(
                "consume",
                "-i", inputResDir("output/android"),
                "-o", outputFile(),
                "-t", "android",
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
    fun `read from json IR matches android`() = test(
        prepare = {
            arrayOf(
                "generate",
                "-i", inputResFile("ir/keys.json.ir"),
                "-o", outputDir(),
                "-t", "android",
                "-s", "json"
            )
        },
        verify = { (_, output) ->
            output.walk().filter { it.isFile }.forEach {
                val expected = resourceFileAsString("output/android/${it.relativeTo(output)}.out")
                val actual = it.reader().readText()
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    )

}