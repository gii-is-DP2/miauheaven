package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento13 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("rendimiento13")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(17)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(20)
		// Logged
		.exec(http("findOwners")
			.get("/owners/find")
			.headers(headers_0))
		.pause(23)
		// findOwners
		.exec(http("OwnersList")
			.get("/owners?lastName=")
			.headers(headers_0))
		.pause(10)
		// OwnersList
		.exec(http("showOwner")
			.get("/owners/1")
			.headers(headers_0))
		.pause(18)
		// showOwner
		.exec(http("AppointmentForm")
			.get("/owners/1/pets/1/appointment/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken1")))
		.pause(39)
		// AppointmentForm
		.exec(http("AppointmentCreated")
			.post("/owners/1/pets/1/appointment/new")
			.headers(headers_3)
			.formParam("date", "2020/05/30")
			.formParam("cause", "Prueba")
			.formParam("urgent", "1")
			.formParam("vet_id", "1")
			.formParam("_csrf", "${stoken1}"))
		.pause(13)
		// AppointmentCreated

	setUp(scn.inject(rampUsers(8000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}