package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento14 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("rendimiento14")
		.exec(http("Home")
			.get("/"))
		.pause(7)
		// Home
		.exec(http("Login")
			.get("/login"))
		.pause(14)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "shelter2")
			.formParam("password", "shelter2")
			.formParam("_csrf", "eec66260-7ce0-4fd6-ad2b-a2f37386d6d6"))
		.pause(7)
		// Logged
		.exec(http("ShowShelter")
			.get("/owners/myAnimalShelter"))
		.pause(30)
		// ShowShelter
		.exec(http("AppointmentForm")
			.get("/owners/12/pets/15/appointment/new"))
		.pause(56)
		// AppointmentForm
		.exec(http("NewAppointment")
			.post("/owners/12/pets/15/appointment/new")
			.headers(headers_2)
			.formParam("date", "2020/08/29")
			.formParam("cause", "Se choca con los muebles")
			.formParam("urgent", "0")
			.formParam("vet_id", "1")
			.formParam("_csrf", "e60564dc-f444-4897-a943-9becc4af248b"))
		.pause(8)
		// NewAppointment

	setUp(scn.inject(rampUsers(9000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
	}