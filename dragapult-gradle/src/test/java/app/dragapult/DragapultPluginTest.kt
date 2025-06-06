package app.dragapult

import app.dragapult.harness.GradleTestHarness
import org.gradle.testkit.runner.TaskOutcome
import kotlin.test.*

class DragapultPluginTest : GradleTestHarness() {

    @Ignore("We're dropping support for non-android project in the first release")
    @Test
    fun `task passes in pristine state`() = test(
        prepare = {
            resolve("build.gradle").writeContentOf("pristine/build.gradle")
            resolve("input.json").writeContentOf("pristine/input.json")
        },
        test = { withArguments("generateStrings") },
        verify = { assertEquals(TaskOutcome.SUCCESS, it.task(":generateStrings")?.outcome) }
    )

    @Ignore("We're dropping support for non-android project in the first release")
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
            assertContentEquals(setOf("ch", "cs", "", "vi", "fa").sorted(), actual.sorted())
        }
    )

    @Test
    fun `task parses build variants`() = test(
        prepare = {
            resolve("build.gradle").writeContentOf("android/build.gradle")
            resolve("settings.gradle").writeContentOf("android/settings.gradle")
            resolve("input.json").writeContentOf("android/input.json")
        },
        test = { withArguments("generateDragapultDebugStrings", "generateDragapultReleaseStrings") },
        verify = {
            assertEquals(TaskOutcome.SUCCESS, it.task(":generateDragapultDebugStrings")?.outcome)
            assertEquals(TaskOutcome.SUCCESS, it.task(":generateDragapultReleaseStrings")?.outcome)
            assertTrue(
                actual = resolve("build/generated/res/generateDragapultDebugStrings").exists(),
                message = "Expected to see build/generated/res/resValues/debug directory, but the directory tree is following: ${walkBottomUp().joinToString { it.absolutePath }}"
            )
            assertTrue(
                actual = resolve("build/generated/res/generateDragapultReleaseStrings").exists(),
                message = "Expected to see build/generated/res/resValues/release directory, but the directory tree is following: ${walkBottomUp().joinToString { it.absolutePath }}"
            )
        }
    )

}