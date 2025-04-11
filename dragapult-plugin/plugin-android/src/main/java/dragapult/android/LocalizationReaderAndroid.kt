package dragapult.android

import dragapult.core.Key
import dragapult.core.LocalizationReader
import dragapult.core.LocalizationType
import dragapult.core.Value
import org.w3c.dom.Document
import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class LocalizationReaderAndroid(
    private val file: File
) : LocalizationReader {

    override fun read(): Sequence<Pair<Key, Value>> {
        return file
            .readDocument()
            .readElements("string")
            .map {
                val key = it.attributes.getNamedItem("name").textContent
                val value = it.textContent
                key to value
            }
    }

    private fun File.readDocument(): Document {
        val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        return builder.parse(this).apply { normalizeDocument() }
    }

    private fun Document.readElements(name: String): Sequence<Node> {
        val elements = getElementsByTagName(name)
        return sequence {
            for (i in 0 until elements.length) {
                yield(elements.item(i))
            }
        }
    }

    class Factory : LocalizationReader.Factory {

        override val type: LocalizationType
            get() = LocalizationTypeAndroid

        override fun create(file: File): LocalizationReader {
            return LocalizationReaderAndroid(file)
        }

    }

}