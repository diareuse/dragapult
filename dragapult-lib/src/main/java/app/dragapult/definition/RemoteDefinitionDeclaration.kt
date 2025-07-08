package app.dragapult.definition

import java.net.URL

@DefinitionDsl
interface RemoteDefinitionDeclaration : BaseDefinitionDeclaration {
    val url: URL
    val headers: Map<String, List<String>>?
    val requestMethod: String
}