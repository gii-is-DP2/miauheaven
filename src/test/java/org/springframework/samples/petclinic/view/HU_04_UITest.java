
package org.springframework.samples.petclinic.view;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class HU_04_UITest {

	private WebDriver			driver;
	private String				baseUrl;
	private boolean				acceptNextAlert		= true;
	private final StringBuffer	verificationErrors	= new StringBuffer();

	@LocalServerPort
	private int					port;


	@BeforeEach
	public void setUp() throws Exception {
		final String pathToGeckoDriver = System.getenv("webdriver.gecko.driver");
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testEditarEvento() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("shelter1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/span[2]")).click();
		Assert.assertEquals("See more", this.driver.findElement(By.xpath("//a[contains(text(),'See more')]")).getText());
		this.driver.findElement(By.xpath("//a[contains(text(),'See more')]")).click();
		Assert.assertEquals("Edit Event", this.driver.findElement(By.xpath("//a[contains(text(),'Edit Event')]")).getText());
		this.driver.findElement(By.xpath("//a[contains(text(),'Edit Event')]")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("AnimalFest");
		Assert.assertEquals("Update Event", this.driver.findElement(By.xpath("//button[@type='submit']")).getText());
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("AnimalFest", this.driver.findElement(By.xpath("//b")).getText());
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/logout')]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Test
	public void testNoSePuedeEditarDejandoCamposVacios() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("shelter1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'See more')]")).click();
		Assert.assertEquals("Edit Event", this.driver.findElement(By.xpath("//a[contains(text(),'Edit Event')]")).getText());
		this.driver.findElement(By.xpath("//a[contains(text(),'Edit Event')]")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("el tamaño tiene que estar entre 3 y 50", this.driver.findElement(By.xpath("//form[@id='event']/div/div/div/div/span[2]")).getText());
		this.driver.findElement(By.id("description")).click();
		this.driver.findElement(By.id("description")).clear();
		this.driver.findElement(By.id("description")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("no puede estar vacío", this.driver.findElement(By.xpath("//form[@id='event']/div/div/div[2]/div/span[2]")).getText());
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.id("date")).clear();
		this.driver.findElement(By.id("date")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("no puede ser null", this.driver.findElement(By.xpath("//form[@id='event']/div/div/div[3]/div/span[2]")).getText());
	}

	//	@Test
	//	public void testSoloProtectoraPuedeEditarEventos() throws Exception {
	//		this.driver.get("http://localhost:" + this.port);
	//		this.driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
	//		this.driver.findElement(By.id("username")).clear();
	//		this.driver.findElement(By.id("username")).sendKeys("owner1");
	//		this.driver.findElement(By.id("password")).clear();
	//		this.driver.findElement(By.id("password")).sendKeys("0wn3r");
	//		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	//		this.driver.findElement(By.xpath("//a[contains(@href, '/events/')]")).click();
	//		this.driver.findElement(By.xpath("//a[contains(text(),'See more')]")).click();
	//		Assert.assertEquals("Edit Event", this.driver.findElement(By.xpath("//a[contains(text(),'Edit Event')]")).getText());
	//		this.driver.findElement(By.xpath("//a[contains(text(),'Edit Event')]")).click();
	//		Assert.assertEquals("Something happened...", this.driver.findElement(By.xpath("//h2")).getText());
	//		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
	//		this.driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
	//		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	//}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		final String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (final NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (final NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			final Alert alert = this.driver.switchTo().alert();
			final String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}
