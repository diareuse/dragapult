@file:JvmName("DragapultPlugin")

package app.dragapult

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import org.gradle.api.Plugin
import org.gradle.api.Project

class DragapultPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create("dragapult", DragapultExtension::class.java)
        forEachVariant(target) { variant ->
            val variantName = variant.name
            val generateStrings = target.tasks.register(
                "generateDragapult${variantName.replaceFirstChar { it.uppercase() }}Strings",
                GenerateStringsTask::class.java
            ) {
                it.inputFile.set(extension.inputFile)
                it.inputFileType.set(extension.inputFileType)
            }
            variant.sources.res?.addGeneratedSourceDirectory(generateStrings) { it.outputDirectory }
        }
    }

    private fun forEachVariant(project: Project, body: (variant: Variant) -> Unit) {
        if (
            !project.plugins.hasPlugin("com.android.application") &&
            !project.plugins.hasPlugin("com.android.library")
        ) {
            return
        }

        val extension = project.extensions.findByType(AndroidComponentsExtension::class.java) ?: return
        extension.onVariants { variant ->
            body(variant)
        }
    }

}