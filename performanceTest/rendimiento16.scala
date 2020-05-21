package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento16 extends Simulation {

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
		.pause(5)
		// Home
		.exec(http("Login")
			.get("/login"))
		.pause(12)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "shelter1")
			.formParam("password", "shelter1")
			.formParam("_csrf", "6d6de926-813d-475f-8a81-52e00b3acacf"))
		.pause(13)
		// Logged
		.exec(http("ShowShelter")
			.get("/owners/myAnimalShelter"))
		.pause(19)
		// ShowShelter
		.exec(http("AppointmentForm")
			.get("/owners/11/pets/14/appointment/new"))
		.pause(33)
		// AppointmentForm
		.exec(http("NewAppointment")
			.post("/owners/11/pets/14/appointment/new")
			.headers(headers_2)
			.formParam("date", "2021/01/28")
			.formParam("cause", "Est� enfermo")
			.formParam("urgent", "1")
			.formParam("vet_id", "6")
			.formParam("_csrf", "732ce2e8-b833-4533-ac03-551820850c3d"))
		.pause(18)
		// NewAppointment

	setUp(scn.inject(rampUsers(10000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
	}