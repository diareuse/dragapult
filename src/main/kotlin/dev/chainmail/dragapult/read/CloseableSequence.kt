package dev.chainmail.dragapult.read

class CloseableSequence<out T>(
    private val closeable: AutoCloseable?,
    private val sequence: Sequence<T>
) : Sequence<T>, AutoCloseable {

    override fun iterator(): Iterator<T> {
        return sequence.iterator()
    }

    override fun close() {
        closeable?.close()
    }

}