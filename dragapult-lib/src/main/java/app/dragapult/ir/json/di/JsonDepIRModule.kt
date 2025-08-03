package app.dragapult.ir.json.di

import app.dragapult.ir.json.JsonIRPreferences
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.serializersModuleOf
import java.util.*
import kotlin.reflect.KClass

@Module
class JsonDepIRModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Suppress("UNCHECKED_CAST")
    @Provides
    fun json(prefs: JsonIRPreferences) = Json {
        prettyPrint = prefs.prettyPrint
        prettyPrintIndent = prefs.prettyPrintIndent
        explicitNulls = prefs.explicitNulls
        encodeDefaults = false
        serializersModule += serializersModuleOf(
            SortedMap::class as KClass<Map<String, String>>,
            MapSerializer(String.serializer(), String.serializer())
        )
    }

}

