package dragapult.json

import dragapult.core.LocalizationKeyWriter
import dragapult.core.LocalizationType
import java.io.File
import java.util.*

class LocalizationKeyWriterJson(
    private val file: File
) : LocalizationKeyWriter {

    override fun write(key: String, locale: Locale, value: String) {

    }

    override fun close() {

    }

    // ---

    class Factory : LocalizationKeyWriter.Factory {

        override val type: LocalizationType
            get() = LocalizationTypeJson

        override fun create(file: File): LocalizationKeyWriter {
            return LocalizationKeyWriterJson(file)
        }

    }

}