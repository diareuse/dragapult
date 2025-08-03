package app.dragapult.di

import app.dragapult.Preferences
import app.dragapult.android.AndroidPreferences
import app.dragapult.apple.ApplePreferences
import app.dragapult.ir.csv.CsvIRPreferences
import dagger.Module
import dagger.Provides

@Module
class PreferencesModule {

    @Provides
    fun android(prefs: Preferences): AndroidPreferences = prefs.android

    @Provides
    fun apple(prefs: Preferences): ApplePreferences = prefs.apple

    @Provides
    fun csvIR(prefs: Preferences): CsvIRPreferences = prefs.csvIR

}