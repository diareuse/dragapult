package app.dragapult.android

import java.util.*

interface AndroidPreferences {
    val defaultLocale: Locale get() = Locale.ENGLISH
    val outputFileName: String get() = "strings.xml"
    val setDefaultLocaleExplicitly: Boolean get() = false
}