package app.dragapult.di

import app.dragapult.TranslationPlugin
import app.dragapult.plugins.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
abstract class PluginModule {

    companion object {

        @IntoSet
        @Provides
        fun noop() = TranslationPlugin.Companion(Int.MAX_VALUE) { _, _, _ -> }

    }

    @Binds
    @IntoSet
    abstract fun prefixQuotes(plugin: PrefixQuotes): TranslationPlugin

    @Binds
    @IntoSet
    abstract fun unPrefixQuotes(plugin: UnPrefixQuotes): TranslationPlugin

    @Binds
    @IntoSet
    abstract fun replaceLiteralLineBreakWithChar(plugin: ReplaceLiteralLineBreakWithChar): TranslationPlugin

    @Binds
    @IntoSet
    abstract fun replaceCharLineBreakWithLiteral(plugin: ReplaceCharLineBreakWithLiteral): TranslationPlugin

    @Binds
    @IntoSet
    abstract fun prefixAmpersand(plugin: PrefixAmpersand): TranslationPlugin

}