package app.dragapult.unity.di

import app.dragapult.Platform
import app.dragapult.TranslationWriter
import app.dragapult.unity.WriterUnity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [UnityDepModule::class])
class UnityWriterModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Unity.LABEL)
    fun unity(file: File): TranslationWriter = WriterUnity(file)

}