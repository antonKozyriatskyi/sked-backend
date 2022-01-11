package kozyriatskyi.anton.sked.beckend.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kozyriatskyi.anton.sked.beckend.generateErrorMessage
import kozyriatskyi.anton.sked.beckend.models.mapToItems
import kozyriatskyi.anton.sked.beckend.queryParams
import kozyriatskyi.anton.sked.beckend.takeIfIsInt
import kozyriatskyi.anton.sutparser.TeacherInfoParser

fun Route.configureTeacherLoginRouting() {
    val parser = TeacherInfoParser()

    get("departments") {
        kotlin.runCatching { parser.getDepartments().mapToItems() }
            .onSuccess { call.respond(it) }
            .onFailure {
                val message = it.generateErrorMessage()
                call.respondText(message)
            }
    }

    get("teachers") {
        val department = queryParams["department"]?.takeIfIsInt()

        if (department != null) {
            kotlin.runCatching { parser.getTeachers(department).mapToItems() }
                .onSuccess { call.respond(it) }
                .onFailure {
                    val message = it.generateErrorMessage()
                    call.respondText(message)
                }
        } else {
            val message = queryParams.generateErrorMessage(fields = arrayOf("department"))
            call.respondText(message)
        }
    }
}