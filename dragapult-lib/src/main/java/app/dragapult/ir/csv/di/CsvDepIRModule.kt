package app.dragapult.ir.csv.di

import app.dragapult.ir.csv.CsvIRPreferences
import dagger.Module
import dagger.Provides
import org.apache.commons.csv.CSVFormat

@Module
class CsvDepIRModule {

    @Provides
    fun format(
        prefs: CsvIRPreferences
    ): CSVFormat = CSVFormat.DEFAULT.builder()
        .setHeader()
        .setSkipHeaderRecord(true)
        .setCommentMarker(prefs.commentMarker)
        .setRecordSeparator(prefs.recordSeparator)
        .get()

}