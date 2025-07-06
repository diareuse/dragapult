package app.dragapult.definition

import java.net.URL

interface RemoteDefinitionDeclaration : BaseDefinitionDeclaration {
    val url: URL
    val headers: Map<String, List<String>>?
    val requestMethod: String
}