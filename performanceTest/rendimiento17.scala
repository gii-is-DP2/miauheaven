package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento17 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("rendimiento17")
		.exec(http("Home")
			.get("/"))
		.pause(5)
		// Home
		.exec(http("Login")
			.get("/login"))
		.pause(9)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "vet1")
			.formParam("password", "v3t")
			.formParam("_csrf", "1bc1755b-a48a-421b-aea5-919d5f363dae"))
		.pause(9)
		// Logged
		.exec(http("ShowAppointments")
			.get("/vets/appointment"))
		.pause(11)
		// ShowAppointments

	setUp(scn.inject(rampUsers(16000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
	}