package webapp

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.body.form
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
    val viewModel = IndexPage("World", listOf("a", "b", "c"))
    Response(OK).body(renderer(viewModel))
  },

  "/assets" bind static(ResourceLoader.Directory("src/main/resources/assets")),

  "/submit" bind POST to { request ->
    val scrabble = request.form("words")?.uppercase()?.split(" ")
      ?.map { scoreCalculator(it) }
    val renderer = FreemarkerTemplates().HotReload("src/main/resources")
    val viewModel = SubmitPage(scrabble)
    Response(OK).body(renderer(viewModel))
  },
)

fun scoreCalculator(letter: String) = when (letter) {
  "A", "E", "I", "O", "U", "L", "N", "S", "T", "R" -> 1
  "D", "G" -> 2
  "B", "C", "M", "P" -> 3
  "F", "H", "V", "W", "Y" -> 4
  "K" -> 5
  "J", "X" -> 8
  "Q", "Z" -> 10
  else -> 0
}

fun main() {
  val server = app.asServer(Jetty(9000)).start()
  println("Server started on http://localhost:" + server.port())
}
