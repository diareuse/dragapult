package app.dragapult.json

interface JsonPreferences {
    val explicitNulls: Boolean get() = false
    val isLenient: Boolean get() = true
    val outputFileName: String get() = "strings.json"
}