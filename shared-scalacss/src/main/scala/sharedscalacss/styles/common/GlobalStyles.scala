package sharedscalacss.styles.common

import scalacss._
import scalacss.Defaults._

trait GlobalStyles { self: StyleSheet.Inline with BaseCss =>
  import dsl._

  // Css Reset
  style(scalacss.ext.CssReset.meyer)

  /**
   * Box-sizing: https://css-tricks.com/inheriting-box-sizing-probably-slightly-better-best-practice/
   */
  style(
    root(Cascade.html)(
      boxSizing.borderBox,
      height(100.%%),
      height(100.vh)
    ),
    root(Cascade.body)(
      height(100.%%),
      height(100.vh)
    ),
    root(Cascade.all)(
      boxSizing.inherit,
      &.before(boxSizing.inherit),
      &.after(boxSizing.inherit)
    )
  )

  style(
    root(Cascade.address)(
      opacity(0.8),
      fontWeight._300
    )
  )
}
