
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Rendimiento24 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")



	val scn = scenario("rendimiento24")
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
			.formParam("_csrf", "94a4a493-89ba-4550-bef4-2c03fe747ef2"))
		.pause(8)
		// Logged
		.exec(http("myAnimalshelter")
			.get("/owners/myAnimalShelter"))
		.pause(23)
		// myAnimalshelter
		.exec(http("request_4")
			.get("/owners/myAnimalShelter/records"))
		.pause(16)
		// records
		.exec(http("addRecord")
			.get("/owners/myAnimalShelter/records/new"))
		.pause(12)
		// addRecord
		.exec(http("createRecord")
			.post("/owners/myAnimalShelter/records/new")
			.headers(headers_2)
			.formParam("owner_id", "2")
			.formParam("_csrf", "83a285d9-e8b0-4b94-8c13-5497843faf5a"))
		.pause(21)
		// createRecord
		.exec(http("request_7")
			.get("/logout"))
		.pause(1)
		.exec(http("Logout")
			.post("/logout")
			.headers(headers_2)
			.formParam("_csrf", "83a285d9-e8b0-4b94-8c13-5497843faf5a"))
		.pause(7)
		// Logout

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}