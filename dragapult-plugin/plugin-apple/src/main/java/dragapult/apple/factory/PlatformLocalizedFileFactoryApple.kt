package dragapult.apple.factory

import dragapult.apple.LocalizationTypeApple
import dragapult.apple.adapter.PlatformLocalizedFileAppleAdapter
import dragapult.core.LocalizationType
import dragapult.core.PlatformLocalizedFile
import java.io.File

class PlatformLocalizedFileFactoryApple : PlatformLocalizedFile.Factory {

    override val type: LocalizationType
        get() = LocalizationTypeApple

    override fun fromFile(file: File): PlatformLocalizedFile {
        return PlatformLocalizedFileAppleAdapter(file)
    }

    override fun fromDirectory(directory: File): Sequence<PlatformLocalizedFile> {
        require(directory.name == "resources") {
            "You should point the program to ../shared/resources directory"
        }
        return directory.listFiles().orEmpty()
            .asSequence()
            .filter { it.name.endsWith(".lproj") }
            .flatMap { it.listFiles().orEmpty().asSequence() }
            .filter { it.name.endsWith(".strings") }
            .map(::fromFile)
    }

}