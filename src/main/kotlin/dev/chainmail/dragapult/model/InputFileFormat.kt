package dev.chainmail.dragapult.model

enum class InputFileFormat {
    CSV, JSON, TWINE;

    companion object {

        fun findValue(value: String?) = values()
            .firstOrNull { it.toString().equals(value, ignoreCase = true) }

    }
}