package app.dragapult.di

import app.dragapult.*
import dagger.Module
import dagger.Provides
import jakarta.inject.Provider

@Module(
    includes = [PluginModule::class],
    subcomponents = [
        ReaderComponent::class,
        ReaderIRComponent::class,
        WriterComponent::class,
        WriterIRComponent::class
    ]
)
class DragapultModule {

    @Provides
    fun provideDragapult(
        plugins: Provider<Set<@JvmSuppressWildcards TranslationPlugin>>,
        readerFactory: Provider<ReaderComponent.Factory>,
        readerIRFactory: Provider<ReaderIRComponent.Factory>,
        writerFactory: Provider<WriterComponent.Factory>,
        writerIRFactory: Provider<WriterIRComponent.Factory>
    ) = Dragapult { input, output, ingress, egress, prefs ->
        val reader = when (input) {
            is Platform -> readerFactory.get().create(input, ingress, prefs).reader
            is Source -> readerIRFactory.get().create(input, ingress, prefs).reader
        }
        val writer: TranslationWriter = when (output) {
            is Platform -> writerFactory.get().create(output, egress, prefs).writer
            is Source -> writerIRFactory.get().create(output, egress, prefs).writer
        }
        writer.use { writer ->
            for (key in reader) {
                for (plugin in plugins.get()) {
                    plugin.modify(input, output, key)
                }
                writer.append(key)
            }
        }
    }

}