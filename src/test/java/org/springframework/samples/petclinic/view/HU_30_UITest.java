
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
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HU_30_UITest {

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
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("admin1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		this.driver.findElement(By.linkText("Create a new product")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Gotas para ojos tortugas");
		this.driver.findElement(By.id("description")).clear();
		this.driver.findElement(By.id("description")).sendKeys("Para limpiar los ojos a los reptiles con problemas de conjuntivitis");
		this.driver.findElement(By.id("image")).clear();
		this.driver.findElement(By.id("image")).sendKeys("https://static.miscota.com/media/1/photos/products/038772/taberaqua-vitam-tortugas-20ml-4_1_g.jpg");
		this.driver.findElement(By.xpath("//option[@value='true']")).click();
		this.driver.findElement(By.id("price")).clear();
		this.driver.findElement(By.id("price")).sendKeys("7.50");
		new Select(this.driver.findElement(By.id("stock"))).selectByVisibleText("true");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Gotas para ojos tortugas", this.driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr[3]/td")).getText());
		Assert.assertEquals("Yes", this.driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr[3]/td[3]")).getText());
		Assert.assertEquals("7.5", this.driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr[3]/td[2]")).getText());
		this.driver.findElement(By.xpath("(//a[contains(text(),'See more')])[3]")).click();
		Assert.assertEquals("Gotas para ojos tortugas", this.driver.findElement(By.xpath("//td")).getText());
		Assert.assertEquals("Para limpiar los ojos a los reptiles con problemas de conjuntivitis", this.driver.findElement(By.xpath("//tr[2]/td")).getText());
		Assert.assertEquals("7.5", this.driver.findElement(By.xpath("//tr[3]/td")).getText());
		Assert.assertEquals("Yes", this.driver.findElement(By.xpath("//tr[4]/td")).getText());
		this.driver.findElement(By.linkText("Update")).click();
		new Select(this.driver.findElement(By.id("stock"))).selectByVisibleText("false");
		this.driver.findElement(By.xpath("//option[@value='false']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("(//a[contains(text(),'See more')])[3]")).click();
		this.driver.findElement(By.linkText("Delete")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.linkText("Logout")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

	}

	@Test
	public void testNegative() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("admin1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.id("main-navbar")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		this.driver.findElement(By.linkText("Create a new product")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Prueba");
		this.driver.findElement(By.id("description")).clear();
		this.driver.findElement(By.id("description")).sendKeys("Descripcion Prueba");
		new Select(this.driver.findElement(By.id("stock"))).selectByVisibleText("false");
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("no puede ser null", this.driver.findElement(By.xpath("//form[@id='product']/div[3]/div/span[2]")).getText());
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.linkText("Logout")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
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
