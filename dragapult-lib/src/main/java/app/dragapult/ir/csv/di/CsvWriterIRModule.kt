package app.dragapult.ir.csv.di

import app.dragapult.Source
import app.dragapult.TranslationWriter
import app.dragapult.ir.csv.WriterCsvIR
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [CsvDepIRModule::class])
class CsvWriterIRModule {

    @Provides
    @IntoMap
    @StringKey(Source.Csv.LABEL)
    fun csv(file: File): TranslationWriter = WriterCsvIR(file.outputStream())

}