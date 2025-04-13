package dragapult.app.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dragapult.app.TranslationPlugin
import dragapult.app.plugins.PrefixQuotes
import dragapult.app.plugins.ReplaceCharLineBreakWithLiteral
import dragapult.app.plugins.ReplaceLiteralLineBreakWithChar
import dragapult.app.plugins.UnPrefixQuotes

@Module
abstract class PluginModule {

    companion object {

        @IntoSet
        @Provides
        fun noop() = TranslationPlugin(Int.MAX_VALUE) { _, _, _ -> }

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