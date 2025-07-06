package app.dragapult

import app.dragapult.definition.DefinitionDownloader
import app.dragapult.definition.RemoteDefinitionDeclaration
import app.dragapult.util.camelCase
import com.android.build.api.variant.Variant
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.net.URI
import java.net.URL

abstract class GenerateStringsFromRemoteTask : DefaultTask() {

    private val definition: RemoteDefinitionDeclaration
        get() = object : RemoteDefinitionDeclaration {
            override val url: URL
                get() = URI(this@GenerateStringsFromRemoteTask.url.get()).toURL()
            override val headers: Map<String, List<String>>?
                get() = this@GenerateStringsFromRemoteTask.headers.get()
            override val requestMethod: String
                get() = this@GenerateStringsFromRemoteTask.requestMethod.get()
            override val name: String?
                get() = this@GenerateStringsFromRemoteTask.taskName.get()
            override val inputFileType: String
                get() = this@GenerateStringsFromRemoteTask.inputFileType.get()
        }

    @get:Input
    abstract val url: Property<String>

    @get:Input
    abstract val headers: MapProperty<String, List<String>>

    @get:Input
    abstract val requestMethod: Property<String>

    @get:Input
    abstract val taskName: Property<String>

    @get:Input
    abstract val inputFileType: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun download() {
        val definition = definition
        val downloader = DefinitionDownloader(definition, outputDirectory.get().asFile)
        val file = downloader.download()
        val source = when (val f = definition.inputFileType) {
            Source.Json.label -> Source.Json
            Source.Csv.label -> Source.Csv
            Source.Yaml.label -> Source.Yaml
            else -> error("Unsupported input file type: $f")
        }
        val platform = Platform.Android
        val outputDir = outputDirectory.get().asFile
        val app = DaggerApp.factory().create(source, platform, file, outputDir)
        val dp = app.dragapult
        dp.convert()
    }

    // ---

    companion object {

        fun create(
            project: Project,
            variant: Variant,
            definition: RemoteDefinitionDeclaration
        ) = project.tasks.register(
            "generateDragapult-${variant.name}-${definition.name.orEmpty()}-RemoteStrings".camelCase(),
            GenerateStringsFromRemoteTask::class.java
        ) {
            it.url.set(definition.url.toString())
            it.headers.set(definition.headers)
            it.requestMethod.set(definition.requestMethod)
            it.taskName.set(definition.name)
            it.inputFileType.set(definition.inputFileType)
        }

    }

}