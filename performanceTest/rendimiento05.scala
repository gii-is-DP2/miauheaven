
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Rendimiento05 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("rendimiento05")
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
			.formParam("username", "shelter1")
			.formParam("password", "shelter1")
			.formParam("_csrf", "16dc6aab-a88b-497b-b201-410e6edcd660"))
		.pause(20)
		// Logged
		.exec(http("myAnimalshelter")
			.get("/owners/myAnimalShelter"))
		.pause(21)
		// myAnimalshelter
		.exec(http("seeApplications")
			.get("/owners/adoptList/questionnaire/14?ownerId=11"))
		.pause(39)
		// seeApplications
		.exec(http("seeMoreApplications")
			.get("/owners/adoptList/questionnaire/show/1"))
		.pause(19)
		// seeMoreApplications
		.exec(http("request_6")
			.get("/logout"))
		.pause(1)
		.exec(http("Logout")
			.post("/logout")
			.headers(headers_2)
			.formParam("_csrf", "4d827afd-5396-47aa-9ce3-5f997c30c6c8"))
		.pause(7)
		// Logout

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}