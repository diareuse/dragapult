package app.dragapult.di

import app.dragapult.Source
import app.dragapult.TranslationWriter
import dagger.Module
import dagger.Provides
import jakarta.inject.Provider

@Module
class WriterIRResolverModule {

    @Provides
    fun writer(
        writers: Map<String, @JvmSuppressWildcards Provider<TranslationWriter>>,
        source: Source
    ): TranslationWriter {
        return writers.getValue(source.label).get()
    }

}