
package org.springframework.samples.petclinic.view;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.web.server.LocalServerPort;

public class HU_16_UITest {

	@LocalServerPort
	private int				port;
	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = System.getenv("webdriver.gecko.driver");
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testPositive() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		this.driver.findElement(By.linkText("My animal shelter")).click();
		this.driver.findElement(By.linkText("Add Appointment")).click();
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.linkText("31")).click();
		this.driver.findElement(By.id("cause")).click();
		this.driver.findElement(By.id("cause")).clear();
		this.driver.findElement(By.id("cause")).sendKeys("No anda bien");
		this.driver.findElement(By.name("vet_id")).click();
		new Select(this.driver.findElement(By.name("vet_id"))).selectByVisibleText("LindaDouglas");
		this.driver.findElement(By.name("vet_id")).click();
		this.driver.findElement(By.name("urgent")).click();
		new Select(this.driver.findElement(By.name("urgent"))).selectByVisibleText("Yes");
		this.driver.findElement(By.name("urgent")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//html")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.linkText("Logout")).click();
		this.driver.findElement(By.xpath("//form[@action='/logout']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [doubleClick | id=username | ]]
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("vet3");
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("v3t");
		this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		this.driver.findElement(By.linkText("See appointments")).click();
		Assert.assertEquals("Pichú Animales Shelter", this.driver.findElement(By.xpath("//td")).getText());
		Assert.assertEquals("2020-08-31", this.driver.findElement(By.xpath("//td[3]")).getText());
		Assert.assertEquals("true", this.driver.findElement(By.xpath("//td[4]")).getText());
	}
	@Test
	public void testNegative() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		this.driver.findElement(By.linkText("My animal shelter")).click();
		this.driver.findElement(By.linkText("Add Appointment")).click();
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.linkText("24")).click();
		this.driver.findElement(By.id("cause")).click();
		this.driver.findElement(By.id("cause")).click();
		this.driver.findElement(By.id("cause")).clear();
		this.driver.findElement(By.id("cause")).sendKeys("No anda bien");
		this.driver.findElement(By.name("vet_id")).click();
		Assert.assertEquals("null", this.driver.findElement(By.name("urgent")).getAttribute("value"));
		new Select(this.driver.findElement(By.name("vet_id"))).selectByVisibleText("SharonJenkins");
		this.driver.findElement(By.name("vet_id")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Caracter de la cita no definido", this.driver.findElement(By.xpath("//form[@id='appointment']/div/div/div/span[2]")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
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
