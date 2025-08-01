package app.dragapult.apple

import app.dragapult.Platform
import app.dragapult.TranslationReader
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [AppleDepModule::class])
class AppleReaderModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Apple.LABEL)
    fun apple(file: File): TranslationReader = ReaderApple(file)

}