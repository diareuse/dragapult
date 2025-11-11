package app.dragapult.json.di

import app.dragapult.Platform
import app.dragapult.TranslationReader
import app.dragapult.json.ReaderJson
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import kotlinx.serialization.json.Json
import java.io.File

@Module(includes = [JsonDepModule::class])
class JsonReaderModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Json.LABEL)
    fun json(
        file: File,
        json: Json
    ): TranslationReader = ReaderJson(
        dir = file,
        json = json
    )

}