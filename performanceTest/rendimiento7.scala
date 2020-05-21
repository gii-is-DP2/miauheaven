package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento7 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("rendimiento7")
		.exec(http("Home")
			.get("/"))
		.pause(5)
		// Home
		.exec(http("Login")
			.get("/login"))
		.pause(15)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "932fc3f2-ebf0-49a0-adc6-bea1609d54ec"))
		.pause(12)
		// Logged
		.exec(http("ShowShelters")
			.get("/animalshelter"))
		.pause(22)
		// ShowShelters
		.exec(http("ShelterForm")
			.get("/animalshelter/new"))
		.pause(52)
		// ShelterForm
		.exec(http("NewShelter")
			.post("/animalshelter/new")
			.headers(headers_2)
			.formParam("id", "")
			.formParam("name", "El corral")
			.formParam("cif", "12345678A")
			.formParam("place", "Madrid")
			.formParam("_csrf", "38fb8302-81b1-48c2-8d47-d7fc86a2eb70"))
		.pause(9)
		// NewShelter

	setUp(scn.inject(rampUsers(11000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
	}