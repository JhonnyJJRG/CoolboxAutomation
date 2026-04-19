package com.lab.app.tests;

import com.lab.app.pages.ProductPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ProductoTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.coolbox.pe/");
    }


    public void realizarLogin(String email, String password) {
        try {
            WebElement closePopup = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class*='close']")));
            closePopup.click();
        } catch (Exception e) {}

        WebElement btnMiCuenta = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-gtm='button-login']")
        ));
        btnMiCuenta.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-gtm='submit-login']")
        )).click();
    }

    @Test
    @DisplayName("Flujo completo: Login + Compra")
    public void testFlujoCompleto() {

        // 1. LOGIN
        realizarLogin("micanalxiexie@gmail.com", "Metro123$$");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Hola')]")
        ));

        // 2. COMPRA
        ProductPage productPage = new ProductPage(driver);
        productPage.comprarProducto("Laptop Msi");

        // 3. VALIDACIÓN FINAL
        Assertions.assertTrue(driver.getCurrentUrl().contains("checkout"),
                "No se llegó al checkout");

        System.out.println("Flujo completo OK");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}