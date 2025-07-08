package app.dragapult.extension

import app.dragapult.definition.BaseDefinitionDeclaration
import org.gradle.api.provider.ListProperty

@ExtensionDsl
abstract class DragapultExtension {

    abstract val definitions: ListProperty<BaseDefinitionDeclaration>

    fun remote(
        name: String,
        block: RemoteDefinitionExtension.() -> Unit
    ) = definitions.add(RemoteDefinitionExtension(name).apply(block))

    fun local(
        name: String,
        block: LocalDefinitionExtension.() -> Unit
    ) = definitions.add(LocalDefinitionExtension(name).apply(block))

}