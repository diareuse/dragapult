package dragapult.json

import dragapult.core.Key
import dragapult.core.LocalizationReader
import dragapult.core.LocalizationType
import dragapult.core.Value
import java.io.File

class LocalizationReaderJson(
    private val file: File
) : LocalizationReader {

    override fun read(): Sequence<Pair<Key, Value>> {
        return sequence {
        }
    }

    class Factory : LocalizationReader.Factory {

        override val type: LocalizationType
            get() = LocalizationTypeJson

        override fun create(file: File): LocalizationReader {
            return LocalizationReaderJson(file)
        }

    }

}