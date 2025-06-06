package app.dragapult

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

interface DragapultExtension {
    val inputFile: RegularFileProperty
    val inputFileType: Property<String>
    val outputFileType: Property<String>
}