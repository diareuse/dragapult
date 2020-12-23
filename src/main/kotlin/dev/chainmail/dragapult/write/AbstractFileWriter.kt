package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.args.Flags
import dev.chainmail.dragapult.format.FileInput
import dev.chainmail.dragapult.format.Language
import java.io.File
import kotlin.system.exitProcess

abstract class AbstractFileWriter : FileWriter {

    abstract fun getFileName(language: Language): String

    abstract suspend fun write(language: Language, lines: List<FileInput>): File

    final override suspend fun write(files: Map<Language, List<FileInput>>): List<File> {
        return files.map { write(it.key, it.value) }
    }

    protected fun getOrCreateFile(dir: File, language: Language): File {
        val filename = getFileName(language)
        val file = File(dir, filename)

        if (Flags.isDebug && (file.exists() || file.isFile)) {
            println(">! File (name=$filename) already exists, it will be overwritten.")
        }

        file.parentFile?.ensureDirExists()
        return file.ensureFileExists()
    }

    protected fun File.ensureDirExists() = apply {
        if (exists() && isDirectory) return@apply
        deleteRecursively()
        if (!mkdirs()) {
            exitProcess(3)
        }
    }

    protected fun File.ensureFileExists() = apply {
        if (exists() && isFile) return@apply
        deleteRecursively()
        if (!createNewFile()) {
            exitProcess(2)
        }
    }

}