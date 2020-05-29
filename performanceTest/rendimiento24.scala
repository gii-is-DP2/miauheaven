package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento24 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.png""", """.*.js""", """.*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

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
		
object Home {
	val home = exec(http("Home")
			.get("/"))
		.pause(5)
}

object Login{
	val login = exec(http("Login")
			.get("/login"))
		.pause(12)
}

object Logged{
	val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "shelter1")
			.formParam("password", "shelter1")
			.formParam("_csrf", "94a4a493-89ba-4550-bef4-2c03fe747ef2"))
		.pause(8)
}

object MyAnimalshelter{
	val myAnimalshelter = exec(http("MyAnimalshelter")
			.get("/owners/myAnimalShelter"))
		.pause(23)
}

object Records{
	val records = exec(http("Records")
			.get("/owners/myAnimalShelter/records"))
		.pause(16)
}

object AddRecord{
	val addRecord = exec(http("AddRecord")
			.get("/owners/myAnimalShelter/records/new"))
		.pause(12)
}

object CreateRecord{
	val createRecord = exec(http("CreateRecord")
			.post("/owners/myAnimalShelter/records/new")
			.headers(headers_2)
			.formParam("owner_id", "2")
			.formParam("_csrf", "83a285d9-e8b0-4b94-8c13-5497843faf5a"))
		.pause(21)
}


	val scn = scenario("rendimiento24").exec(Home.home, Login.login, Logged.logged, MyAnimalshelter.myAnimalshelter, Records.records, AddRecord.addRecord, CreateRecord.createRecord)
		

	setUp(scn.inject(rampUsers(7000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95))
}