package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento6 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("Home")
		.exec(http("request_0")
			.get("/"))
		.pause(7)
		// Home
		.exec(http("Login")
			.get("/login"))
		.pause(17)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "shelter1")
			.formParam("password", "shelter1")
			.formParam("_csrf", "c6be4b21-c1e3-471a-933d-451d99d13e83"))
		.pause(17)
		// Logged
		.exec(http("ShowShelter")
			.get("/owners/myAnimalShelter"))
		.pause(15)
		// ShowShelter
		.exec(http("PetForm")
			.get("/owners/11/pets/new"))
		.pause(42)
		// PetForm
		.exec(http("NewPet")
			.post("/owners/11/pets/new")
			.headers(headers_2)
			.formParam("id", "")
			.formParam("name", "Segis")
			.formParam("birthDate", "2020/04/01")
			.formParam("type", "hamster")
			.formParam("genre", "male")
			.formParam("_csrf", "20704744-a127-45f8-82c6-9e0ff92f1000"))
		.pause(22)
		// NewPet

	setUp(scn.inject(rampUsers(10000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
	}