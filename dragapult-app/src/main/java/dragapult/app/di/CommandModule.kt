package dragapult.app.di

import dagger.Module
import dagger.Provides
import dragapult.app.Command
import dragapult.app.model.Platform
import dragapult.app.model.Source
import dragapult.app.v2.TranslationReader
import dragapult.app.v2.TranslationWriter
import dragapult.app.v2.android.ReaderAndroid
import dragapult.app.v2.android.WriterAndroid
import dragapult.app.v2.apple.ReaderApple
import dragapult.app.v2.apple.WriterApple
import dragapult.app.v2.ir.csv.ReaderCsvIR
import dragapult.app.v2.ir.csv.WriterCsvIR
import dragapult.app.v2.ir.json.ReaderJsonIR
import dragapult.app.v2.ir.json.WriterJsonIR
import dragapult.app.v2.ir.yaml.ReaderYamlIR
import dragapult.app.v2.ir.yaml.WriterYamlIR
import dragapult.app.v2.json.ReaderJson
import dragapult.app.v2.json.WriterJson

@Module(includes = [CommandLineModule::class])
class CommandModule {

    @Provides
    fun provide(
        option: Subroutine
    ): Command {
        val reader: TranslationReader
        val writer: TranslationWriter
        when (option) {
            is OptionConsumeModule.Parsed -> {
                val input = option.inputDirectory
                val output = option.outputFile.outputStream()
                reader = when (option.inputType) {
                    Platform.Android -> ReaderAndroid(input)
                    Platform.Apple -> ReaderApple(input)
                    Platform.Json -> ReaderJson(input)
                }
                writer = when (option.outputType) {
                    Source.Json -> WriterJsonIR(output)
                    Source.Csv -> WriterCsvIR(output)
                    Source.Yaml -> WriterYamlIR(output)
                }
            }

            is OptionGenerateModule.Parsed -> {
                val input = option.inputFile.inputStream()
                val output = option.outputDirectory
                reader = when (option.inputType) {
                    Source.Json -> ReaderJsonIR(input)
                    Source.Csv -> ReaderCsvIR(input)
                    Source.Yaml -> ReaderYamlIR(input)
                }
                writer = when (option.outputType) {
                    Platform.Android -> WriterAndroid(output)
                    Platform.Apple -> WriterApple(output)
                    Platform.Json -> WriterJson(output)
                }
            }
        }
        return Command {
            writer.use { writer ->
                for (ir in reader)
                    writer.append(ir)
            }
        }
    }

}