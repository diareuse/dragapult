package app.dragapult.di

import app.dragapult.Dragapult
import app.dragapult.Platform
import app.dragapult.Source
import app.dragapult.TranslationPlugin
import app.dragapult.android.WriterAndroid
import app.dragapult.apple.WriterApple
import app.dragapult.ir.csv.ReaderCsvIR
import app.dragapult.ir.json.ReaderJsonIR
import app.dragapult.ir.yaml.ReaderYamlIR
import app.dragapult.json.WriterJson
import dagger.Module
import dagger.Provides
import java.io.File

@Module(includes = [PluginModule::class])
class DragapultModule {

    @Provides
    fun provide(
        inputType: Source,
        outputType: Platform,
        @Input ingress: File,
        @Output egress: File,
        plugins: Set<@JvmSuppressWildcards TranslationPlugin>
    ): Dragapult {
        val input = ingress.inputStream()
        val reader = when (inputType) {
            Source.Json -> ReaderJsonIR(input)
            Source.Csv -> ReaderCsvIR(input)
            Source.Yaml -> ReaderYamlIR(input)
        }
        val writer = when (outputType) {
            Platform.Android -> WriterAndroid(egress)
            Platform.Apple -> WriterApple(egress)
            Platform.Json -> WriterJson(egress)
        }
        return Dragapult {
            writer.use { writer ->
                for (key in reader) {
                    for (plugin in plugins) {
                        plugin.modify(inputType, outputType, key)
                    }
                    writer.append(key)
                }
            }
        }
    }

}