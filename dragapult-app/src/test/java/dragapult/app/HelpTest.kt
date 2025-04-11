package dragapult.app

import dragapult.app.harness.CommandLineHarness
import kotlin.test.Test
import kotlin.test.assertFails

class HelpTest : CommandLineHarness() {

    @Test
    fun `prints global help`() = simpleTest(
        prepare = { arrayOf("-h") },
        test = { it.execute() }
    )

    @Test
    fun `prints consume help`() = simpleTest(
        prepare = { arrayOf("consume", "-h") },
        test = { it.execute() }
    )

    @Test
    fun `prints generate help`() = simpleTest(
        prepare = { arrayOf("generate", "-h") },
        test = {
            val out = withOutputStream {
                it.execute()
            }
            assert(out.isNotEmpty())
        }
    )

    @Test
    fun `fails on unknown command`() = simpleTest(
        prepare = { arrayOf("-h") },
        test = {
            assertFails {
                it.execute()
            }
        }
    )

}