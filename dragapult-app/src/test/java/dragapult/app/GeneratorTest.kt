package dragapult.app

import dragapult.app.android.WriterAndroid
import dragapult.app.apple.WriterApple
import dragapult.app.ir.csv.WriterCsvIR
import dragapult.app.ir.json.ReaderJsonIR
import dragapult.app.ir.json.WriterJsonIR
import dragapult.app.ir.yaml.WriterYamlIR
import dragapult.app.json.WriterJson
import java.io.File
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore("This is only for special cases where we need to regenerate files")
class GeneratorTest {

    val classLoader get() = this::class.java.classLoader
    val reader get() = ReaderJsonIR(classLoader.getResourceAsStream("keys.json")!!)

    @Test
    fun toCsvIr() {
        WriterCsvIR(asFile("ir/keys.csv.ir").outputStream()).pipe()
    }

    @Test
    fun toYamlIr() {
        WriterYamlIR(asFile("ir/keys.yaml.ir").outputStream()).pipe()
    }

    @Test
    fun toJsonIr() {
        WriterJsonIR(asFile("ir/keys.json.ir").outputStream()).pipe()
    }

    // ---

    @Test
    fun toAndroid() {
        WriterAndroid(asDir("output/android"), "strings.xml.out").pipe()
    }

    @Test
    fun toJson() {
        WriterJson(asDir("output/json"), "strings.json.out").pipe()
    }

    @Test
    fun toApple() {
        WriterApple(asDir("output/apple"), "Localizable.strings.out").pipe()
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