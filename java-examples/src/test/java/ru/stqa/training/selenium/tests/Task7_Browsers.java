package ru.stqa.training.selenium.tests;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task7_Browsers {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 10);
  }

  @Test
  public void testTask7() {
    driver.get("http://localhost/litecard/admin/login.php");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();

    List<WebElement> mainMenu = driver.findElements(By.xpath("//ul[@id='box-apps-menu']//li"));

    new WebDriverWait(driver, 10)
        .until(ExpectedConditions.visibilityOfAllElements(mainMenu));

    for (int i = 1; i <= mainMenu.size(); i++) {
      driver.findElement(By.xpath("(//ul[@id='box-apps-menu']//li[@id='app-'])[" + i + "]"))
          .click();
      wait.until(presenceOfElementLocated(By.xpath("//h1")));
      //если есть вложенное меню
      if (isElementPresent(
          By.xpath("(//ul[@id='box-apps-menu']//li[@id='app-'])[" + i + "]//li"))) {

        List<WebElement> insertedMenu = driver
            .findElements(By.xpath("(//ul[@id='box-apps-menu']//li[@id='app-'])[" + i + "]//li"));

        for (int j = 1; j <= insertedMenu.size(); j++) {
          driver.findElement(
              By.xpath("(//ul[@id='box-apps-menu']//li[@id='app-'])[" + i + "]//li[" + j + "]"))
              .click();
          wait.until(presenceOfElementLocated(By.xpath("//h1")));
        }
      }
    }
  }

  public boolean isElementPresent(By locator) {
    try {
      driver.findElement(locator);
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }
}
