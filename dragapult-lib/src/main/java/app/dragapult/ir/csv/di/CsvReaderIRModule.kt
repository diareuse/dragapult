package app.dragapult.ir.csv.di

import app.dragapult.Source
import app.dragapult.TranslationReader
import app.dragapult.ir.csv.ReaderCsvIR
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import org.apache.commons.csv.CSVFormat
import java.io.File

@Module(includes = [CsvDepIRModule::class])
class CsvReaderIRModule {

    @Provides
    @IntoMap
    @StringKey(Source.Csv.LABEL)
    fun csv(
        file: File,
        format: CSVFormat
    ): TranslationReader = ReaderCsvIR(
        input = file.inputStream(),
        format = format
    )

}