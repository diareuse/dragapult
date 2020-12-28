package dev.chainmail.dragapult.read

import dev.chainmail.dragapult.model.Translation
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

class CSVReadSequence(
    private val file: File,
    private val separator: String
) : ReadSequence {

    override fun getSequence(): CloseableSequence<Translation> {
        if (!file.isFile) {
            return CloseableSequence(null, sequenceOf())
        }

        val reader = BufferedReader(FileReader(file))
        val sequence = reader
            .translationSequence(separator)
            .filterNot { it.isComment }

        return CloseableSequence(reader, sequence)
    }

}

private fun BufferedReader.translationSequence(separator: String): Sequence<Translation> =
    TranslationSequence(this, separator).flatten().constrainOnce()

private class TranslationSequence(
    private val reader: BufferedReader,
    private val separator: String
) : Sequence<List<Translation>> {

    override fun iterator(): Iterator<List<Translation>> {
        return TranslationIterator(reader, separator)
    }

}

private class TranslationIterator(
    private val reader: BufferedReader,
    private val separator: String
) : Iterator<List<Translation>> {

    private var nextValue: List<Translation>? = null
    private var done = false
    private val languages = mutableListOf<String>()

    override fun hasNext(): Boolean {
        if (nextValue == null && languages.isEmpty()) {
            val names = reader.readLine()
            if (names != null) {
                languages.addAll(names.split(separator))
            }
        }
        if (nextValue == null && !done) {
            nextValue = reader.readLine()?.asTranslations()
            if (nextValue == null) done = true
        }
        return nextValue != null
    }

    override fun next(): List<Translation> {
        if (!hasNext()) {
            throw NoSuchElementException()
        }
        val answer = nextValue
        nextValue = null
        return answer!!
    }

    private fun String.asTranslations(): List<Translation> {
        val columns = split(separator)
        val key = columns.first()
        val list = mutableListOf<Translation>()

        for (i in 1 until languages.size) {
            val value = columns.getOrNull(i)
            if (value == null || value.isBlank()) {
                continue
            }
            list += Translation(
                key,
                languages[i],
                value
            )
        }

        return list
    }

}