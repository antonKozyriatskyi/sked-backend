package kozyriatskyi.anton.sked.beckend.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kozyriatskyi.anton.sked.beckend.generateErrorMessage
import kozyriatskyi.anton.sked.beckend.models.ScheduleResponse
import kozyriatskyi.anton.sked.beckend.models.mapToLessons
import kozyriatskyi.anton.sked.beckend.queryParams
import kozyriatskyi.anton.sked.beckend.takeIfIsInt
import kozyriatskyi.anton.sked.beckend.takeIfIsLocalDate
import kozyriatskyi.anton.sutparser.StudentScheduleParser

fun Route.configureStudentScheduleRouting() {
    val parser = StudentScheduleParser()

    get("schedule") {
        val faculty = queryParams["faculty"]?.takeIfIsInt()
        val course = queryParams["course"]?.takeIfIsInt()
        val group = queryParams["group"]?.takeIfIsInt()
        val dateFrom = queryParams["dateFrom"]?.takeIfIsLocalDate()
        val dateTo = queryParams["dateTo"]?.takeIfIsLocalDate()

        if (
            faculty != null &&
            course != null &&
            group != null &&
            dateFrom != null &&
            dateTo != null
        ) {
            kotlin.runCatching {
                val lessons = withContext(Dispatchers.IO) {
                    parser.getSchedule(
                        facultyId = faculty,
                        courseId = course,
                        groupId = group,
                        dateStart = dateFrom,
                        dateEnd = dateTo
                    ).mapToLessons()
                }

                ScheduleResponse(lessons)
            }
                .onSuccess { call.respond(it) }
                .onFailure {
                    val message = it.generateErrorMessage()
                    call.respondText(message)
                }
        } else {
            val message = queryParams.generateErrorMessage(
                fields = arrayOf(
                    "faculty",
                    "course",
                    "group",
                    "dateFrom",
                    "dateTo"
                )
            )
            call.respondText(message)
        }
    }
}