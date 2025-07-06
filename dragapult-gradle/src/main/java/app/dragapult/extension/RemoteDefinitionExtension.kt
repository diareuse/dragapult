package app.dragapult.extension

import app.dragapult.definition.RemoteDefinitionDeclaration
import java.net.URI
import java.net.URL
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class RemoteDefinitionExtension(
    override val name: String
) : RemoteDefinitionDeclaration {

    override lateinit var url: URL
    override val headers = mutableMapOf<String, List<String>>()
    override var requestMethod: String = "GET"
    override lateinit var inputFileType: String

    // ---

    fun url(url: String) {
        this.url = URI(url).toURL()
    }

    fun headers(block: HeadersExtension.() -> Unit) = HeadersExtension().block()
    fun authorization(block: AuthorizationExtension.() -> Unit) = AuthorizationExtension().block()

    // ---

    inner class HeadersExtension {
        fun header(name: String, value: String) {
            headers[name] = headers[name].orEmpty() + value
        }
    }

    inner class AuthorizationExtension {

        fun bearer(token: String) = headers {
            header(name = "Authorization", value = "Bearer $token")
        }

        fun basic(base64: String) = headers {
            header(name = "Authorization", value = "Basic $base64")
        }

        fun digest(
            username: String,
            realm: String,
            uri: String,
            algorithm: String,
            nonce: String,
            nc: String,
            cnonce: String,
            qop: String,
            response: String,
            opaque: String
        ) = headers {
            header(
                name = "Authorization",
                value = "Digest username=\"$username\",realm=\"$realm\",uri=\"$uri\",algorithm=$algorithm,nonce=\"$nonce\",nc=$nc,cnonce=\"$cnonce\",qop=$qop,response=\"$response\",opaque=\"$opaque\""
            )
        }

        @OptIn(ExperimentalEncodingApi::class)
        fun basic(username: String, password: String) {
            basic(Base64.encode("$username:$password".encodeToByteArray()))
        }

    }

}