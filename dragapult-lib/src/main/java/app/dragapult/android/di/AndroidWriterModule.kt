package app.dragapult.android.di

import app.dragapult.Platform
import app.dragapult.TranslationWriter
import app.dragapult.android.AndroidPreferences
import app.dragapult.android.WriterAndroid
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File

@Module(includes = [AndroidDepModule::class])
class AndroidWriterModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Android.LABEL)
    fun android(
        file: File,
        xml: XML,
        prefs: AndroidPreferences
    ): TranslationWriter = WriterAndroid(
        outputDirectory = file,
        xml = xml,
        prefs = prefs
    )

}