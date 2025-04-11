package dragapult.app

enum class Platform {

    Android,
    Apple,
    Json;

    companion object {

        fun valuesString() = listOf("apple", "android", "json")

        fun valueOfOption(type: String) = when (type) {
            "apple" -> Apple
            "android" -> Android
            "json" -> Json
            else -> throw IllegalArgumentException("Unsupported file type $type")
        }

    }

}