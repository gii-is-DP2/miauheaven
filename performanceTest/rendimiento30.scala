package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class rendimiento30 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.jpg""", """.*.png""", """.*.js"""), WhiteList())
		.disableAutoReferer
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Referer" -> "http://www.dp2.com/",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Origin" -> "http://www.dp2.com",
		"Referer" -> "http://www.dp2.com/login",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map(
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_5 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Referer" -> "http://www.dp2.com/admin/product",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Origin" -> "http://www.dp2.com",
		"Referer" -> "http://www.dp2.com/admin/product/create",
		"Upgrade-Insecure-Requests" -> "1")

    val uri1 = "http://clientservices.googleapis.com/chrome-variations/seed"

object Home {
	val home = exec(http("Home")
			.get("/")
			.headers(headers_1))
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
			.headers(headers_1)
			.resources(http("request_4")
			.get(uri1 + "?osname=win&channel=stable&milestone=83")
			.headers(headers_4)))
		.pause(5)

}

object CreateProduct{
	val createProduct = exec(http("Create product")
			.get("/admin/product/create")
			.headers(headers_5)
	.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(5)	
		// CreateNotification
		.exec(http("Create Product")
			.post("/admin/product/save/")
			.headers(headers_6)
			.formParam("id", "")
			.formParam("name", "Prueba rendimiento")
			.formParam("description", "Prueba rendimiento")
			.formParam("price", "10")
			.formParam("stock", "true")
			.formParam("image", "https://www.google.es")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
		// CreatedNotification
}


	val scn = scenario("rendimiento30")
		.exec(Home.home,Login.login,ListProducts.listProducts,CreateProduct.createProduct)


	setUp(scn.inject(rampUsers(2500) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)


}