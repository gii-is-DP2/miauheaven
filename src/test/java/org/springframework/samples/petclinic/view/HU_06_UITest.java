
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

public class HU_06_UITest {

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
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("shelter1");
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("shelter1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
		this.driver.findElement(By.linkText("Add New Pet")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Flipper");
		this.driver.findElement(By.id("birthDate")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/table/thead/tr/th[6]")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		this.driver.findElement(By.linkText("Prev")).click();
		this.driver.findElement(By.linkText("Prev")).click();
		this.driver.findElement(By.linkText("11")).click();
		new Select(this.driver.findElement(By.id("type"))).selectByVisibleText("dog");
		this.driver.findElement(By.xpath("(//option[@value='dog'])[1]")).click();
		new Select(this.driver.findElement(By.id("genre"))).selectByVisibleText("male");
		this.driver.findElement(By.xpath("//option[@value='male']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Flipper", this.driver.findElement(By.xpath("//tr[2]/td/dl/dd")).getText());
	}

	@Test
	public void testNegative() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [doubleClick | id=username | ]]
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [doubleClick | id=username | ]]
		this.driver.findElement(By.xpath("//form[@action='/login']")).click();
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("shelter1");
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		this.driver.findElement(By.linkText("My animal shelter")).click();
		this.driver.findElement(By.linkText("Add New Pet")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Chips");
		new Select(this.driver.findElement(By.id("type"))).selectByVisibleText("dog");
		this.driver.findElement(By.xpath("(//option[@value='dog'])[1]")).click();
		new Select(this.driver.findElement(By.id("genre"))).selectByVisibleText("female");
		this.driver.findElement(By.xpath("//option[@value='female']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("is required", this.driver.findElement(By.xpath("//form[@id='pet']/div/div[3]/div/span[2]")).getText());
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
