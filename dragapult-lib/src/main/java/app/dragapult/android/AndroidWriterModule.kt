package app.dragapult.android

import app.dragapult.Platform
import app.dragapult.TranslationWriter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [AndroidDepModule::class])
class AndroidWriterModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Android.LABEL)
    fun android(file: File): TranslationWriter = WriterAndroid(file)

}