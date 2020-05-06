package org.springframework.samples.petclinic.view;

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
public class HU_2_UITest {
  
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
	this.driver.findElement(By.id("username")).click();
	this.driver.findElement(By.id("username")).sendKeys(Keys.DOWN);
	this.driver.findElement(By.id("username")).clear();
	this.driver.findElement(By.id("username")).sendKeys("owner1");
	this.driver.findElement(By.id("password")).click();
	this.driver.findElement(By.id("password")).clear();
	this.driver.findElement(By.id("password")).sendKeys("0wn3r");
	this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
    driver.findElement(By.linkText("Adopt it")).click();
    new Select(driver.findElement(By.id("vivienda"))).selectByVisibleText("Piso");
    driver.findElement(By.xpath("//option[@value='Piso']")).click();
    new Select(driver.findElement(By.id("ingresos"))).selectByVisibleText("Medios");
    driver.findElement(By.xpath("//option[@value='Medios']")).click();
    new Select(driver.findElement(By.id("horasLibres"))).selectByVisibleText("Menos de 3 horas");
    driver.findElement(By.xpath("//option[@value='Menos de 3 horas']")).click();
    new Select(driver.findElement(By.id("convivencia"))).selectByVisibleText("No");
    driver.findElement(By.xpath("//option[@value='No']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("George Franklin", driver.findElement(By.xpath("//b")).getText());
    this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
	this.driver.findElement(By.linkText("Logout")).click();
	this.driver.findElement(By.xpath("//button[@type='submit']")).click();
  }
  
  @Test
  public void testNegative() throws Exception {
	  driver.get("http://localhost:"+ this.port);
	  this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).sendKeys(Keys.DOWN);
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("owner1");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("0wn3r");
		this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
	    driver.findElement(By.linkText("Adopt it")).click();
	    new Select(driver.findElement(By.id("vivienda"))).selectByVisibleText("Casa");
	    driver.findElement(By.xpath("//option[@value='Casa']")).click();
	    new Select(driver.findElement(By.id("horasLibres"))).selectByVisibleText("Más de 6 horas");
	    driver.findElement(By.xpath("//option[@value='Más de 6 horas']")).click();
	    new Select(driver.findElement(By.id("convivencia"))).selectByVisibleText("No");
	    driver.findElement(By.xpath("//option[@value='No']")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("no puede estar vacío", driver.findElement(By.xpath("//form[@id='questionnaire']/div/div/div[2]/div/span[2]")).getText());
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

