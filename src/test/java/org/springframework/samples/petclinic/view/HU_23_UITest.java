package org.springframework.samples.petclinic.view;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

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
public class HU_23_UITest {
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
		driver = new FirefoxDriver();
	    baseUrl = "https://www.google.com/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testPositive() throws Exception {
	    driver.get("http://localhost:" + this.port);
	    this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//div")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("shelter1");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("shelter1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/span")).click();
	    driver.findElement(By.linkText("Create new Event")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("AnimalFestival");
	    driver.findElement(By.id("description")).click();
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("Festival de mascotas");
	    driver.findElement(By.id("date")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
	    driver.findElement(By.linkText("3")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("2021-02-03", driver.findElement(By.xpath("//tr[2]/td")).getText());
	    this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.linkText("Logout")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	  }
	  
	  @Test
	  public void testNegative() throws Exception {
		  driver.get("http://localhost:" + this.port);
		  this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
			this.driver.findElement(By.xpath("//div")).click();
		    driver.findElement(By.id("username")).click();
		    driver.findElement(By.id("username")).clear();
		    driver.findElement(By.id("username")).sendKeys("shelter1");
		    driver.findElement(By.id("password")).click();
		    driver.findElement(By.id("password")).clear();
		    driver.findElement(By.id("password")).sendKeys("shelter1");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/span")).click();
		    driver.findElement(By.linkText("Create new Event")).click();
		    driver.findElement(By.id("name")).click();
		    driver.findElement(By.id("name")).clear();
		    driver.findElement(By.id("name")).sendKeys("Evento Anual");
		    driver.findElement(By.id("description")).click();
		    driver.findElement(By.id("description")).clear();
		    driver.findElement(By.id("description")).sendKeys("Evento relacionado con mascotas");
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='event']/div/div/div[3]/div/span[2]")).getText());
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
