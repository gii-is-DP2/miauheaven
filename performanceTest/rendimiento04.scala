
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Rendimiento04 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources()
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Client-Request-Id" -> "{1991624E-F57C-4752-B64A-404715D49302}",
		"Content-Type" -> "text/xml",
		"Pragma" -> "no-cache",
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft Office/16.0 (Windows NT 10.0; Microsoft Outlook 16.0.11929; Pro)")

	val headers_9 = Map(
		"Accept" -> "image/webp,*/*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")

    val uri2 = "http://autodiscover.accenture.com/autodiscover/autodiscover.xml"

	val scn = scenario("rendimiento04")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(11)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0))
		.pause(15)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "shelter1")
			.formParam("password", "shelter1")
			.formParam("_csrf", "a06a621a-3988-4dee-af98-7d6cfbaf3763"))
		.pause(51)
		// Logged
		.exec(http("showEvents")
			.get("/events/")
			.headers(headers_0))
		.pause(30)
		// showEvents
		.exec(http("seeMoreDetails")
			.get("/events/1")
			.headers(headers_0))
		.pause(18)
		// seeMoreDetails
		.exec(http("editEventForm")
			.get("/events/1/edit")
			.headers(headers_0))
		.pause(1)
		// editEventForm
		.exec(http("updateEvent")
			.get(uri2)
			.headers(headers_6))
		.pause(20)
		// updateEvent
		.exec(http("newEventForm")
			.get("/events/new")
			.headers(headers_0))
		.pause(20)
		// newEventForm
		.exec(http("request_9")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_highlight-soft_100_eeeeee_1x100.png")
			.headers(headers_9)
			.resources(http("request_10")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_gloss-wave_35_f6a828_500x100.png")
			.headers(headers_9),
            http("request_11")
			.get("/webjars/jquery-ui/1.11.4/images/ui-icons_ffffff_256x240.png")
			.headers(headers_9),
            http("request_12")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_highlight-soft_75_ffe45c_1x100.png")
			.headers(headers_9),
            http("request_13")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_glass_100_f6f6f6_1x400.png")
			.headers(headers_9),
            http("request_14")
			.get("/webjars/jquery-ui/1.11.4/images/ui-bg_glass_100_fdf5ce_1x400.png")
			.headers(headers_9)))
		.pause(2)
		.exec(http("request_15")
			.get("/webjars/jquery-ui/1.11.4/images/ui-icons_ef8c08_256x240.png")
			.headers(headers_9))
		.pause(4)
		.exec(http("eventCreate")
			.post("/events/new")
			.headers(headers_2)
			.formParam("id", "")
			.formParam("name", "Evento1")
			.formParam("description", "Descripcion evento 1")
			.formParam("date", "2020/06/25")
			.formParam("_csrf", "c1ced00a-a133-4ce0-beb8-502f35f7b774"))
		.pause(54)
		// eventCreate
		.exec(http("request_17")
			.get("/logout")
			.headers(headers_0))
		.pause(6)
		.exec(http("Logout")
			.post("/logout")
			.headers(headers_2)
			.formParam("_csrf", "c1ced00a-a133-4ce0-beb8-502f35f7b774"))
		.pause(6)
		// Logout

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}