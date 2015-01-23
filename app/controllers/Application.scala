package controllers

import play.api._
import play.api.mvc._
import service.ThingsService

import scala.collection.mutable

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def get(key :String) = Action {
    val value = ThingsService.get(key)
    println(s"get $key -> $value")
    value match  {
      case Some(x) => Ok(x.something)
      case None => NotFound
    }
  }

  def update(key : String) = Action { implicit request =>
    val value : String = request.body.asText.getOrElse("You didn't send me anything")
    ThingsService.update(key, value)
    println(s"set $key -> $value")
    Ok("saved")
  }

  def create() = Action { implicit request =>
    val value : String = request.body.asText.getOrElse("You didn't send me anything")
    val key = ThingsService.create(value)
    println(s"set $key -> $value")
    Created(s"$key")
  }

  def delete(key : String) = Action {
    ThingsService.delete(key)
    println(s"set $key deleted")
    NoContent
  }

}