package kozyriatskyi.anton.sked.beckend.app

import io.ktor.http.*
import io.ktor.server.testing.*
import kozyriatskyi.anton.sked.beckend.plugins.configureRouting
import kotlin.test.Test
import kotlin.test.assertEquals

class AppTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}