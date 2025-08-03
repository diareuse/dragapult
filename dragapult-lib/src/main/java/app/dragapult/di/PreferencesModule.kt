package app.dragapult.di

import app.dragapult.Preferences
import app.dragapult.android.AndroidPreferences
import app.dragapult.apple.ApplePreferences
import app.dragapult.ir.csv.CsvIRPreferences
import app.dragapult.ir.json.JsonIRPreferences
import app.dragapult.ir.yaml.YamlIRPreferences
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

    @Provides
    fun jsonIR(prefs: Preferences): JsonIRPreferences = prefs.jsonIR

    @Provides
    fun yamlIR(prefs: Preferences): YamlIRPreferences = prefs.yamlIR

}