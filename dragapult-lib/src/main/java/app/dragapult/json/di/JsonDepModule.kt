package app.dragapult.json.di

import app.dragapult.json.JsonPreferences
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json

@Module
class JsonDepModule {

    @Provides
    fun json(prefs: JsonPreferences) = Json {
        explicitNulls = prefs.explicitNulls
        encodeDefaults = false
        ignoreUnknownKeys = true
        isLenient = prefs.isLenient
    }

}