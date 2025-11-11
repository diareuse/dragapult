package app.dragapult.unity.di

import dagger.Module
import dagger.Provides
import org.apache.commons.csv.CSVFormat

@Module
class UnityDepModule {

    @Provides
    fun format(): CSVFormat = CSVFormat.DEFAULT.builder()
        .setHeader()
        .setSkipHeaderRecord(true)
        .setRecordSeparator("\n")
        .get()

}