package app.dragapult.unity

import app.dragapult.Platform
import app.dragapult.TranslationReader
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [UnityDepModule::class])
class UnityReaderModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Unity.LABEL)
    fun unity(file: File): TranslationReader = ReaderUnity(file)

}