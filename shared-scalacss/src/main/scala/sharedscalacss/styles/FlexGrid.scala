package sharedscalacss.styles

import scalacss.Defaults._
import scalacss._

object FlexGrid {
  sealed trait SpacingModifier
  case object Gutter extends SpacingModifier
  case object Collapse extends SpacingModifier

  sealed trait FlowModifier
  case object RowWrap extends FlowModifier
  case object RowNoWrap extends FlowModifier
  case object ColumnWrap extends FlowModifier
  case object ColumnNoWrap extends FlowModifier

  sealed trait AlignModifier
  case object Top extends AlignModifier
  case object Middle extends AlignModifier
  case object Bottom extends AlignModifier

  sealed trait SizeModifier
  case object Default extends SizeModifier
  case object Auto extends SizeModifier
  case object Fit extends SizeModifier
  case object Full extends SizeModifier
  case object Half extends SizeModifier
  case object Third extends SizeModifier
  case object Fourth extends SizeModifier
  case object Fifth extends SizeModifier
  case object Sixth extends SizeModifier

  sealed trait DirectionModifier
  case object Row extends DirectionModifier
  case object RowReverse extends DirectionModifier
  case object Column extends DirectionModifier
  case object ColumnReverse extends DirectionModifier
}

class FlexGrid(gutterSpacing: Length[Int])(implicit r: mutable.Register) extends StyleSheet.Inline()(r) with CssFramework {
  import dsl._
  import FlexGrid._

  val sizingDomain = Domain.ofValues[SizeModifier](Default,Auto,Fit,Full,Half,Third,Fourth,Fifth,Sixth)

  val alignDomain = Domain.ofValues[AlignModifier](Top,Middle,Bottom)

  val directionDomain = Domain.ofValues[DirectionModifier](Row, RowReverse, Column, ColumnReverse)

  val sizing = styleF(sizingDomain) {
    case Default => mixin(flex:="0 1 auto")
    case Auto => mixin(flex:="none")
    case Fit => mixin(flex:="1")
    case Full => mixin(flex:="0 0 100%")
    case Half => mixin(flex:="0 0 50%")
    case Third => mixin(flex:="0 0 33.3333%")
    case Fourth => mixin(flex:="0 0 25%")
    case Fifth => mixin(flex:="0 0 20%")
    case Sixth => mixin(flex:="0 0 16.6666%")
  }

  //Modifiers
  val collapse = style("fg-collapse")()

  val justifyCenter = style("fg-justify-center")()

  val justifyBetween = style("fg-justify-between")()

  val justifyEnd = style("fg-justify-end")()

  val column = style("fg-column")()

  val inline = style("fg-inline")()

  val flexChildren = style("fg-flex-children")()

  val cellsize = styleF(sizingDomain) { case _ => mixin() }

  private val cellsizeF = mixin(List(Default,Auto,Fit,Full,Half,Third,Fourth,Fifth,Sixth).map(a =>
    ToStyleUnsafeExt(~&(cellsize(a))(sizing(a)))):_*
  )

  val cell = style(
    flex:="1",
    padding(gutterSpacing,0.em,0.em,gutterSpacing),
    cellsizeF
  )

  val gridBase = mixin(
    display.flex,
    flexWrap.wrap,
    listStyle := none,
    margin(gutterSpacing * -1, 0.px, 0.px, gutterSpacing * -1),
    padding(0.px),
    ~&(collapse)(
      margin(0.px),
      ~>(cell)(padding(0.px))
    ),
    ~&(flexChildren)(
      ~>(cell)(
        display.flex,
        ~>(Cascade.all)(
          sizing(FlexGrid.Fit)
        )
      )
    ),
    ~&(justifyCenter)(
      justifyContent.center
    ),
    ~&(justifyEnd)(
      justifyContent.flexEnd
    ),
    ~&(justifyBetween)(
      justifyContent.spaceBetween
    ),
    ~&(column)(
      flexDirection.column
    ),
    ~&(inline)(
      display.inlineFlex
    )
  )

  val grid = style(gridBase)

  val gridsize = styleF(sizingDomain)(s =>
    styleS(~>(cell)(sizing(s)))
  )

  val mediumGridSize = styleF(sizingDomain)(s =>
    styleS(~>(cell)(mediumUp(sizing(s))))
  )

  val largeGridSize = styleF(sizingDomain)(s =>
    styleS(~>(cell)(largeUp(sizing(s))))
  )

  val gridalign = styleF(alignDomain) {
    case Top => styleS(alignItems.flexStart)
    case Middle => styleS(alignItems.center)
    case Bottom => styleS(alignItems.flexEnd)
  }

  val cellalign = styleF(alignDomain) {
    case Top => styleS(~&(cell)(alignSelf.flexStart))
    case Middle => styleS(~&(cell)(alignSelf.center))
    case Bottom => styleS(~&(cell)(alignSelf.flexEnd))
  }

  val gridDirection = styleF(directionDomain) {
    case Row => styleS(flexDirection.row)
    case RowReverse => styleS(flexDirection.rowReverse)
    case Column => styleS(flexDirection.column)
    case ColumnReverse => styleS(flexDirection.columnReverse)
  }

  val mediumGridDirection = styleF(directionDomain) {
    case Row => styleS(mediumUp(flexDirection.row))
    case RowReverse => styleS(mediumUp(flexDirection.rowReverse))
    case Column => styleS(mediumUp(flexDirection.column))
    case ColumnReverse => styleS(mediumUp(flexDirection.columnReverse))
  }

  val largeGridDirection = styleF(directionDomain) {
    case Row => styleS(largeUp(flexDirection.row))
    case RowReverse => styleS(largeUp(flexDirection.rowReverse))
    case Column => styleS(largeUp(flexDirection.column))
    case ColumnReverse => styleS(largeUp(flexDirection.columnReverse))
  }
}
