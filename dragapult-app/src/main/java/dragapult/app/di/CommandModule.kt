package dragapult.app.di

import app.dragapult.*
import app.dragapult.android.ReaderAndroid
import app.dragapult.android.WriterAndroid
import app.dragapult.apple.ReaderApple
import app.dragapult.apple.WriterApple
import app.dragapult.di.PluginModule
import app.dragapult.ir.csv.ReaderCsvIR
import app.dragapult.ir.csv.WriterCsvIR
import app.dragapult.ir.json.ReaderJsonIR
import app.dragapult.ir.json.WriterJsonIR
import app.dragapult.ir.yaml.ReaderYamlIR
import app.dragapult.ir.yaml.WriterYamlIR
import app.dragapult.json.ReaderJson
import app.dragapult.json.WriterJson
import dagger.Module
import dagger.Provides
import dragapult.app.Command

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