package app.dragapult.definition

import nl.adaptivity.xmlutil.core.impl.multiplatform.InputStream
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class DefinitionDownloader(
    private val declaration: RemoteDefinitionDeclaration,
    private val outputDirectory: File
) {

    fun download(): File {
        val connection = createConnection()
        val file = createFile(connection)
        connection.connectInputStream()?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    // ---

    private fun createFile(connection: HttpURLConnection): File {
        val filename = declaration.url.file.substringAfterLast('/').substringBefore('?').run {
            "%s-%s.%s".format(
                substringBeforeLast('.'),
                declaration.name,
                substringAfterLast('.')
            )
        }
        return File(outputDirectory, filename).apply {
            parentFile?.mkdirs()
            if (exists()) {
                connection.ifModifiedSince = lastModified()
            } else {
                check(createNewFile()) {
                    "Could not create file: $this"
                }
            }
        }
    }

    private fun createConnection() = declaration.url.openHttpConnection().apply {
        useCaches = true
        requestMethod = declaration.requestMethod
        for ((key, value) in declaration.headers.orEmpty()) {
            setRequestProperty(key, value.joinToString(","))
        }
    }

    // ---

    private fun URL.openHttpConnection() = openConnection() as HttpURLConnection

    private fun HttpURLConnection.connectInputStream(): InputStream? {
        try {
            connect()
        } catch (e: IOException) {
            disconnect()
            throw e
        }
        when (responseCode) {
            HttpURLConnection.HTTP_NOT_MODIFIED -> return null
            !in HttpURLConnection.HTTP_OK until HttpURLConnection.HTTP_MULT_CHOICE -> error("Unexpected response code: $responseCode (${responseMessage})")
        }
        return getInputStream()
    }

}