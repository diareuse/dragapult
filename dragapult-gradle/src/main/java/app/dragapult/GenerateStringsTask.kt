package app.dragapult

import app.dragapult.definition.LocalDefinitionDeclaration
import app.dragapult.util.capitalize
import com.android.build.api.variant.Variant
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*

abstract class GenerateStringsTask : DefaultTask() {

    @get:InputFile
    abstract val inputFile: RegularFileProperty

    @get:Optional
    @get:Input
    abstract val inputFileType: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generate() {
        val source = when (val f = inputFileType.orNull ?: inferFileType()) {
            Source.Json.label -> Source.Json
            Source.Csv.label -> Source.Csv
            Source.Yaml.label -> Source.Yaml
            else -> error("Unsupported input file type: $f")
        }
        val platform = Platform.Android
        val outputDir = outputDirectory.get().asFile
        val app = DaggerApp.factory().create(source, platform, inputFile.get().asFile, outputDir)
        val dp = app.dragapult
        dp.convert()
    }

    // ---

    private fun inferFileType() = when (val extension = inputFile.get().asFile.extension) {
        "json" -> Source.Json.label
        "csv" -> Source.Csv.label
        "yml", "yaml" -> Source.Yaml.label
        else -> error("Unsupported input file extension: $extension")
    }

    // ---

    companion object {

        fun create(
            project: Project,
            variant: Variant,
            definition: LocalDefinitionDeclaration
        ) = project.tasks.register(
            "generateDragapult${variant.name.capitalize()}${definition.name?.capitalize().orEmpty()}Strings",
            GenerateStringsTask::class.java
        ) {
            it.inputFile.set(definition.file)
            it.inputFileType.set(definition.inputFileType)
        }

    }

}