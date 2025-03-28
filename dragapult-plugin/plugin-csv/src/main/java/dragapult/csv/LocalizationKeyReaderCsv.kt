package dragapult.csv

import dragapult.core.LocalizationKeyReader
import dragapult.core.LocalizationType
import java.io.File
import java.util.*

class LocalizationKeyReaderCsv(
    private val file: File
) : LocalizationKeyReader {

    override val keys: Sequence<String>
        get() = TODO()

    override fun read(key: String): Sequence<Pair<Locale, String>> {
        return sequence {
            TODO()
        }
    }

    class Factory : LocalizationKeyReader.Factory {

        override val type: LocalizationType
            get() = LocalizationTypeCsv

        override fun create(file: File): LocalizationKeyReader {
            return LocalizationKeyReaderCsv(file)
        }

    }

    companion object {

        private val keyRegex = Regex("^[kK][eE][yY][sS]?")

    }

}