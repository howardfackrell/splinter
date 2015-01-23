package service

import model.Thing
import play.api.Play.current
import play.api.db.DB

import anorm.SQL
import anorm.SqlQuery

/**
 * Created by howard.fackrell on 1/23/15.
 */
object ThingsService {

  def get(key: String): Option[Thing] = DB.withConnection { implicit connection =>
    val id: Long = key.toLong
    val sql = SQL("select * from things where id = {id}").on("id" -> id)

    sql().map(row =>
      Thing(row[Long]("id"), row[String]("something"))
    ).headOption
  }

  def create(something: String): Long = DB.withConnection { implicit connection =>
    val sql = SQL("insert into things(something) values ({something})").on("something" -> something)
    val id: Option[Long] = sql.executeInsert()
    id.getOrElse(0)
  }

  def update(key: String, something: String): Unit = DB.withConnection { implicit connection =>
    val id = key.toLong
    val sql = SQL("update things set something = {something} where id={id}").on("id" -> id, "something" -> something)
    sql.executeUpdate()
  }



  def delete(key : String): Unit = DB.withConnection { implicit connection =>
    val sql = SQL("delete from Things where id = {id}").on("id" -> key.toLong)
    sql.executeUpdate()
  }
}
