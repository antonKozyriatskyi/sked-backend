package kozyriatskyi.anton.sked.beckend

import io.ktor.http.*
import org.jsoup.HttpStatusException
import java.net.SocketTimeoutException

fun Parameters.generateErrorMessage(
    prefix: String = "Following query params must be specified: ",
    vararg fields: String
): String {
    check(fields.isNotEmpty())

    return fields
        .filter { this[it]?.toIntOrNull() == null }
        .joinToString(prefix = prefix)
}

internal fun Throwable.generateErrorMessage(): String {
    return when (this) {
        is HttpStatusException -> {
            "e-rozklad.dut.edu.ua returned $statusCode"
        }
        is SocketTimeoutException -> {
            "Connection to e-rozklad.dut.edu.ua timed out"
        }
        else -> {
            "Couldn't connect to e-rozklad.dut.edu.ua: [${this.message}]"
        }
    }
}