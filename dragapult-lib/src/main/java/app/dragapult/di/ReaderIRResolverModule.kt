package app.dragapult.di

import app.dragapult.Source
import app.dragapult.TranslationReader
import dagger.Module
import dagger.Provides
import jakarta.inject.Provider

@Module
class ReaderIRResolverModule {

    @Provides
    fun reader(
        readers: Map<String, @JvmSuppressWildcards Provider<TranslationReader>>,
        source: Source
    ): TranslationReader {
        return readers.getValue(source.label).get()
    }

}