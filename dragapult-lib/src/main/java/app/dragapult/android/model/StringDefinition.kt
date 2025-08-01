package app.dragapult.android.model

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue
import nl.adaptivity.xmlutil.util.CompactFragment

@Serializable
@XmlSerialName("string")
internal data class StringDefinition(
    @XmlElement(false)
    @XmlSerialName("name")
    val name: String,
    @XmlSerialName("translatable")
    @XmlElement(false)
    val translatable: Boolean = true,
    @XmlSerialName("comment")
    @XmlElement(false)
    val comment: String?,
    @XmlElement(false)
    val parameters: Map<String, String>? = null,
    @XmlValue
    val content: CompactFragment = CompactFragment("")
)