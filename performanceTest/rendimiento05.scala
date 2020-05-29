package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento05 extends Simulation {

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
		
object Home{
	val home = exec(http("Home")
			.get("/"))
		.pause(7)
}

object Login{
	val login = exec(http("Login")
			.get("/login"))
		.pause(14)
}

object Logged{
 	val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "shelter1")
			.formParam("password", "shelter1")
			.formParam("_csrf", "16dc6aab-a88b-497b-b201-410e6edcd660"))
		.pause(20)
}
object MyAnimalshelter{
	val myAnimalshelter = exec(http("MyAnimalshelter")
			.get("/owners/myAnimalShelter"))
		.pause(21)
}

object SeeApplications{
	val seeApplications = exec(http("SeeApplications")
			.get("/owners/adoptList/questionnaire/14?ownerId=11"))
		.pause(39)
}
object SeeMoreApplications{
	val seeMoreApplications = exec(http("SeeMoreApplications")
			.get("/owners/adoptList/questionnaire/show/1"))
		.pause(19)
}

	val scn = scenario("rendimiento05").exec(Home.home, Login.login, Logged.logged, MyAnimalshelter.myAnimalshelter, SeeApplications.seeApplications, SeeMoreApplications.seeMoreApplications)
	

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}