package app.dragapult.android

import app.dragapult.Platform
import app.dragapult.TranslationReader
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File

@Module(includes = [AndroidDepModule::class])
class AndroidReaderModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Android.LABEL)
    fun android(file: File, xml: XML): TranslationReader = ReaderAndroid(file, xml)

}