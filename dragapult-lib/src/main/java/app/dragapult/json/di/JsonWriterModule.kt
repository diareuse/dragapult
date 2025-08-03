package app.dragapult.json.di

import app.dragapult.Platform
import app.dragapult.TranslationWriter
import app.dragapult.json.JsonPreferences
import app.dragapult.json.WriterJson
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import kotlinx.serialization.json.Json
import java.io.File

@Module(includes = [JsonDepModule::class])
class JsonWriterModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Json.LABEL)
    fun json(
        file: File,
        json: Json,
        prefs: JsonPreferences
    ): TranslationWriter = WriterJson(
        dir = file,
        json = json,
        prefs = prefs
    )

}