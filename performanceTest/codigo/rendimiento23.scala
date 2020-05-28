package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento23 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_7 = Map(
		"Accept-Encoding" -> "gzip,deflate",
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Apache-HttpClient/4.5.6 (Java/1.8.0_221)")

	val headers_11 = Map(
		"Accept-Encoding" -> "gzip,deflate",
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "p2/1.2.100.v20180822-1354 (Java 1.8.0_221-b11 Oracle Corporation; Windows10 10.0.0 x86-64; es_ES) org.eclipse.epp.package.java.product/4.12.0.I20190605-1800 (org.eclipse.ui.ide.workbench)")

    val uri2 = "http://download.eclipse.org"


	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)

	
	}

	object Login{
		val login= exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(20)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "shelter1")
			.formParam("password", "shelter1")
			.formParam("_csrf", "${stoken}"))
		.pause(13)

	
	}

	object EventList{
		val eventList= exec(http("eventList")
			.get("/events/")
			.headers(headers_0))
		.pause(13)

	
	}


	object EventCreate{
		val eventCreate= exec(http("EventForm")
			.get("/events/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(16)
		.exec(http("eventCreated")
			.post("/events/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "Prueba")
			.formParam("description", "Prueba")
			.formParam("date", "2020/05/30")
			.formParam("_csrf", "${stoken}"))
		.pause(6)

	
	}

	val scn = scenario("rendimiento23").exec(
					Home.home,
					Login.login,
					EventList.eventList,
					EventCreate.eventCreate)
		
		

	setUp(scn.inject(rampUsers(11500) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}