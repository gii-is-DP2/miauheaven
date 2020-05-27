package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento16v2 extends Simulation {

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
        .formParam("username", "shelter2")
        .formParam("password", "shelter2")        
        .formParam("_csrf", "${stoken}")
    ).pause(17)
  }
object AnimalShelter{
		val animalShelter = exec(http("My animal shelter")
			.get("/owners/myAnimalShelter")
			.headers(headers_0))
		.pause(9)
	}
object NewAppointment{
		val newAppointment = exec(http("AppointmentForm")
			.get("/owners/12/pets/15/appointment/new")
			.headers(headers_0)
	.check(css("input[name=_csrf]", "value").saveAs("stoken"))
)
		.pause(50)
		// AppointmentForm 
		.exec(http("NewAppointment")
			.post("/owners/12/pets/15/appointment/new")
			.headers(headers_3)
			.formParam("date", "2020/05/23")
			.formParam("cause", "No come")
			.formParam("urgent", "1")
			.formParam("vet_id", "2")
			.formParam("_csrf", "${stoken}"))
		.pause(34)
		// NewAppointment
	}

	val scn = scenario("rendimiento16v2")
		.exec(Home.home,Login.login,AnimalShelter.animalShelter,NewAppointment.newAppointment)

setUp(scn.inject(rampUsers(7000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}