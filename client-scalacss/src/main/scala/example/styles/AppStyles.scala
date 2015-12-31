package example.styles

import org.scalajs.dom
import sharedscalacss.styles.common.{StyleConfig, GlobalStyles, BaseCss}
import sharedscalacss.styles.{CrossCssDefinition, FlexGrid}

import scalacss._
import scalacss._
import scalacss.Defaults._
import scalacss.mutable.GlobalRegistry

class AppStyles()(implicit val r: mutable.Register)
  extends StyleSheet.Inline()(r)
  with BaseCss
  with GlobalStyles
  with StyleConfig
  with CrossCssDefinition {

  override val fg = new FlexGrid(gutterSpacing)
}

object AppStyles extends StyleSheet.Inline {
  val all = new AppStyles()
}

object AppCSS {
  import scalacss.ScalatagsCss._

  def addToDocument() = {
    GlobalRegistry.register(
      AppStyles
    )

    GlobalRegistry.onRegistration { css =>
      val elem = css.render[scalatags.JsDom.TypedTag[dom.raw.HTMLStyleElement]]
      dom.document.head.appendChild(elem.render)
      ()
    }
  }
}