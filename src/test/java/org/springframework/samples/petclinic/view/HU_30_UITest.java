
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
public class HU_30_UITest {
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
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("admin1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("4dm1n");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
	    driver.findElement(By.linkText("Create a new product")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("Gotas para ojos tortugas");
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("Para limpiar los ojos a los reptiles con problemas de conjuntivitis");
	    driver.findElement(By.id("image")).clear();
	    driver.findElement(By.id("image")).sendKeys("https://static.miscota.com/media/1/photos/products/038772/taberaqua-vitam-tortugas-20ml-4_1_g.jpg");
	    driver.findElement(By.xpath("//option[@value='true']")).click();
	    driver.findElement(By.id("price")).clear();
	    driver.findElement(By.id("price")).sendKeys("7.50");
	    new Select(driver.findElement(By.id("stock"))).selectByVisibleText("true");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    Assert.assertEquals("Gotas para ojos tortugas", driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr[5]/td")).getText());
	    Assert.assertEquals("Yes", driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr[5]/td[3]")).getText());
	    Assert.assertEquals("7.5", driver.findElement(By.xpath("//table[@id='questionnaireTable']/tbody/tr[5]/td[2]")).getText());
	    driver.findElement(By.xpath("(//a[contains(text(),'See more')])[5]")).click();
	    Assert.assertEquals("Gotas para ojos tortugas", driver.findElement(By.xpath("//td")).getText());
	    Assert.assertEquals("Para limpiar los ojos a los reptiles con problemas de conjuntivitis", driver.findElement(By.xpath("//tr[2]/td")).getText());
	    Assert.assertEquals("7.5", driver.findElement(By.xpath("//tr[3]/td")).getText());
	    Assert.assertEquals("Yes", driver.findElement(By.xpath("//tr[4]/td")).getText());
	    driver.findElement(By.linkText("Update")).click();
	    new Select(driver.findElement(By.id("stock"))).selectByVisibleText("false");
	    driver.findElement(By.xpath("//option[@value='false']")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'See more')])[5]")).click();
	    driver.findElement(By.linkText("Delete")).click();
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
	    driver.findElement(By.id("main-navbar")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
	    driver.findElement(By.linkText("Create a new product")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("Prueba");
	    driver.findElement(By.id("description")).clear();
	    driver.findElement(By.id("description")).sendKeys("Descripcion Prueba");
	    new Select(driver.findElement(By.id("stock"))).selectByVisibleText("false");
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    Assert.assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='product']/div[3]/div/span[2]")).getText());
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