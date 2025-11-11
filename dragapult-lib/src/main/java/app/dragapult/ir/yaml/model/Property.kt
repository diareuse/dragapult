package app.dragapult.ir.yaml.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Property(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    val value: String
)