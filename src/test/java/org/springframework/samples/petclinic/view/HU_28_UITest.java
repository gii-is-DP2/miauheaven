
package org.springframework.samples.petclinic.view;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class HU_28_UITest {

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
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("admin1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/admin/product')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'See more')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Update')]")).click();
		this.driver.findElement(By.id("price")).click();
		this.driver.findElement(By.id("price")).clear();
		this.driver.findElement(By.id("price")).sendKeys("7.98");
		new Select(this.driver.findElement(By.id("stock"))).selectByVisibleText("false");
		this.driver.findElement(By.xpath("//option[@value='false']")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Esto es una edicion de prueba");
		this.driver.findElement(By.xpath("//body/div/div")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assertions.assertEquals("Esto es una edicion de prueba", this.driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr/td")).getText());
		Assertions.assertEquals("7.98", this.driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr/td[2]")).getText());
		Assertions.assertEquals("No", this.driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr/td[3]")).getText());
	}

	@Test
	public void testNegative() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("admin1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/admin/product')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'See more')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Update')]")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("");
		this.driver.findElement(By.id("price")).click();
		this.driver.findElement(By.id("price")).clear();
		this.driver.findElement(By.id("price")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assertions.assertEquals("el tamaño tiene que estar entre 3 y 50", this.driver.findElement(By.xpath("//form[@id='product']/div/div/span[2]")).getText());
		Assertions.assertEquals("no puede ser null", this.driver.findElement(By.xpath("//form[@id='product']/div[3]/div/span[2]")).getText());
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
