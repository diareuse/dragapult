package app.dragapult.ir.json

import app.dragapult.Source
import app.dragapult.TranslationWriter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [JsonDepIRModule::class])
class JsonWriterIRModule {

    @Provides
    @IntoMap
    @StringKey(Source.Json.LABEL)
    fun json(file: File): TranslationWriter = WriterJsonIR(file.outputStream())

}