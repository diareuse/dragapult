package dragapult.app.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dragapult.app.TranslationKeyIR
import dragapult.app.TranslationPlugin

@Module
class PluginModule {

    @IntoSet
    @Provides
    fun noop() = object : TranslationPlugin {
        override val priority: Int get() = Int.MAX_VALUE
        override fun modify(key: TranslationKeyIR) = key
    }

}