package app.dragapult.unity.di

import app.dragapult.Platform
import app.dragapult.TranslationWriter
import app.dragapult.unity.UnityPreferences
import app.dragapult.unity.WriterUnity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import org.apache.commons.csv.CSVFormat
import java.io.File

@Module(includes = [UnityDepModule::class])
class UnityWriterModule {

    @Provides
    @IntoMap
    @StringKey(Platform.Unity.LABEL)
    fun unity(
        file: File,
        format: CSVFormat,
        prefs: UnityPreferences
    ): TranslationWriter = WriterUnity(
        dir = file,
        format = format,
        prefs = prefs
    )

}