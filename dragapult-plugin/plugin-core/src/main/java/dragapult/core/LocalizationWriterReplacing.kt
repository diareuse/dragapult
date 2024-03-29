package dragapult.core

class LocalizationWriterReplacing(
    private val origin: LocalizationWriter,
    private val value: String,
    private val replacement: String
) : LocalizationWriter {

    override fun write(values: Sequence<Pair<Key, Value>>) {
        origin.write(values.map { (first, second) -> first to second.replace(value, replacement) })
    }

}