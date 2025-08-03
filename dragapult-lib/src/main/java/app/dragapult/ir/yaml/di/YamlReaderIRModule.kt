package app.dragapult.ir.yaml.di

import app.dragapult.Source
import app.dragapult.TranslationReader
import app.dragapult.ir.yaml.ReaderYamlIR
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [YamlDepIRModule::class])
class YamlReaderIRModule {

    @Provides
    @IntoMap
    @StringKey(Source.Yaml.LABEL)
    fun yaml(file: File): TranslationReader = ReaderYamlIR(file.inputStream())

}