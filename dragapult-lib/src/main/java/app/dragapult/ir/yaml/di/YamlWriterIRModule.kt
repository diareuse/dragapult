package app.dragapult.ir.yaml.di

import app.dragapult.Source
import app.dragapult.TranslationWriter
import app.dragapult.ir.yaml.WriterYamlIR
import com.charleskorn.kaml.Yaml
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
    fun yaml(
        file: File,
        yaml: Yaml
    ): TranslationWriter = WriterYamlIR(
        out = file.outputStream(),
        yaml = yaml
    )

}