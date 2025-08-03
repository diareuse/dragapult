package app.dragapult.unity

interface UnityPreferences {
    val sharedCommentsLabel: String get() = "Shared Comments"
    val commentsLabel: String get() = "Comments"
    val propertiesLabel: String get() = "Properties"
    val keyLabel: String get() = "Key"
    val outputFileName: String get() = "strings.csv"
}