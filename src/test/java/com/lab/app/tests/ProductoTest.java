package com.lab.app.tests;

import com.lab.app.pages.ProductPage;
import com.lab.app.pages.CartPage; // IMPORTANTE: Importamos la nueva página
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// IMPORTACIONES EXCLUSIVAS DE TESTNG (Limpiamos las de JUnit)
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
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

    // En TestNG, la descripción va dentro de la etiqueta @Test
    @Test(description = "Flujo completo: Login + Compra")
    public void testFlujoCompleto() {

        // 1. LOGIN
        realizarLogin("micanalxiexie@gmail.com", "Metro123$$");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Hola')]")
        ));

        // 2. BUSCAR Y AGREGAR AL CARRITO
        ProductPage productPage = new ProductPage(driver);
        productPage.comprarProducto("Laptop Msi");

        // --- NUEVA INTEGRACIÓN DEL CARRITO ---
        // 3. IR A COMPRAR (Checkout)
        CartPage cartPage = new CartPage(driver);
        cartPage.clicIrAComprar();

        // 4. VALIDACIÓN FINAL
        // Actualizado para validar la ruta real a la que envía Coolbox
        wait.until(ExpectedConditions.urlContains("profile"));
        Assert.assertTrue(driver.getCurrentUrl().contains("profile"), "No se llegó al checkout (perfil)");

        System.out.println("Flujo completo OK");
        try {
            System.out.println("Pausando 5 segundos antes de cerrar...");
            Thread.sleep(5000); // 5000 milisegundos = 5 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Cambiamos @AfterEach (JUnit) a @AfterMethod (TestNG)
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}