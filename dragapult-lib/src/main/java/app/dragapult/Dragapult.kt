package app.dragapult

import java.io.File

fun interface Dragapult {

    fun convert(
        source: FileKind,
        target: FileKind,
        ingress: File,
        egress: File
    )

}