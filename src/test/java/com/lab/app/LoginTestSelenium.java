package com.lab.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;


public class LoginTestSelenium {

  @Test
  public void testLoginSuccess() {
    WebDriver webDriver = new ChromeDriver();
    webDriver.get("https://www.saucedemo.com/");
    webDriver.manage().window().maximize();
    webDriver.findElement(By.id("user-name")).sendKeys("standard_user");
    webDriver.findElement(By.id("password")).sendKeys("secret_sauce");
    webDriver.findElement(By.xpath("//input[@id='login-button']")).click();

    WebElement title = webDriver.findElement(By.className("title"));
    assertTrue(title.getText().contains("Products"));
    webDriver.quit();

  }
}
