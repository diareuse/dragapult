package dev.chainmail.dragapult.write

import dev.chainmail.dragapult.Exit
import dev.chainmail.dragapult.args.Flags
import java.io.File

abstract class AbstractFileWriter : FileWriter {

    abstract fun getFileName(language: String): String
    abstract fun open(file: File)

    protected fun getOrCreateFile(dir: File, language: String): File {
        val filename = getFileName(language)
        val file = File(dir, filename)

        if (Flags.isDebug && (file.exists() || file.isFile)) {
            println(">! File (name=$filename) already exists, it will be overwritten.")
        }

        file.parentFile?.ensureDirExists()
        return file.ensureFileExists().apply(::open)
    }

    protected fun File.ensureDirExists() = apply {
        if (exists() && isDirectory) return@apply
        deleteRecursively()
        if (!mkdirs()) {
            Exit.directoryCannotBeCreated()
        }
    }

    protected fun File.ensureFileExists() = apply {
        if (exists() && isFile) return@apply
        deleteRecursively()
        if (!createNewFile()) {
            Exit.fileCannotBeCreated()
        }
    }

}