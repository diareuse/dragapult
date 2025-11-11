package app.dragapult.di

import app.dragapult.TranslationPlugin
import app.dragapult.plugins.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds

@Module
abstract class PluginModule {

    @Multibinds
    abstract fun translations(): Set<TranslationPlugin>

    @Binds
    @IntoSet
    abstract fun replaceAppleFormatSpecifier(plugin: ReplaceAppleFormatSpecifier): TranslationPlugin

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