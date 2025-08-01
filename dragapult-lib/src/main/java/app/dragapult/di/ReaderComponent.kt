package app.dragapult.di

import app.dragapult.Platform
import app.dragapult.TranslationReader
import app.dragapult.android.AndroidReaderModule
import app.dragapult.apple.AppleReaderModule
import app.dragapult.json.JsonReaderModule
import app.dragapult.unity.UnityReaderModule
import dagger.BindsInstance
import dagger.Subcomponent
import java.io.File

@Subcomponent(
    modules = [
        ReaderResolverModule::class,
        AndroidReaderModule::class,
        AppleReaderModule::class,
        JsonReaderModule::class,
        UnityReaderModule::class
    ]
)
interface ReaderComponent {

    val reader: TranslationReader

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance platform: Platform,
            @BindsInstance file: File
        ): ReaderComponent
    }

}

