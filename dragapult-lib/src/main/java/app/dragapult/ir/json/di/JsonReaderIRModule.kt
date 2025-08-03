package app.dragapult.ir.json.di

import app.dragapult.Source
import app.dragapult.TranslationReader
import app.dragapult.ir.json.ReaderJsonIR
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import kotlinx.serialization.json.Json
import java.io.File

@Module(includes = [JsonDepIRModule::class])
class JsonReaderIRModule {

    @Provides
    @IntoMap
    @StringKey(Source.Json.LABEL)
    fun json(
        file: File,
        json: Json
    ): TranslationReader = ReaderJsonIR(
        input = file.inputStream(),
        json = json
    )

}