
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
import org.springframework.boot.web.server.LocalServerPort;

public class HU_14_UITest {

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
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).sendKeys(Keys.DOWN);
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("owner5");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("0wn3r");
		this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		this.driver.findElement(By.linkText("Animal Shelter")).click();
		this.driver.findElement(By.linkText("Become an Animal Shelter")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Save Them");
		this.driver.findElement(By.id("cif")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [doubleClick | id=name | ]]
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("El Mundo con los animales");
		this.driver.findElement(By.id("cif")).click();
		this.driver.findElement(By.id("cif")).clear();
		this.driver.findElement(By.id("cif")).sendKeys("77863296Q");
		this.driver.findElement(By.id("cif")).click();
		this.driver.findElement(By.xpath("//form[@id='add-animalshelter-form']/div/div[2]")).click();
		this.driver.findElement(By.id("cif")).clear();
		this.driver.findElement(By.id("cif")).sendKeys("99999999A");
		this.driver.findElement(By.id("place")).click();
		this.driver.findElement(By.id("place")).clear();
		this.driver.findElement(By.id("place")).sendKeys("Seville");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("El Mundo con los animales", this.driver.findElement(By.xpath("//table[@id='animalsheltersTable']/tbody/tr[3]/td")).getText());
		this.driver.findElement(By.xpath("//div/div/div/div/div")).click();
	}
	@Test
	public void testNegative() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).sendKeys(Keys.DOWN);
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("owner6");
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("0wn3r");
		this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		this.driver.findElement(By.linkText("Animal Shelter")).click();
		this.driver.findElement(By.linkText("Become an Animal Shelter")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("More Animals");
		this.driver.findElement(By.id("cif")).click();
		this.driver.findElement(By.id("cif")).clear();
		this.driver.findElement(By.id("cif")).sendKeys("77863296Q");
		this.driver.findElement(By.xpath("//form[@id='add-animalshelter-form']/div/div[2]")).click();
		this.driver.findElement(By.id("cif")).click();
		this.driver.findElement(By.xpath("//form[@id='add-animalshelter-form']/div/div[2]")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [doubleClick | //form[@id='add-animalshelter-form']/div/div[2] | ]]
		this.driver.findElement(By.id("cif")).click();
		this.driver.findElement(By.id("cif")).clear();
		this.driver.findElement(By.id("cif")).sendKeys("11111111Z");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("no puede estar vacío", this.driver.findElement(By.xpath("//form[@id='add-animalshelter-form']/div/div[3]/div/span[2]")).getText());
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
