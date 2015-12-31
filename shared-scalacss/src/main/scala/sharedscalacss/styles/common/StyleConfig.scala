package sharedscalacss.styles.common

import scalacss._
import scalacss.Defaults._
import sharedscalacss.styles.CssFramework

trait StyleConfig {
  self: StyleSheet.Inline
    with CssFramework =>

  import dsl._

  // Layout
  val gutterSpacing = 15.px
  val pageMaxWidth = 1000.px

  val layoutTopOffset = 50.px // vertically align top spacing between sidebar and main
}
