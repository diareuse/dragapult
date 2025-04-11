package dragapult.app.di

import dagger.Module
import dagger.Provides
import dragapult.app.util.printHelp
import org.apache.commons.cli.CommandLine
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
        @Generate
        generate: Provider<Options>,
        @Consume
        consume: Provider<Options>
    ): Subroutine {
        val cli = CommandLine.builder()
        val arg = args[0]
        for (it in options.options) {
            if (it.argName == arg || arg.contains(it.opt) || it.opt == "-$arg" || it.longOpt == "--$arg") {
                cli.addArg(arg)
                cli.addOption(it)
                break
            }
        }
        val opt = OptionModule(cli.build())
        val subargs = Array(args.size - 1) { args[it + 1] }
        return when {
            opt.help -> options.printHelp()
            opt.consume -> OptionConsumeModule(parser.parse(consume.get(), subargs))
            opt.generate -> OptionGenerateModule(parser.parse(generate.get(), subargs))
            else -> options.printHelp()
        }
    }

}