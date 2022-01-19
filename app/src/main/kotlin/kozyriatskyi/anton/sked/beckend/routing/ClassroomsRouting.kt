package kozyriatskyi.anton.sked.beckend.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kozyriatskyi.anton.sked.beckend.generateErrorMessage
import kozyriatskyi.anton.sked.beckend.models.AudiencesTimesResponse
import kozyriatskyi.anton.sked.beckend.models.mapToAudiences
import kozyriatskyi.anton.sked.beckend.models.mapToItems
import kozyriatskyi.anton.sked.beckend.queryParams
import kozyriatskyi.anton.sked.beckend.takeIfIsInt
import kozyriatskyi.anton.sked.beckend.takeIfIsLocalDate
import kozyriatskyi.anton.sutparser.AudiencesParser

fun Route.configureAudiencesRouting() {
    val parser = AudiencesParser()

    route("classrooms") {
        get("times") {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    parser.getTimes().let {
                        val from = it.first.mapToItems()
                        val to = it.second.mapToItems()

                        AudiencesTimesResponse(
                            from = from,
                            to = to,
                        )
                    }
                }
            }
                .onSuccess { call.respond(it) }
                .onFailure {
                    val message = it.generateErrorMessage()
                    call.respondText(message)
                }
        }

        get {
            val faculty = queryParams["date"]?.takeIfIsLocalDate()
            val lessonStart = queryParams["lessonStart"]?.takeIfIsInt()
            val lessonEnd = queryParams["lessonEnd"]?.takeIfIsInt()

            if (
                faculty != null &&
                lessonStart != null &&
                lessonEnd != null
            ) {
                kotlin.runCatching {
                    parser.getAudiences(faculty, lessonStart, lessonEnd).mapToAudiences()
                }
                    .onSuccess { call.respond(it) }
                    .onFailure {
                        val message = it.generateErrorMessage()
                        call.respondText(message)
                    }
            } else {
                val message = queryParams.generateErrorMessage(fields = arrayOf("faculty"))
                call.respondText(message)
            }
        }
    }
}