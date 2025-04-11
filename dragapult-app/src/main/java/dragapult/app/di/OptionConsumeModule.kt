package dragapult.app.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dragapult.app.Platform
import dragapult.app.Source
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import java.io.File

@Module
object OptionConsumeModule {

    @get:Consume
    @get:IntoSet
    @get:Provides
    val inputDirectory: Option = Option.builder()
        .option("i")
        .longOpt("input-directory")
        .hasArg()
        .required()
        .desc("Required. Input directory meant for resource consumption. Accepts a single parameter which represents the nearest parent of all resource configurations. Accepts relative paths. Example: \"-i src/res\"")
        .build()

    @get:Consume
    @get:IntoSet
    @get:Provides
    val outputFile: Option = Option.builder()
        .option("o")
        .longOpt("output-file")
        .hasArg()
        .required()
        .desc("Required. Output file created after consuming all resource input files. It will be created exactly as specified. May or may not require you to create parent folders. Doesn't infer file type from extension automatically, it needs to be specified as part of \"output-type\" argument. Example: \"-o out/translations.csv\"")
        .build()

    @get:Consume
    @get:IntoSet
    @get:Provides
    val inputType: Option = Option.builder()
        .option("t")
        .longOpt("input-type")
        .hasArg()
        .required()
        .desc("Required. Defines input type for the \"input-directory\" argument. It takes in account the different project structures for respective the types. Allowed types are ${Platform.Companion.valuesString()}.")
        .build()

    @get:Consume
    @get:IntoSet
    @get:Provides
    val outputType: Option = Option.builder()
        .option("r")
        .longOpt("output-type")
        .hasArg()
        .required()
        .desc("Required. Defines output type for \"output-file\" argument. It chooses a specific parser capable of converting the data. Allowed types are ${Source.Companion.valuesString()}")
        .build()

    @get:Consume
    @get:IntoSet
    @get:Provides
    val help: Option = Option.builder()
        .option("h")
        .longOpt("help")
        .desc("Prints this help message")
        .build()

    operator fun invoke(cli: CommandLine) = Parsed(cli)

    class Parsed(private val cli: CommandLine) : Subroutine {

        val inputDirectory
            get() = File(cli.getOptionValue(OptionConsumeModule.inputDirectory).let(::requireNotNull))

        val outputFile
            get() = File(cli.getOptionValue(OptionConsumeModule.outputFile).let(::requireNotNull))

        val inputType
            get() = Platform.valueOfOption(cli.getOptionValue(OptionConsumeModule.inputType).let(::requireNotNull))

        val outputType
            get() = Source.valueOfOption(cli.getOptionValue(OptionConsumeModule.outputType).let(::requireNotNull))

        val help
            get() = cli.hasOption(OptionConsumeModule.help)

    }

}