package app.dragapult.di

import app.dragapult.Platform
import app.dragapult.TranslationReader
import dagger.Module
import dagger.Provides
import jakarta.inject.Provider

@Module
class ReaderResolverModule {

    @Provides
    fun reader(
        readers: Map<String, @JvmSuppressWildcards Provider<TranslationReader>>,
        platform: Platform
    ): TranslationReader {
        return readers.getValue(platform.label).get().apply { println("reader: $this") }
    }

}