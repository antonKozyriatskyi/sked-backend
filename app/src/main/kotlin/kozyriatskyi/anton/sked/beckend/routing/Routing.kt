package kozyriatskyi.anton.sked.beckend.routing

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml(block = createWelcomePage())
        }

        configureStudentRouting()
        configureTeacherRouting()
        configureAudiencesRouting()
    }
}

private fun createWelcomePage(): HTML.() -> Unit = {
    head {
        title {
            +"Sked Server"
        }
    }
    body {
        h1 {
            +"Welcome to Sked Server!"
        }
    }
}