package app.dragapult.definition

import java.io.File

@DefinitionDsl
interface LocalDefinitionDeclaration : BaseDefinitionDeclaration {
    val file: File
}