package dragapult.app

import java.util.*

sealed interface TranslationKeyIR {

    val key: String
    val metadata: Metadata
    val translations: MutableMap<Locale, String>

    interface Metadata {
        var comment: String?
        val properties: MutableMap<String, String>
    }

    companion object {
        operator fun invoke(key: String): TranslationKeyIR = TranslationKeyIRImpl(key)
    }

}

private data class TranslationKeyIRImpl(
    override val key: String
) : TranslationKeyIR {

    override val metadata: TranslationKeyIR.Metadata = Metadata()
    override val translations: MutableMap<Locale, String> = mutableMapOf()

    class Metadata : TranslationKeyIR.Metadata {
        override var comment: String? = null
        override val properties: MutableMap<String, String> = mutableMapOf()
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Metadata) return false

            if (comment != other.comment) return false
            if (properties != other.properties) return false

            return true
        }

        override fun hashCode(): Int {
            var result = comment?.hashCode() ?: 0
            result = 31 * result + properties.hashCode()
            return result
        }

        override fun toString(): String {
            return "(comment='$comment', properties=$properties)"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TranslationKeyIRImpl) return false

        if (key != other.key) return false
        if (metadata != other.metadata) return false
        if (translations != other.translations) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + metadata.hashCode()
        result = 31 * result + translations.hashCode()
        return result
    }

    override fun toString(): String {
        return "TranslationKeyIR(key='$key', metadata=$metadata, translations=$translations)"
    }


}