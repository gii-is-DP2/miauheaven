
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HU_05_UITest {

	private WebDriver			driver;
	private String				baseUrl;
	private boolean				acceptNextAlert		= true;
	private final StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		final String pathToGeckoDriver = System.getenv("webdriver.gecko.driver");
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testListaAplicaciones() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("shelter1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/myAnimalShelter')]")).click();
		Assert.assertEquals("Pets and Visits", this.driver.findElement(By.xpath("//h2[2]")).getText());
		Assert.assertEquals("See applications", this.driver.findElement(By.xpath("//a[contains(text(),'See applications')]")).getText());
		this.driver.findElement(By.xpath("//a[contains(text(),'See applications')]")).click();
		Assert.assertEquals("Applications for Desto", this.driver.findElement(By.xpath("//h2")).getText());
		Assert.assertEquals("George Franklin", this.driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr/td")).getText());
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/adoptList/questionnaire/show/1')]")).click();
		Assert.assertEquals("Desto", this.driver.findElement(By.xpath("//td")).getText());
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/logout')]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		final String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString))
			Assert.fail(verificationErrorString);
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
			if (this.acceptNextAlert)
				alert.accept();
			else
				alert.dismiss();
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}
