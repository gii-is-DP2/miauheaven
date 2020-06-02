package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento09 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.jpg""", """.*.png""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")

object Home {
	val home = exec(http("Home")
			.get("/"))
		.pause(5)
}
object Login{
	val login = exec(http("Login")
			.get("/login")
	.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(5)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(5)		
}
object ListNotifications{
	val listNotifications = exec(http("List Notification")
			.get("/admin/notification/"))
}

object NewNotification{
	val newNotification = exec(http("New Notification")
			.get("/admin/notification/new")
	.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(5)	
		// CreateNotification
		.exec(http("Create Notification")
			.post("/admin/notification/new")
			.headers(headers_2)
			.formParam("id", "")
			.formParam("title", "Prueba de rendimiento")
			.formParam("message", "Prueba de rendimiento")
			.formParam("url", "https://www.google.es")
			.formParam("target", "owner")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
		// CreatedNotification
}

	val scn = scenario("rendimiento09")
		.exec(Home.home, Login.login, ListNotifications.listNotifications, NewNotification.newNotification)
	setUp(scn.inject(rampUsers(750) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}