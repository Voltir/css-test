package controllers

import play.api.mvc._
import sharedscalacss.SharedMessages

object Application extends Controller {

  def scalacss = Action {
    Ok(views.html.index(SharedMessages.itWorks)("clientscalacss",false))
  }

  def scalatags = Action {
    Ok(views.html.index(SharedMessages.itWorks)("clientScalatags",false))
  }

  def scalacssOpt = Action {
    Ok(views.html.index(SharedMessages.itWorks)("clientscalacss",true))
  }

  def scalatagsOpt = Action {
    Ok(views.html.index(SharedMessages.itWorks)("clientScalatags",true))
  }
}
