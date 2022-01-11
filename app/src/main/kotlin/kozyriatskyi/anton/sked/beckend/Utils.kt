package kozyriatskyi.anton.sked.beckend

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.util.pipeline.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal inline val PipelineContext<*, ApplicationCall>.queryParams: Parameters
    get() = call.request.queryParameters

internal fun String.takeIfIsInt(): String? = takeUnless { it.toIntOrNull() == null }

internal inline fun <T> tryOrNull(action: () -> T): T? = try {
    action()
} catch (t: Throwable) {
    null
}

internal fun String.takeIfIsLocalDate(): String? {
    return takeUnless { tryOrNull { LocalDate.parse(this, DateTimeFormatter.ofPattern("dd.MM.yyyy")) } == null }
}