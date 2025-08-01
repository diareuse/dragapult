package app.dragapult.android.model

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("resources")
internal data class Resources(
    val strings: List<StringDefinition> = emptyList()
)

