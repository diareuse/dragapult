package dev.chainmail.dragapult.parse

import dev.chainmail.dragapult.log.expects
import dev.chainmail.dragapult.model.KeyedTranslation
import java.io.File
import kotlin.system.exitProcess

abstract class AbstractFileParser : FileParser {

    final override suspend fun parse(file: File): List<KeyedTranslation> {
        val exists = file.exists()

        if (!exists) {
            "input file" expects "to exist" given file.absolutePath
            exitProcess(1)
        }

        return tryParse(file)
    }

    abstract suspend fun tryParse(file: File): List<KeyedTranslation>

}