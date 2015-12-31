package example

import scala.scalajs.js
import org.scalajs.dom
import sharedscalacss.SharedMessages

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    println("Wat?")
    example.styles.AppCSS.addToDocument()
    dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
  }
}
