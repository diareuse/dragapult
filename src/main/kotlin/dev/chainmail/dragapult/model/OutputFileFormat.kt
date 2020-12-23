package dev.chainmail.dragapult.model

enum class OutputFileFormat {
    ANDROID, APPLE, JSON;

    companion object {

        fun findValue(value: String?) = values()
            .firstOrNull { it.toString().equals(value, ignoreCase = true) }

    }
}