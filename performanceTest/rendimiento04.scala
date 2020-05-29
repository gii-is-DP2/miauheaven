package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento04 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.png""", """.*.js""", """.*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_9 = Map("Accept" -> "image/webp,*/*")

	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(17)
	}
	object Login{
	 	val login = exec(http("Login")
			.get("/login")
			.headers(headers_0))
		.pause(8)
	}
	object Logged{
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "shelter1")
			.formParam("password", "shelter1")
			.formParam("_csrf", "c52783df-9776-4bc0-8355-ee8ff89d5b62"))
		.pause(7)
	}
	
	object ShowEvent{
		val showEvent = exec(http("showEvents")
			.get("/events/")
			.headers(headers_0))
		.pause(14)
	}
	
	object SeeMoreEvent{
		val seeMoreEvent = exec(http("seeMoreEvent")
			.get("/events/1")
			.headers(headers_0))
		.pause(19)
	}
	
	object EditEvent{
		val editEvent = exec(http("editEvent")
			.get("/events/1/edit")
			.headers(headers_0))
		.pause(10)
	
	}
	
	object UpdateEvent{
		val updateEvent = exec(http("updateEvent")
			.post("/events/1/edit")
			.headers(headers_2)
			.formParam("id", "1")
			.formParam("name", "AnimalFest")
			.formParam("description", "Event to take a good time with your pet")
			.formParam("date", "2050/03/04")
			.formParam("_csrf", "4d3e1bdd-f405-4f8e-ad3e-6582532770b8"))
		.pause(9)
	}
	
	object NewEvent{
		val newEvent = exec(http("newEvent")
			.get("/events/new")
			.headers(headers_0))
		.pause(15)
	}
	
	object CreateEvent{
		val createEvent = exec(http("createEvent")
			.post("/events/new")
			.headers(headers_2)
			.formParam("id", "")
			.formParam("name", "Evento2")
			.formParam("description", "descripcion evento 2")
			.formParam("date", "2020/06/17")
			.formParam("_csrf", "4d3e1bdd-f405-4f8e-ad3e-6582532770b8"))
		.pause(8)
	
	} 

	val scn = scenario("rendimiento04")
		.exec(Home.home, Login.login, Logged.logged, ShowEvent.showEvent, SeeMoreEvent.seeMoreEvent, EditEvent.editEvent, UpdateEvent.updateEvent, NewEvent.newEvent, CreateEvent.createEvent)
		

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}