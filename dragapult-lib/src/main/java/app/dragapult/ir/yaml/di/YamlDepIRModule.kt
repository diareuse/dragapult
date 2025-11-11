package app.dragapult.ir.yaml.di

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi

@Module
class YamlDepIRModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun yaml() = Yaml(
        configuration = YamlConfiguration(
            encodeDefaults = false,
            strictMode = false,
            codePointLimit = Int.MAX_VALUE
        )
    )

}
