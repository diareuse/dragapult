package app.dragapult.ir.yaml.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Key(
    @SerialName("key")
    val name: String,
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("properties")
    val properties: List<Property>? = null,
    @SerialName("translations")
    val translations: Map<String, String>
)