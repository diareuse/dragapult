package app.dragapult.util

fun String.ensurePrefix(target: Char, prefix: Char = '\\') = buildString {
    val self = this@ensurePrefix
    for (i in self.indices) {
        if (self[i] == target && last() != prefix) {
            append(prefix)
        }
        append(self[i])
    }
}

fun String.removePrefix(target: Char, prefix: Char = '\\') = buildString {
    val self = this@removePrefix
    for (i in self.indices) {
        if (self[i] == target && last() == prefix) {
            deleteCharAt(length - 1)
        }
        append(self[i])
    }
}