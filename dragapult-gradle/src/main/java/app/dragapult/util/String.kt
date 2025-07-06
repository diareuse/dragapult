package app.dragapult.util

fun String.capitalize() = replaceFirstChar { it.uppercase() }
fun String.decapitalize() = replaceFirstChar { it.lowercase() }
fun String.camelCase() = split('_', '-', ' ').joinToString("") { it.capitalize() }.decapitalize()