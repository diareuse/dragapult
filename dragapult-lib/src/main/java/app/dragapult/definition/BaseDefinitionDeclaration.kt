package app.dragapult.definition

sealed interface BaseDefinitionDeclaration {
    val name: String?
    val inputFileType: String
}