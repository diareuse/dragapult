package app.dragapult.di

import app.dragapult.Source
import app.dragapult.TranslationWriter
import app.dragapult.ir.csv.di.CsvWriterIRModule
import app.dragapult.ir.json.di.JsonWriterIRModule
import app.dragapult.ir.yaml.di.YamlWriterIRModule
import dagger.BindsInstance
import dagger.Subcomponent
import java.io.File

@Subcomponent(
    modules = [
        WriterIRResolverModule::class,
        JsonWriterIRModule::class,
        CsvWriterIRModule::class,
        YamlWriterIRModule::class
    ]
)
interface WriterIRComponent {

    val writer: TranslationWriter

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance source: Source,
            @BindsInstance file: File
        ): WriterIRComponent
    }

}