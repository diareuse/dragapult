package app.dragapult.di

import app.dragapult.Preferences
import app.dragapult.Source
import app.dragapult.TranslationReader
import app.dragapult.ir.csv.di.CsvReaderIRModule
import app.dragapult.ir.json.di.JsonReaderIRModule
import app.dragapult.ir.yaml.di.YamlReaderIRModule
import dagger.BindsInstance
import dagger.Subcomponent
import java.io.File

@Subcomponent(
    modules = [
        ReaderIRResolverModule::class,
        JsonReaderIRModule::class,
        CsvReaderIRModule::class,
        YamlReaderIRModule::class,
        PreferencesModule::class
    ]
)
interface ReaderIRComponent {

    val reader: TranslationReader

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance source: Source,
            @BindsInstance file: File,
            @BindsInstance prefs: Preferences
        ): ReaderIRComponent
    }

}