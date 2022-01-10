package kozyriatskyi.anton.sked.beckend.plugins

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml(block = createWelcomePage())
        }
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