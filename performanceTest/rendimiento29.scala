package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento29 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.jpg""", """.*.png""", """.*.js"""), WhiteList())
		.disableAutoReferer
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_1 = Map("Referer" -> "http://www.dp2.com/")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Referer" -> "http://www.dp2.com/login")

	val headers_4 = Map("Referer" -> "http://www.dp2.com/admin/product")


object Home {
	val home = exec(http("Home")
			.get("/"))
		.pause(5)
}
object Login{
	val login = exec(http("Login")
			.get("/login")
			.headers(headers_1)
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
object ListProducts{
	val listProducts = exec(http("List Products")
			.get("/admin/product")
			.headers(headers_1))
}

object ListOneProduct{
	val listOneProduct = exec(http("List Product")
			.get("/admin/product/1")
			.headers(headers_4))
		.pause(5)
}

	val scn = scenario("rendimiento29")
		.exec(Home.home,Login.login,ListProducts.listProducts,ListOneProduct.listOneProduct)


	setUp(scn.inject(rampUsers(10000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}