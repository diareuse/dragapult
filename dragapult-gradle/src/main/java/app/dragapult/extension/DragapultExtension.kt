package app.dragapult.extension

import app.dragapult.definition.BaseDefinitionDeclaration
import groovy.lang.Closure
import org.gradle.api.provider.ListProperty

abstract class DragapultExtension {

    abstract val definitions: ListProperty<BaseDefinitionDeclaration>

    fun remote(
        name: String,
        block: Closure<RemoteDefinitionExtension>
    ) = definitions.add(RemoteDefinitionExtension(name).apply { block.call(this) })

    fun local(
        name: String,
        block: Closure<LocalDefinitionExtension>
    ) = definitions.add(LocalDefinitionExtension(name).apply { block.call(this) })

}