package webapp

import org.http4k.template.ViewModel

data class IndexPage(val name: String, val list: List<String>) : ViewModel {
    override fun template() = "views/IndexPage.ftl"
}
data class SubmitPage(val list: List<Int>?) : ViewModel {
  override fun template() = "views/IndexPage.ftl"
}
