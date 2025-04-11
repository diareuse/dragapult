package dragapult.app.di

import dagger.Module
import dagger.Provides
import dragapult.app.util.printHelp
import org.apache.commons.cli.CommandLineParser
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import javax.inject.Provider

@Module(includes = [OptionsModule::class])
class CommandLineModule {

    @Provides
    fun parser(): CommandLineParser = DefaultParser()

    @Provides
    fun commandLine(
        args: Array<out String>,
        parser: CommandLineParser,
        options: Options,
        generate: Provider<Options>,
        consume: Provider<Options>
    ): Subroutine {
        val cli = parser.parse(options, args)
        val opt = OptionModule(cli)
        val subargs = Array(args.size - 1) { args[it + 1] }
        return when {
            opt.help -> options.printHelp()
            opt.consume -> OptionConsumeModule(parser.parse(consume.get(), subargs))
            opt.generate -> OptionGenerateModule(parser.parse(generate.get(), subargs))
            else -> error("Unknown argument")
        }
    }

}