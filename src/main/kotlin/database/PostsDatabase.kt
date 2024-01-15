package database

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.FOUND
import org.http4k.core.body.form
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.FreemarkerTemplates
import webapp.* // ktlint-disable no-wildcard-imports
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

val postsDatabase = PostsDatabase()
val posts = postsDatabase.loadAllPosts()
val titles = posts.map { it.title }
val bodies = posts.map { it.body }

val app: HttpHandler = routes(

  "/posts" bind Method.GET to {
    val renderer = FreemarkerTemplates().HotReload("src/main/resources")
    val viewModel = PostPage(titles, bodies)
    Response(Status.OK).body(renderer(viewModel))
  },
  "/submit" bind Method.POST to { request ->
    val postsDatabase = PostsDatabase()
    request.form("body")
      ?.let { Post(title = request.form("title")!!, body = it) }
      ?.let { postsDatabase.addPost(it) }
    Response(FOUND).header("Location", "/posts")
  },
)

class PostsDatabase {

  private val url = "jdbc:sqlite:./src/main/resources/database/posts.db"
  private val connection = connectTo(url)

  fun createTable() {
    val query = connection.createStatement()
    query.executeUpdate("CREATE TABLE posts (Id integer primary key, Title varchar(255), Body varchar(255))")
    println("Created table")
  }

  fun insertSomeData() {
    addPost(
      Post(
        title = "Ten ways to attract more clicks...",
        body = "First of all, write some decent content..."
      )
    )
    addPost(
      Post(
        title = "Which programming language should you learn next?",
        body = "Is it Kotlin? Is it Rust?..."
      )
    )
    addPost(
      Post(
        title = "Top ten gifts for Christmas",
        body = "Number one, a new book on Kotlin..."
      )
    )
    println("inserted three records")
  }

  fun addPost(post: Post) {
    val query = connection.createStatement()
    query.executeUpdate("INSERT INTO posts (title, body) VALUES ('${post.title}', '${post.body}');")
    println("Inserted... ${post.title}")
  }

  fun loadAllPosts(): List<Post> {
    val query = connection.prepareStatement("SELECT * FROM posts")
    return query.executeQuery().asListOfPosts()
  }

  private fun ResultSet.asListOfPosts(): List<Post> {
    val posts = mutableListOf<Post>()

    while (next()) {
      posts.add(Post(getInt("id"), getString("title"), getString("body")))
    }
    return posts
  }

  private fun connectTo(url: String): Connection =
    DriverManager.getConnection(url)
}

fun main() {
  val server = app.asServer(Jetty(9001)).start()
  println("Server started on http://localhost:" + server.port())
}
