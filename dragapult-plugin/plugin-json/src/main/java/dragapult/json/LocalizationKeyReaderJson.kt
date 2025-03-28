package dragapult.json

import dragapult.core.LocalizationKeyReader
import dragapult.core.LocalizationType
import java.io.File
import java.util.*

class LocalizationKeyReaderJson(
    private val file: File
) : LocalizationKeyReader {

    override val keys: Sequence<String>
        get() = sequence {
            TODO()
        }

    override fun read(key: String): Sequence<Pair<Locale, String>> {
        return sequence {
            TODO()
        }
    }

    // ---

    class Factory : LocalizationKeyReader.Factory {

        override val type: LocalizationType
            get() = LocalizationTypeJson

        override fun create(file: File): LocalizationKeyReader {
            return LocalizationKeyReaderJson(file)
        }

    }

}