package dragapult.app.di

import dagger.Module
import dagger.Provides
import dragapult.app.*
import dragapult.app.android.ReaderAndroid
import dragapult.app.android.WriterAndroid
import dragapult.app.apple.ReaderApple
import dragapult.app.apple.WriterApple
import dragapult.app.ir.csv.ReaderCsvIR
import dragapult.app.ir.csv.WriterCsvIR
import dragapult.app.ir.json.ReaderJsonIR
import dragapult.app.ir.json.WriterJsonIR
import dragapult.app.ir.yaml.ReaderYamlIR
import dragapult.app.ir.yaml.WriterYamlIR
import dragapult.app.json.ReaderJson
import dragapult.app.json.WriterJson

@Module(includes = [CommandLineModule::class, PluginModule::class])
class CommandModule {

    @Provides
    fun provide(
        option: Subroutine,
        plugins: Set<@JvmSuppressWildcards TranslationPlugin>
    ): Command {
        val reader: TranslationReader
        val writer: TranslationWriter
        val inputType: FileKind
        val outputType: FileKind
        when (option) {
            is OptionConsumeModule.Parsed -> {
                val input = option.inputDirectory
                val output = option.outputFile.outputStream()
                inputType = option.inputType
                outputType = option.outputType
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
                inputType = option.inputType
                outputType = option.outputType
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

            is HelpSubroutine -> return Command {
                option.print()
            }
        }
        val plugins = plugins.sortedByDescending { it.priority }
        return Command {
            writer.use { writer ->
                for (ir in reader) {
                    for (plugin in plugins) {
                        plugin.modify(inputType, outputType, ir)
                    }
                    writer.append(ir)
                }
            }
        }
    }

}