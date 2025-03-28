package dragapult.app.v2

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

interface TranslationFileIR {
    val sections: Iterable<TranslationSectionIR>
}

interface TranslationSectionIR {
    val name: String?
    val keys: Iterable<TranslationKeyIR>
}