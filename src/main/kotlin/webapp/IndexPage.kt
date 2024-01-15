package webapp

import org.http4k.template.ViewModel

data class IndexPage(val name: String, val list: List<String>) : ViewModel {
  override fun template() = "views/IndexPage.ftl"
}

data class SubmitPage(
  val words: List<String>?,
  val scores: List<Int>?
) : ViewModel {
  override fun template() = "views/SubmitPage.ftl"
}

data class PostPage(
  val titles: List<String>,
  val bodies: List<String>
) : ViewModel {
  override fun template() = "views/PostPage.ftl"
}
