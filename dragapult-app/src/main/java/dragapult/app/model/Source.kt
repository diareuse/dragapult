package dragapult.app.model

enum class Source {

    Json,
    Csv,
    Yaml;

    companion object {

        fun valuesString() = listOf("json", "csv", "yaml")

        fun valueOfOption(type: String) = when (type) {
            "json" -> Json
            "csv" -> Csv
            "yaml" -> Yaml
            else -> throw IllegalArgumentException("Unsupported file type $type")
        }

    }

}