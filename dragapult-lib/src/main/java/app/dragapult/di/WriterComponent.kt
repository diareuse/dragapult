package app.dragapult.di

import app.dragapult.Platform
import app.dragapult.TranslationWriter
import app.dragapult.android.AndroidWriterModule
import app.dragapult.apple.AppleWriterModule
import app.dragapult.json.JsonWriterModule
import app.dragapult.unity.UnityWriterModule
import dagger.BindsInstance
import dagger.Subcomponent
import java.io.File

@Subcomponent(
    modules = [
        WriterResolverModule::class,
        AndroidWriterModule::class,
        AppleWriterModule::class,
        JsonWriterModule::class,
        UnityWriterModule::class
    ]
)
interface WriterComponent {

    val writer: TranslationWriter

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance platform: Platform,
            @BindsInstance file: File
        ): WriterComponent
    }
}