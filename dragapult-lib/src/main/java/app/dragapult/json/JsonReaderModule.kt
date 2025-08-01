package app.dragapult.json

import app.dragapult.Platform
import app.dragapult.TranslationReader
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [JsonDepModule::class])
class JsonReaderModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Json.LABEL)
    fun json(file: File): TranslationReader = ReaderJson(file)

}