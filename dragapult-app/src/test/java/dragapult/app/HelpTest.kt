package dragapult.app

import dragapult.app.harness.CommandLineHarness
import kotlin.test.Test
import kotlin.test.assertFails

class HelpTest : CommandLineHarness() {

    @Test
    fun `prints global help`() = simpleTest(
        prepare = { arrayOf("-h") },
        test = {
            val out = withOutputStream {
                it.command.execute()
            }
            println(out)
            assert(out.isNotEmpty())
        }
    )

    @Test
    fun `prints consume help`() = simpleTest(
        prepare = { arrayOf("consume", "-h") },
        test = {
            val out = withOutputStream {
                it.command.execute()
            }
            println(out)
            assert(out.isNotEmpty())
        }
    )

    @Test
    fun `prints generate help`() = simpleTest(
        prepare = { arrayOf("generate", "-h") },
        test = {
            val out = withOutputStream {
                it.command.execute()
            }
            println(out)
            assert(out.isNotEmpty())
        }
    )

    @Test
    fun `fails on unknown command`() = simpleTest(
        prepare = { arrayOf("beepboop") },
        test = {
            assertFails {
                it.command.execute()
            }
        }
    )

}