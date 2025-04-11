package dragapult.app

import java.util.*

interface TranslationKeyIR {

    val key: String
    val metadata: Metadata?
    val translations: Map<Locale, String>

    interface Metadata {
        val comment: String?
        val properties: Map<String, String>?
    }

    enum class FileType {
        Android, Apple, Csv, Json, Yaml, Toml
    }

}
