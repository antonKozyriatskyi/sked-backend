package kozyriatskyi.anton.sked.beckend.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kozyriatskyi.anton.sked.beckend.generateErrorMessage
import kozyriatskyi.anton.sked.beckend.models.mapToItems
import kozyriatskyi.anton.sked.beckend.queryParams
import kozyriatskyi.anton.sked.beckend.takeIfIsInt
import kozyriatskyi.anton.sutparser.StudentInfoParser

fun Route.configureStudentLoginRouting() {
    val parser = StudentInfoParser()

    route("student") {
        get("/faculties") {
            val faculties = parser.getFaculties().mapToItems()
            call.respond(faculties)
        }

        get("/courses") {
            val faculty = queryParams["faculty"]?.takeIfIsInt()

            if (faculty != null) {
                kotlin.runCatching {
                    parser.getCourses(faculty).mapToItems()
                }
                    .onSuccess { call.respond(it) }
                    .onFailure {
                        val message = it.generateErrorMessage()
                        call.respondText(message)
                    }
            } else {
                call.respond("faculty id must be specified as a query parameter")
            }
        }

        get("/groups") {
            val faculty = queryParams["faculty"]?.takeIfIsInt()
            val course = queryParams["course"]?.takeIfIsInt()

            if (faculty != null && course != null) {
                val groups = parser.getGroups(faculty, course).mapToItems()
                call.respond(groups)
            } else {
                val message = queryParams.generateErrorMessage(fields = arrayOf("faculty", "course"))
                call.respondText(message)
            }
        }
    }
}