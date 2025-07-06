package app.dragapult.extension

import app.dragapult.definition.LocalDefinitionDeclaration
import java.io.File

class LocalDefinitionExtension(
    override val name: String?
) : LocalDefinitionDeclaration {
    override lateinit var file: File
    override var inputFileType: String = Unset
        get() {
            field == Unset || return field
            return file.extension
        }

    companion object {
        private const val Unset = "unset"
    }
}