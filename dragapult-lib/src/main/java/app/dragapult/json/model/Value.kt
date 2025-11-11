package app.dragapult.json.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Value(
    @SerialName("_c")
    val comment: String? = null,
    @SerialName("_p")
    val parameters: Map<String, String>? = null,
    @SerialName("value")
    val value: String
)