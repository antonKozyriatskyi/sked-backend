package kozyriatskyi.anton.sked.beckend.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
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
        val dateStart = queryParams["dateStart"]?.takeIfIsLocalDate()
        val dateEnd = queryParams["dateEnd"]?.takeIfIsLocalDate()

        if (
            faculty != null &&
            course != null &&
            group != null &&
            dateStart != null &&
            dateEnd != null
        ) {
            kotlin.runCatching {
                val lessons = parser.getSchedule(
                    facultyId = faculty,
                    courseId = course,
                    groupId = group,
                    dateStart = dateStart,
                    dateEnd = dateEnd
                ).mapToLessons()

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
                    "dateStart",
                    "dateEnd"
                )
            )
            call.respondText(message)
        }
    }
}