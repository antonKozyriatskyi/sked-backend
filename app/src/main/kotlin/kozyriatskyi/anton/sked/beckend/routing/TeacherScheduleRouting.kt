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
import kozyriatskyi.anton.sutparser.TeacherScheduleParser

fun Route.configureTeacherScheduleRouting() {
    val parser = TeacherScheduleParser()

    get("schedule") {
        val department = queryParams["department"]?.takeIfIsInt()
        val teacher = queryParams["teacher"]?.takeIfIsInt()
        val dateFrom = queryParams["dateFrom"]?.takeIfIsLocalDate()
        val dateTo = queryParams["dateTo"]?.takeIfIsLocalDate()

        if (
            department != null &&
            teacher != null &&
            dateFrom != null &&
            dateTo != null
        ) {
            kotlin.runCatching {
                val lessons = parser.getSchedule(
                    departmentId = department,
                    teacherId = teacher,
                    dateStart = dateFrom,
                    dateEnd = dateTo
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
                    "department",
                    "teacher",
                    "dateFrom",
                    "dateTo"
                )
            )
            call.respondText(message)
        }
    }
}