package dev.chainmail.dragapult

import kotlin.system.exitProcess

object Exit {

    fun ok(): Nothing = exitProcess(0)
    fun requiredArgumentMissing(): Nothing = exitProcess(1)
    fun expectedArgumentNotFound(): Nothing = exitProcess(2)
    fun directoryCannotBeCreated(): Nothing = exitProcess(3)
    fun fileCannotBeCreated(): Nothing = exitProcess(4)


}