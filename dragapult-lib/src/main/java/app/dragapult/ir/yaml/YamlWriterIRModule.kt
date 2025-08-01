package app.dragapult.ir.yaml

import app.dragapult.Source
import app.dragapult.TranslationWriter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [YamlDepIRModule::class])
class YamlWriterIRModule {

    @Provides
    @IntoMap
    @StringKey(Source.Yaml.LABEL)
    fun yaml(file: File): TranslationWriter = WriterYamlIR(file.outputStream())

}