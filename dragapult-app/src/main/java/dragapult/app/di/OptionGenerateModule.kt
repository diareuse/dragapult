package dragapult.app.di

import app.dragapult.Platform
import app.dragapult.Source
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import java.io.File

@Module
object OptionGenerateModule {

    @get:Generate
    @get:IntoSet
    @get:Provides
    val inputFile: Option = Option.builder()
        .option("i")
        .longOpt("input-file")
        .hasArg()
        .required()
        .desc("Required. Input file used for generating outputs. Doesn't infer file type from extension automatically, it needs to be specified as part of \"input-type\" argument. Example: \"-i common/translations.csv\"")
        .build()

    @get:Generate
    @get:IntoSet
    @get:Provides
    val outputDirectory: Option = Option.builder()
        .option("o")
        .longOpt("output-directory")
        .hasArg()
        .required()
        .desc("Required. Output directory meant for generating platform resource. Accepts a single parameter which represents the very parent of all resource configurations. Accepts relative paths. Example: \"-o src/res\"")
        .build()

    @get:Generate
    @get:IntoSet
    @get:Provides
    val outputType: Option = Option.builder()
        .option("t")
        .longOpt("output-type")
        .hasArg()
        .required()
        .desc("Required. Defines output type for the \"output-directory\" argument. It takes in account the different project structures for respective the types. Allowed types are ${Platform.entries.joinToString()}.")
        .build()

    @get:Generate
    @get:IntoSet
    @get:Provides
    val inputType: Option = Option.builder()
        .option("s")
        .longOpt("input-type")
        .hasArg()
        .required()
        .desc("Required. Defines input type for \"input-file\" argument. It chooses a specific parser capable of converting the data. Allowed types are ${Source.entries.joinToString()}")
        .build()

    @get:Generate
    @get:IntoSet
    @get:Provides
    val blankValues: Option = Option.builder()
        .option("b")
        .longOpt("allow-blank-values")
        .desc("Permits writing lines with empty values. This might be useful for projects which require text keys to be empty for some translations. Off by default.")
        .optionalArg(true)
        .build()

    @get:Generate
    @get:IntoSet
    @get:Provides
    val help: Option = Option.builder()
        .option("h")
        .longOpt("help")
        .desc("Prints this help message")
        .build()

    operator fun invoke(cli: CommandLine): Subroutine {
        val parsed = Parsed(cli)
        if (parsed.help)
            return HelpSubroutine(Options().apply {
                for (option in options)
                    addOption(option)
            })
        return parsed
    }

    class Parsed(private val cli: CommandLine) : Subroutine {

        val inputFile
            get() = File(cli.getOptionValue(OptionGenerateModule.inputFile))

        val outputDirectory
            get() = File(cli.getOptionValue(OptionGenerateModule.outputDirectory))

        val outputType
            get() = Platform.valueOf(cli.getOptionValue(OptionGenerateModule.outputType))

        val inputType
            get() = Source.valueOf(cli.getOptionValue(OptionGenerateModule.inputType))

        val blankValues
            get() = cli.hasOption(OptionGenerateModule.blankValues)

        val help
            get() = cli.hasOption(OptionGenerateModule.help)

    }

}