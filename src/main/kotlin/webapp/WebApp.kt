package webapp

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.FreemarkerTemplates

val app: HttpHandler = routes(

    "/index" bind GET to {
        val renderer = FreemarkerTemplates().HotReload("src/main/resources")
        val viewModel = IndexPage("World")
        Response(OK).body(renderer(viewModel))
    },

    "/assets" bind static(ResourceLoader.Directory("src/main/resources/assets")),
)

fun main() {
    val server = app.asServer(Jetty(9000)).start()
    println("Server started on http://localhost:" + server.port())
}
