package dragapult.app.di

import dagger.Module
import dagger.Provides
import jakarta.inject.Provider
import org.apache.commons.cli.*

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
            opt.help -> HelpSubroutine(options)
            opt.consume -> try {
                OptionConsumeModule(parser.parse(consume.get(), subargs))
            } catch (_: MissingOptionException) {
                HelpSubroutine(consume.get())
            }

            opt.generate -> try {
                OptionGenerateModule(parser.parse(generate.get(), subargs))
            } catch (_: MissingOptionException) {
                HelpSubroutine(generate.get())
            }

            else -> error("Unknown options, please use -h or --help to print help message")
        }
    }

}