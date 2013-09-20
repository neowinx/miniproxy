package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits._

object Application extends Controller {

  val context_ = "/dwm-jaxws"
  val from_ = "http://localhost:8080" + context_
  val to_ = "https://dev.dncp.gov.py" + context_

  def index(resto: String) = Action { request =>
    Logger.logger.debug(from_ + "/" + resto + "?" + request.rawQueryString)
    Async {

      var request_ = from_ + "/" + resto

      if(request.queryString.size > 0)
        request_ += "?" + request.rawQueryString

      WS.url( request_ ).get().map { response =>
        val contentType = response.header("Content-type").getOrElse("text/html")
        Ok(response.body.replaceAll(from_, to_)).as(contentType)
      }

    }
  }
  
}