package kozyriatskyi.anton.sked.beckend.routing

import io.ktor.routing.*

fun Route.configureStudentRouting() {
    route("student") {
        configureStudentLoginRouting()
        configureStudentScheduleRouting()
    }
}