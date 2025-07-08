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
        test = { withArguments("generateDragapultDebugDefaultStrings", "generateDragapultReleaseDefaultStrings") },
        verify = {
            val debug = checkNotNull(it.task(":generateDragapultDebugDefaultStrings")) {
                "Expected to see task ':generateDragapultDebugDefaultStrings', but were only ${it.tasks.joinToString { it.path }}"
            }
            val release = checkNotNull(it.task(":generateDragapultReleaseDefaultStrings")) {
                "Expected to see task ':generateDragapultReleaseDefaultStrings', but were only ${it.tasks.joinToString { it.path }}"
            }
            assertEquals(TaskOutcome.SUCCESS, debug.outcome)
            assertEquals(TaskOutcome.SUCCESS, release.outcome)
            assertTrue(
                actual = resolve("build/generated/res/generateDragapultDebugDefaultStrings").exists(),
                message = "Expected to see build/generated/res/resValues/debug directory, but the directory tree is following: ${walkBottomUp().joinToString { it.absolutePath }}"
            )
            assertTrue(
                actual = resolve("build/generated/res/generateDragapultReleaseDefaultStrings").exists(),
                message = "Expected to see build/generated/res/resValues/release directory, but the directory tree is following: ${walkBottomUp().joinToString { it.absolutePath }}"
            )
        }
    )

    @Test
    fun `plugin works with kotlin`() = test(
        prepare = {
            resolve("build.gradle").writeContentOf("android/build.gradle.kts")
            resolve("settings.gradle").writeContentOf("android/settings.gradle")
            resolve("input.json").writeContentOf("android/input.json")
        },
        test = { withArguments("generateDragapultDebugDefaultStrings", "generateDragapultReleaseDefaultStrings") },
        verify = {
            val debug = checkNotNull(it.task(":generateDragapultDebugDefaultStrings")) {
                "Expected to see task ':generateDragapultDebugDefaultStrings', but were only ${it.tasks.joinToString { it.path }}"
            }
            val release = checkNotNull(it.task(":generateDragapultReleaseDefaultStrings")) {
                "Expected to see task ':generateDragapultReleaseDefaultStrings', but were only ${it.tasks.joinToString { it.path }}"
            }
            assertEquals(TaskOutcome.SUCCESS, debug.outcome)
            assertEquals(TaskOutcome.SUCCESS, release.outcome)
            assertTrue(
                actual = resolve("build/generated/res/generateDragapultDebugDefaultStrings").exists(),
                message = "Expected to see build/generated/res/resValues/debug directory, but the directory tree is following: ${walkBottomUp().joinToString { it.absolutePath }}"
            )
            assertTrue(
                actual = resolve("build/generated/res/generateDragapultReleaseDefaultStrings").exists(),
                message = "Expected to see build/generated/res/resValues/release directory, but the directory tree is following: ${walkBottomUp().joinToString { it.absolutePath }}"
            )
        }
    )

    @Test
    fun `task downloads and generates expected output`() = test(
        prepare = { server ->
            resolve("build.gradle").writeContentOf("download/build.gradle")
            resolve("settings.gradle").writeContentOf("download/settings.gradle")
            server.addHandler("/input.json") {
                check(requestMethod.lowercase() == "get")
                check(requestHeaders.getFirst("X-Foo") == "bar")
                check(requestHeaders.getFirst("Authorization") == "Bearer i-am-token")
                respond(200, resourceAsStream("download/input.json"))
            }
        },
        test = {
            withArguments(
                "generateDragapultDebugDefaultRemoteStrings",
                "generateDragapultReleaseDefaultRemoteStrings"
            )
        },
        verify = {
            assertEquals(TaskOutcome.SUCCESS, it.task(":generateDragapultDebugDefaultRemoteStrings")?.outcome)
            assertEquals(TaskOutcome.SUCCESS, it.task(":generateDragapultReleaseDefaultRemoteStrings")?.outcome)
            val actual = resolve("build/generated").walk()
                .filter { it.isFile }
                .filter { it.parent.contains("values") }
                .onEach { assertEquals("strings.xml", it.name) }
                .map { it.parent.substringAfter("-", "") }
                .toSet()
            assertContentEquals(setOf("ch", "cs", "", "vi", "fa").sorted(), actual.sorted())
        }
    )

}