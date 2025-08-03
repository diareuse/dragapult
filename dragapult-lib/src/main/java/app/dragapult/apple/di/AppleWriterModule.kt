package app.dragapult.apple.di

import app.dragapult.Platform
import app.dragapult.TranslationWriter
import app.dragapult.apple.WriterApple
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [AppleDepModule::class])
class AppleWriterModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Apple.LABEL)
    fun apple(file: File): TranslationWriter = WriterApple(file)

}