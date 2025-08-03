package dragapult.app

import app.dragapult.Preferences
import app.dragapult.TranslationWriter
import app.dragapult.android.WriterAndroid
import app.dragapult.android.di.AndroidDepModule
import app.dragapult.apple.WriterApple
import app.dragapult.ir.csv.WriterCsvIR
import app.dragapult.ir.csv.di.CsvDepIRModule
import app.dragapult.ir.json.ReaderJsonIR
import app.dragapult.ir.json.WriterJsonIR
import app.dragapult.ir.json.di.JsonDepIRModule
import app.dragapult.ir.yaml.WriterYamlIR
import app.dragapult.ir.yaml.di.YamlDepIRModule
import app.dragapult.json.WriterJson
import app.dragapult.json.di.JsonDepModule
import java.io.File
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore("This is only for special cases where we need to regenerate files")
class GeneratorTest {

    val classLoader get() = this::class.java.classLoader
    val reader
        get() = ReaderJsonIR(
            classLoader.getResourceAsStream("keys.json")!!,
            JsonDepIRModule().json(Preferences.jsonIR())
        )

    @Test
    fun toCsvIr() {
        val format = CsvDepIRModule().format(Preferences.csvIR())
        WriterCsvIR(asFile("ir/keys.csv.ir").outputStream(), format).pipe()
    }

    @Test
    fun toYamlIr() {
        val yaml = YamlDepIRModule().yaml()
        WriterYamlIR(asFile("ir/keys.yaml.ir").outputStream(), yaml).pipe()
    }

    @Test
    fun toJsonIr() {
        val json = JsonDepIRModule().json(Preferences.jsonIR())
        WriterJsonIR(asFile("ir/keys.json.ir").outputStream(), json).pipe()
    }

    // ---

    @Test
    fun toAndroid() {
        val prefs = Preferences.android(outputFileName = "strings.xml.out")
        WriterAndroid(asDir("output/android"), AndroidDepModule().xml(), prefs).pipe()
    }

    @Test
    fun toJson() {
        val prefs = Preferences.json(outputFileName = "strings.json.out")
        val json = JsonDepModule().json(prefs)
        WriterJson(asDir("output/json"), json, prefs).pipe()
    }

    @Test
    fun toApple() {
        val prefs = Preferences.apple(outputFileName = "Localizable.strings.out")
        WriterApple(asDir("output/apple"), prefs).pipe()
    }

    // ---

    fun asFile(path: String) = File("src/test/resources/$path")
    fun asDir(path: String) = File("src/test/resources/$path").apply {
        mkdirs()
    }

    fun TranslationWriter.pipe() = use {
        for (key in reader)
            append(key)
    }

}