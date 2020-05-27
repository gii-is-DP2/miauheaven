package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento7v2 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.png""", """.*.js""", """.*.jpg"""), WhiteList())
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

object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
}
 object Login {
  val login = exec(
      http("Login")
        .get("/login")
	.headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
	.resources(http("request_2")
	.get("/login")
	.headers(headers_2))
    ).pause(12)
    .exec(
      http("Logged")
        .post("/login")
        .headers(headers_3)
        .formParam("username", "owner5")
        .formParam("password", "0wn3r")        
        .formParam("_csrf", "${stoken}")
    ).pause(17)
  }
object ShowShelters{
		val showShelters = exec(http("ShowShelters")
			.get("/animalshelter")
			.headers(headers_0))
		.pause(21)
		// ShowShelters
}
object NewShelter{
		val newShelter= exec(http("ShelterForm")
			.get("/animalshelter/new")
			.headers(headers_0)
.check(css("input[name=_csrf]", "value").saveAs("stoken"))
)
		.pause(30)
		// ShelterForm
		.exec(http("NewShelter")
			.post("/animalshelter/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "Chips")
			.formParam("cif", "11111111Z")
			.formParam("place", "Gijon")
			.formParam("_csrf", "${stoken}"))
		.pause(10)
		// NewShelter
}

	val scn = scenario("rendimiento7v2")
		.exec(Home.home,Login.login,ShowShelters.showShelters,NewShelter.newShelter)
		
		

setUp(scn.inject(rampUsers(5000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}