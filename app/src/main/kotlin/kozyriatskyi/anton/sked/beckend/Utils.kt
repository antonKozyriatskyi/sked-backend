package kozyriatskyi.anton.sked.beckend

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.util.pipeline.*

internal inline val PipelineContext<*, ApplicationCall>.queryParams: Parameters
    get() = call.request.queryParameters

internal fun String.takeIfIsInt(): String? = takeUnless { it.toIntOrNull() == null }