package app.dragapult

import app.dragapult.harness.GradleTestHarness
import org.gradle.testkit.runner.TaskOutcome
import kotlin.test.*

class DragapultPluginTest : GradleTestHarness() {

    @Test
    fun `task passes in pristine state`() = test(
        prepare = {
            resolve("build.gradle").writeContentOf("pristine/build.gradle")
            resolve("input.json").writeContentOf("pristine/input.json")
        },
        test = { withArguments("generateStrings") },
        verify = { assertEquals(TaskOutcome.SUCCESS, it.task(":generateStrings")?.outcome) }
    )

    @Test
    fun `task generates expected output`() = test(
        prepare = {
            resolve("build.gradle").writeContentOf("json-ir/build.gradle")
            resolve("input.json").writeContentOf("json-ir/input.json")
        },
        test = { withArguments("generateStrings") },
        verify = {
            assertEquals(TaskOutcome.SUCCESS, it.task(":generateStrings")?.outcome)
            val actual = resolve("build/generated").walk()
                .filter { it.isFile }
                .onEach {
                    assertContains(it.parent, "values")
                    assertEquals("strings.xml", it.name)
                }
                .map { it.parent.substringAfter("-", "") }
                .toSet()
            assertContentEquals(setOf("ch", "cs", "", "vi", "fa"), actual.asIterable())
        }
    )

    @Test
    fun `task parses build variants`() = test(
        prepare = {
            resolve("build.gradle").writeContentOf("android/build.gradle")
            resolve("settings.gradle").writeContentOf("android/settings.gradle")
            resolve("input.json").writeContentOf("android/input.json")
        },
        test = { withArguments("generateDebugStrings", "generateReleaseStrings") },
        verify = {
            assertEquals(TaskOutcome.SUCCESS, it.task(":generateDebugStrings")?.outcome)
            assertEquals(TaskOutcome.SUCCESS, it.task(":generateReleaseStrings")?.outcome)
            assertTrue(resolve("build/generated/res/resValues/debug").exists())
            assertTrue(resolve("build/generated/res/resValues/release").exists())
        }
    )

}