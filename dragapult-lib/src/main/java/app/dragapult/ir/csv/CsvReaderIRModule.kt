package app.dragapult.ir.csv

import app.dragapult.Source
import app.dragapult.TranslationReader
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.io.File

@Module(includes = [CsvDepIRModule::class])
class CsvReaderIRModule {

    @Provides
    @IntoMap
    @StringKey(Source.Csv.LABEL)
    fun csv(file: File): TranslationReader = ReaderCsvIR(file.inputStream())

}