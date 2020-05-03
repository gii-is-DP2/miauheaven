
package org.springframework.samples.petclinic.view;

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

import static org.junit.Assert.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;


public class HU_09_UITest {
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
	    driver.findElement(By.linkText("Login")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("admin1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("4dm1n");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    Assert.assertEquals("Â  admin1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
	    driver.findElement(By.linkText("Create new notification")).click();
	    driver.findElement(By.id("title")).clear();
	    driver.findElement(By.id("title")).sendKeys("Prueba de interfaz HU09");
	    driver.findElement(By.id("message")).clear();
	    driver.findElement(By.id("message")).sendKeys("Mensaje de prueba HU09");
	    driver.findElement(By.id("url")).clear();
	    driver.findElement(By.id("url")).sendKeys("https://mamimemima.com/es/4150-large_default/lamina-descargable-arco-iris-todo-ira-bien.jpg");
	    new Select(driver.findElement(By.id("target"))).selectByVisibleText("owner");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'See more')])[9]")).click();
	    Assert.assertEquals("Prueba de interfaz HU09", driver.findElement(By.xpath("//td")).getText());
	    Assert.assertEquals("Mensaje de prueba HU09", driver.findElement(By.xpath("//tr[2]/td")).getText());
	    Assert.assertEquals("owner", driver.findElement(By.xpath("//tr[4]/td")).getText());
	    driver.findElement(By.linkText("Click Here")).click();
	    Assert.assertEquals("lamina-descargable-arco-iris-todo-ira-bien.jpg (Imagen PNG, 458 × 458 píxeles)", driver.findElement(By.xpath("//html")).getText());
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
		    assertEquals("Â  admin1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
		    driver.findElement(By.linkText("List of notifications")).click();
		    driver.findElement(By.linkText("Create new notification")).click();
		    driver.findElement(By.id("title")).clear();
		    driver.findElement(By.id("title")).sendKeys("Prueba fallida");
		    driver.findElement(By.id("url")).clear();
		    driver.findElement(By.id("url")).sendKeys("https://mamimemima.com/es/4150-large_default/lamina-descargable-arco-iris-todo-ira-bien.jpg");
		    new Select(driver.findElement(By.id("target"))).selectByVisibleText("owner");
		    driver.findElement(By.xpath("//option[@value='owner']")).click();
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    assertEquals("no puede estar vacío", driver.findElement(By.xpath("//form[@id='notification']/div[2]/div/span[2]")).getText());
		 
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