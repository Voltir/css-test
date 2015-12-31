package sharedscalacss.styles

import scalacss._
import scalacss.Defaults._

trait ResponsiveFramework extends StyleSheet.Inline { self: CssFramework =>
  import dsl._

  lazy val smallBp = 640
  lazy val mediumBp = 1024
  lazy val largeBp = 1440

  lazy val smallRange = (emCalc(0), emCalc(smallBp))
  lazy val mediumRange = (emCalc(smallBp + 1), emCalc(mediumBp))
  lazy val largeRange = (emCalc(mediumBp + 1), emCalc(largeBp))

  def smallUp = media.screen
  def smallOnly = media.screen.maxWidth(smallRange._2)
  def mediumUp = media.screen.minWidth(mediumRange._1)
  def mediumOnly = media.screen.minWidth(mediumRange._1).maxWidth(mediumRange._2)
  def largeUp = media.screen.minWidth(largeRange._1)
  def largeOnly = media.screen.minWidth(largeRange._1).maxWidth(largeRange._2)
}

trait CssFramework extends ResponsiveFramework {
  import dsl._

  implicit def unsafe2Style(unsafes: List[Style.UnsafeExt]): List[DslBase.ToStyle] = unsafes.map(dsl.ToStyleUnsafeExt)

  //CSS Selectors
  def ~~(s: StyleA)(to: scalacss.DslBase.ToStyle *) = {
    unsafeChild("." + s.className.value)(to:_*)
  }

  def ~>(s: StyleA)(to: scalacss.DslBase.ToStyle *) = {
    unsafeChild("> ." + s.className.value)(to:_*)
  }

  def ~~(s: CascadeSelector)(to: scalacss.DslBase.ToStyle *) = {
    unsafeChild(s.value)(to:_*)
  }

  def ~>(s: CascadeSelector)(to: scalacss.DslBase.ToStyle *) = {
    unsafeChild("> " + s.value)(to:_*)
  }

  def ~&(s: StyleA)(to: scalacss.DslBase.ToStyle *) = {
    unsafeExt(parent => parent+"." + s.className.value)(to:_*)
  }

  def root(s: StyleA)(to: scalacss.DslBase.ToStyle *) = {
    unsafeRoot(s.className.value)(to:_*)
  }

  def root(s: CascadeSelector)(to: scalacss.DslBase.ToStyle *) = {
    unsafeRoot(s.value)(to:_*)
  }

  def ~+(s: CascadeSelector)(to: scalacss.DslBase.ToStyle *) = {
    unsafeChild("+ " + s.value)(to:_*)
  }

  private def convertToEm(value: Int, baseValue: Int = 15): Length[Double] = {
    (value.toDouble / baseValue.toDouble).em
  }
  protected def emCalc(px: Int): Length[Double] = convertToEm(px)

  /**
   *
   * @param value target pixel size
   * @param baseValue page base font size
   * @return
   */
  private def convertToRem(value: Int, baseValue: Int = 15): Length[Double] = {
    (value.toDouble / baseValue.toDouble).rem
  }
  protected def remCalc(px: Int): Length[Double] = convertToRem(px)

  /**
   * used to calculate a set of numbers based on a
   * typographic scale: e.g. ms(0) = 1em,
   * ms(1) = 1.414em, ...
   * @param value raise the ratio to this power
   * @param ratio typographic ratio
   * @return the calculated length in ems
   */
  protected def ms(value: Int, ratio: Double = 1.414) = {
    scala.math.pow(ratio, value.toDouble).em
  }

  case class CascadeSelector private[CssFramework] (value: String) {
    def gen(attr: String, target: String) = CascadeSelector(s"$value[$attr='$target']")
  }

  trait InputCascadeSelector { self: CascadeSelector =>
    def text = gen("type","text")
    def num = gen("type","nums")
    def email = gen("type","email")
    def password = gen("type","password")
  }

