package app.dragapult.di

import app.dragapult.Platform
import app.dragapult.TranslationWriter
import dagger.Module
import dagger.Provides
import jakarta.inject.Provider

@Module
class WriterResolverModule {

    @Provides
    fun writer(
        readers: Map<String, @JvmSuppressWildcards Provider<TranslationWriter>>,
        platform: Platform
    ): TranslationWriter {
        return readers.getValue(platform.label).get().apply { println("writer: $this") }
    }

}