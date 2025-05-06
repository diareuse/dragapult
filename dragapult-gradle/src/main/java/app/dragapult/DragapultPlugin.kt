@file:JvmName("DragapultPlugin")

package app.dragapult

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class DragapultPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create("dragapult", DragapultExtension::class.java)
        forEachVariant(target) { variant ->
            val generateStrings = target.tasks.register("generate${variant}Strings", GenerateStringsTask::class.java) {
                it.inputFile.set(extension.inputFile)
                it.outputDirectory.set(extension.outputDirectory)
                it.inputFileType.set(extension.inputFileType)
                it.outputFileType.set(extension.outputFileType)
                it.buildVariant.set(variant)
            }
            println(generateStrings.name)
            target.tasks.findByName("preBuild")?.dependsOn(generateStrings)
        }
    }

    private fun forEachVariant(project: Project, body: (name: String) -> Unit) {
        if (
            !project.plugins.hasPlugin("com.android.application") &&
            !project.plugins.hasPlugin("com.android.library")
        ) {
            return body("")
        }

        val extension = project.extensions.findByType(AndroidComponentsExtension::class.java)
        if (extension == null) {
            return body("")
        }
        extension.onVariants { variant ->
            body(variant.name.replaceFirstChar { it.uppercase() })
        }
    }

}