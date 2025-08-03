package app.dragapult.ir.json.di

import app.dragapult.Source
import app.dragapult.TranslationWriter
import app.dragapult.ir.json.WriterJsonIR
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