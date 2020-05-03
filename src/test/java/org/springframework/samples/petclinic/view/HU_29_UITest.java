
package org.springframework.samples.petclinic.view;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
public class HU_29_UITest {
	@LocalServerPort
	private int				port;
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	
	
	
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
		driver.get("http://localhost:8080/");
	    driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("admin1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("4dm1n");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
	    Assert.assertEquals("Comida para perros", driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr/td")).getText());
	    Assert.assertEquals("12.2", driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr/td[2]")).getText());
	    Assert.assertEquals("Yes", driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr/td[3]")).getText());
	    driver.findElement(By.linkText("See more")).click();
	    Assert.assertEquals("Comida para perros", driver.findElement(By.xpath("//td")).getText());
	    Assert.assertEquals("La mejor comida para alimentar a nuestros compañeros caninos", driver.findElement(By.xpath("//tr[2]/td")).getText());
	    Assert.assertEquals("12.2", driver.findElement(By.xpath("//tr[3]/td")).getText());
	    Assert.assertEquals("Yes", driver.findElement(By.xpath("//tr[4]/td")).getText());
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
	    driver.findElement(By.linkText("Logout")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	  }
	
	@Test
	  public void testNegative() throws Exception {
		driver.get("http://localhost:8080/");
	    driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("admin1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("4dm1n");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
	    driver.get("http://localhost:8080/admin/product/aaa");
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
	    driver.findElement(By.linkText("Logout")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	  }
	
	@AfterEach
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
	}