  // Root Element
  object Cascade {
    val all = CascadeSelector("*")
    val html = CascadeSelector("html")
    // Document Metadata
    val head = CascadeSelector("head")
    val base = CascadeSelector("base")

    val link       = CascadeSelector("link")
    val meta       = CascadeSelector("meta")
    // Scripting
    val script     = CascadeSelector("script")
    // Sections
    val body       = CascadeSelector("body")
    val h1         = CascadeSelector("h1")
    val h2         = CascadeSelector("h2")
    val h3         = CascadeSelector("h3")
    val h4         = CascadeSelector("h4")
    val h5         = CascadeSelector("h5")
    val h6         = CascadeSelector("h6")
    val headers    = List(h1,h2,h3,h4,h5,h6) // Select all headers

    val header     = CascadeSelector("header")
    val footer     = CascadeSelector("footer")
    // Grouping content
    val p          = CascadeSelector("p")
    val hr         = CascadeSelector("hr")
    val pre        = CascadeSelector("pre")
    val blockquote = CascadeSelector("blockquote")
    val ol         = CascadeSelector("ol")
    val ul         = CascadeSelector("ul")
    val li         = CascadeSelector("li")
    val dl         = CascadeSelector("dl")
    val dt         = CascadeSelector("dt")
    val dd         = CascadeSelector("dd")
    val figure     = CascadeSelector("figure")
    val figcaption = CascadeSelector("figcaption")
    val div        = CascadeSelector("div")
    val nav        = CascadeSelector("nav")
    val main       = CascadeSelector("main")
    val aside      = CascadeSelector("aside")
    val address    = CascadeSelector("address")
    val article    = CascadeSelector("article")
    // Text-level semantics
    val a          = CascadeSelector("a")
    val big        = CascadeSelector("big")
    val em         = CascadeSelector("em")
    val strong     = CascadeSelector("strong")
    val small      = CascadeSelector("small")
    val s          = CascadeSelector("s")
    val cite       = CascadeSelector("cite")
    val code       = CascadeSelector("code")
    val sub        = CascadeSelector("sub")
    val sup        = CascadeSelector("sup")
    val i          = CascadeSelector("i")
    val b          = CascadeSelector("b")
    val u          = CascadeSelector("u")
    val span       = CascadeSelector("span")
    val br         = CascadeSelector("br")
    val wbr        = CascadeSelector("wbr")
    // Edits
    val ins        = CascadeSelector("ins")
    val del        = CascadeSelector("del")
    // Embedded content
    val img        = CascadeSelector("img")
    val iframe     = CascadeSelector("iframe")
    val embed      = CascadeSelector("embed")
    val `object`   = CascadeSelector("object")
    val param      = CascadeSelector("param")
    val video      = CascadeSelector("video")
    val audio      = CascadeSelector("audio")
    val source     = CascadeSelector("source")
    val track      = CascadeSelector("track")
    val canvas     = CascadeSelector("canvas")
    val map        = CascadeSelector("map")
    val area       = CascadeSelector("area")
    // Tabular data
    val table      = CascadeSelector("table")
    val caption    = CascadeSelector("caption")
    val colgroup   = CascadeSelector("colgroup")
    val col        = CascadeSelector("col")
    val tbody      = CascadeSelector("tbody")
    val thead      = CascadeSelector("thead")
    val tfoot      = CascadeSelector("tfoot")
    val tr         = CascadeSelector("tr")
    val td         = CascadeSelector("td")
    val th         = CascadeSelector("th")
    // Forms
    val form       = CascadeSelector("form")
    val fieldset   = CascadeSelector("fieldset")
    val legend     = CascadeSelector("legend")
    val label      = CascadeSelector("label")
    val input      = new CascadeSelector("input") with InputCascadeSelector
    val button     = CascadeSelector("button")
    val select     = CascadeSelector("select")
    val datalist   = CascadeSelector("datalist")
    val optgroup   = CascadeSelector("optgroup")
    val option     = CascadeSelector("option")
    val textarea   = CascadeSelector("textarea")
    val path       = CascadeSelector("path")
  }
}