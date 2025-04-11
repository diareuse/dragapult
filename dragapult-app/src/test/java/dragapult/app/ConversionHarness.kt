package dragapult.app

import java.io.ByteArrayOutputStream
import java.io.File

abstract class ConversionHarness {

    private val loader get() = this::class.java.classLoader

    fun resourceFile(name: String) = loader.getResourceAsStream(name)!!
    fun resourceFileAsString(name: String) = resourceFile(name).reader().readText()
    fun resourceDir(name: String) = File("src/test/resources/$name")

    fun TranslationReader.copyTo(writer: TranslationWriter) = writer.use {
        for (item in this)
            it.append(item)
    }

    fun <S : TestSetup, T> test(
        prepare: () -> S,
        test: (S) -> T,
        verify: (T) -> Unit,
        clean: (S, T?) -> Unit = { setup, result ->
            when (setup) {
                is TestSetup.FromIR -> setup.outputDir.deleteRecursively()
                is TestSetup.ToIR -> setup.output.reset()
            }
        }
    ) {
        val setup = prepare()
        val result = try {
            test(setup)
        } catch (e: Throwable) {
            clean(setup, null)
            throw e
        }
        try {
            verify(result)
        } finally {
            clean(setup, result)
        }
    }

    sealed class TestSetup {
        abstract val reader: TranslationReader
        abstract val writer: TranslationWriter

        data class FromIR(
            override val reader: TranslationReader,
            override val writer: TranslationWriter,
            val outputDir: File
        ) : TestSetup()

        data class ToIR(
            override val reader: TranslationReader,
            override val writer: TranslationWriter,
            val output: ByteArrayOutputStream
        ) : TestSetup()

    }

}