package app.dragapult.ir.json

import app.dragapult.Source
import app.dragapult.TranslationReader
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [JsonDepIRModule::class])
class JsonReaderIRModule {

    @Provides
    @IntoMap
    @StringKey(Source.Json.LABEL)
    fun json(file: File): TranslationReader = ReaderJsonIR(file.inputStream())

}