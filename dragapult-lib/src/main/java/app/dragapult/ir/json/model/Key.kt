package app.dragapult.ir.json.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Key(
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("properties")
    val properties: Map<String, String>? = null,
    @SerialName("keys")
    val translations: Map<@Serializable(with = LocaleSerializer::class) Locale, String>
)