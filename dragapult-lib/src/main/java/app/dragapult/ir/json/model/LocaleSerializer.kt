package app.dragapult.ir.json.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

class LocaleSerializer : KSerializer<Locale> {
    override val descriptor = PrimitiveSerialDescriptor("locale", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): Locale {
        return Locale.forLanguageTag(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Locale) {
        encoder.encodeString(value.toLanguageTag())
    }
}