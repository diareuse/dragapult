package app.dragapult.json.di

import app.dragapult.Platform
import app.dragapult.TranslationWriter
import app.dragapult.json.WriterJson
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [JsonDepModule::class])
class JsonWriterModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Json.LABEL)
    fun json(file: File): TranslationWriter = WriterJson(file)

}