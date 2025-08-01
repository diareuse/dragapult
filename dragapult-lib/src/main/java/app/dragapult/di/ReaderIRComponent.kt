package app.dragapult.di

import app.dragapult.Source
import app.dragapult.TranslationReader
import app.dragapult.ir.csv.CsvReaderIRModule
import app.dragapult.ir.json.JsonReaderIRModule
import app.dragapult.ir.yaml.YamlReaderIRModule
import dagger.BindsInstance
import dagger.Subcomponent
import java.io.File

@Subcomponent(
    modules = [
        ReaderIRResolverModule::class,
        JsonReaderIRModule::class,
        CsvReaderIRModule::class,
        YamlReaderIRModule::class
    ]
)
interface ReaderIRComponent {

    val reader: TranslationReader

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance source: Source,
            @BindsInstance file: File
        ): ReaderIRComponent
    }

}