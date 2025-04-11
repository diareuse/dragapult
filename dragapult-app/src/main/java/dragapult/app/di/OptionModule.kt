package dragapult.app.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option

@Module
object OptionModule {

    @get:IntoSet
    @get:Provides
    val consume: Option = Option.builder()
        .argName("consume")
        .option("c")
        .desc("Consumes platform files into a common file type. Try \"consume --help\" for usage info.")
        .build()

    @get:IntoSet
    @get:Provides
    val generate: Option = Option.builder()
        .argName("generate")
        .option("g")
        .desc("Generates platform files from a common file type. Try \"generate --help\" for usage info.")
        .build()

    @get:IntoSet
    @get:Provides
    val help: Option = Option.builder()
        .option("h")
        .longOpt("help")
        .desc("Prints this help message.")
        .build()

    operator fun invoke(cli: CommandLine) = Parsed(cli)

    class Parsed(private val cli: CommandLine) {

        val consume
            get() = cli.hasOption(OptionModule.consume)

        val generate
            get() = cli.hasOption(OptionModule.generate)

        val help
            get() = cli.hasOption(OptionModule.help)

    }

}