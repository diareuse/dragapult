package app.dragapult.definition

import java.io.File

interface LocalDefinitionDeclaration : BaseDefinitionDeclaration {
    val file: File
}