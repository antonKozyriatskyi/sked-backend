package kozyriatskyi.anton.sked.beckend.routing

import io.ktor.routing.*

fun Route.configureTeacherRouting() {
    route("teacher") {
        configureTeacherLoginRouting()
        configureTeacherScheduleRouting()
    }
}