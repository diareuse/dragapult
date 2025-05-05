package app.dragapult.di

import app.dragapult.TranslationPlugin
import app.dragapult.plugins.PrefixQuotes
import app.dragapult.plugins.ReplaceCharLineBreakWithLiteral
import app.dragapult.plugins.ReplaceLiteralLineBreakWithChar
import app.dragapult.plugins.UnPrefixQuotes
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

}