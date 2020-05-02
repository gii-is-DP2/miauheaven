
package org.springframework.samples.petclinic.view;

import java.util.concurrent.TimeUnit;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class HU_09_UITest {
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
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("admin1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("4dm1n");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
    driver.findElement(By.xpath("//table[@id='notificationsTable']/thead/tr/th")).click();
    driver.findElement(By.linkText("Create new notification")).click();
    driver.findElement(By.id("title")).click();
    driver.findElement(By.id("title")).clear();
    driver.findElement(By.id("title")).sendKeys("Prueba");
    driver.findElement(By.id("title")).sendKeys(Keys.DOWN);
    driver.findElement(By.id("title")).clear();
    driver.findElement(By.id("title")).sendKeys("Prueba de interfaz");
    driver.findElement(By.id("message")).clear();
    driver.findElement(By.id("message")).sendKeys("Esto es una prueba de interfaz");
    driver.findElement(By.id("url")).clear();
    driver.findElement(By.id("url")).sendKeys("https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg");
    assertEquals("Â  admin1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
    new Select(driver.findElement(By.id("target"))).selectByVisibleText("animal_shelter");
    driver.findElement(By.xpath("//option[@value='animal_shelter']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("(//a[contains(text(),'See more')])[7]")).click();
  }
  @Test
  public void testNegative() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("admin1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("4dm1n");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
    driver.findElement(By.linkText("Create new notification")).click();
    driver.findElement(By.id("title")).clear();
    driver.findElement(By.id("title")).sendKeys("Prueba fallida");
    driver.findElement(By.id("url")).clear();
    driver.findElement(By.id("url")).sendKeys("https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg");
    assertEquals("Â  admin1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
    new Select(driver.findElement(By.id("target"))).selectByVisibleText("owner");
    driver.findElement(By.xpath("//option[@value='owner']")).click();
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