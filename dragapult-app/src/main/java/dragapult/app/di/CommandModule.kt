package dragapult.app.di

import app.dragapult.Dragapult
import app.dragapult.Preferences
import app.dragapult.di.DragapultModule
import dagger.Module
import dagger.Provides
import dragapult.app.Command

@Module(
    includes = [
        DragapultModule::class,
        CommandLineModule::class
    ]
)
class CommandModule {

    @Provides
    fun provide(
        subroutine: Subroutine,
        dragapult: Dragapult,
    ): Command = Command {
        when (val s = subroutine) {
            is HelpSubroutine -> return@Command s.print()
            is OptionConsumeModule.Parsed -> dragapult.convert(
                s.inputType,
                s.outputType,
                s.inputDirectory,
                s.outputFile,
                Preferences.static()
            )

            is OptionGenerateModule.Parsed -> dragapult.convert(
                s.inputType,
                s.outputType,
                s.inputFile,
                s.outputDirectory,
                Preferences.static()
            )
        }
    }

}