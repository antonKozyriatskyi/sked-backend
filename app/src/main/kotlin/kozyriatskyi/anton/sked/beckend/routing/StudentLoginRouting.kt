package kozyriatskyi.anton.sked.beckend.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kozyriatskyi.anton.sked.beckend.generateErrorMessage
import kozyriatskyi.anton.sked.beckend.models.mapToItems
import kozyriatskyi.anton.sked.beckend.queryParams
import kozyriatskyi.anton.sked.beckend.takeIfIsInt
import kozyriatskyi.anton.sutparser.StudentInfoParser

fun Route.configureStudentLoginRouting() {
    val parser = StudentInfoParser()

    get("faculties") {
        kotlin.runCatching {
            withContext(Dispatchers.IO) {
                parser.getFaculties().mapToItems()
            }
        }
            .onSuccess { call.respond(it) }
            .onFailure {
                val message = it.generateErrorMessage()
                call.respondText(message)
            }
    }

    get("courses") {
        val faculty = queryParams["faculty"]?.takeIfIsInt()

        if (faculty != null) {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    parser.getCourses(faculty).mapToItems()
                }
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

    get("groups") {
        val faculty = queryParams["faculty"]?.takeIfIsInt()
        val course = queryParams["course"]?.takeIfIsInt()

        if (faculty != null && course != null) {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    parser.getGroups(faculty, course).mapToItems()
                }
            }
                .onSuccess { call.respond(it) }
                .onFailure {
                    val message = it.generateErrorMessage()
                    call.respondText(message)
                }
        } else {
            val message = queryParams.generateErrorMessage(fields = arrayOf("faculty", "course"))
            call.respondText(message)
        }
    }
}