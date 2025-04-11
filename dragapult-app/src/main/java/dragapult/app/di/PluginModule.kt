package dragapult.app.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dragapult.app.TranslationPlugin

@Module
class PluginModule {

    @IntoSet
    @Provides
    fun noop() = TranslationPlugin(Int.MAX_VALUE) { it }

}