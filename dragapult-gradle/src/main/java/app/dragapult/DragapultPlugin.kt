@file:JvmName("DragapultPlugin")

package app.dragapult

import app.dragapult.android.forEachVariant
import app.dragapult.definition.LocalDefinitionDeclaration
import app.dragapult.definition.RemoteDefinitionDeclaration
import app.dragapult.extension.DragapultExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class DragapultPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create("dragapult", DragapultExtension::class.java)
        target.forEachVariant { variant ->
            for (definition in extension.definitions.getOrElse(emptyList())) when (definition) {
                is RemoteDefinitionDeclaration -> {
                    val task = GenerateStringsFromRemoteTask.create(
                        project = target,
                        variant = variant,
                        definition = definition
                    )
                    variant.sources.res?.addGeneratedSourceDirectory(task) { it.outputDirectory }
                }

                is LocalDefinitionDeclaration -> {
                    val task = GenerateStringsTask.create(
                        project = target,
                        variant = variant,
                        definition = definition
                    )
                    variant.sources.res?.addGeneratedSourceDirectory(task) { it.outputDirectory }
                }
            }
        }
    }

}