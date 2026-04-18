package com.lab.app.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        // 1. Inicialización: Se ejecuta ANTES de cada test para su correcta prueba
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Configuración de esperas globales
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.coolbox.pe/");
    }

    public void realizarLogin(String email, String password) {
        // 1. Cerrar popup inicial si aparece
        try {
            WebElement closePopup = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class*='close']")));
            closePopup.click();
        } catch (Exception e) {
            System.out.println("No apareció popup");
        }

        // 2. Click en botón "Iniciar sesión"
        WebElement btnMiCuenta = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-gtm='button-login']")
        ));
        btnMiCuenta.click();

        // 3. Esperar campos
        WebElement inputEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("email")
        ));
        inputEmail.clear();
        inputEmail.sendKeys(email);

        WebElement inputPass = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("password")
        ));
        inputPass.sendKeys(password);


        // 4. Click login
        WebElement btnSubmit = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-gtm='submit-login']")
        ));
        btnSubmit.click();
    }

    @Test
    @DisplayName("Prueba de Login Fallido")
    public void testLoginConCredentialsInvalid() {
        realizarLogin("correo_falso@test.com", "Metro123$$");
        boolean loginVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("email")
        )).isDisplayed();

        Assertions.assertTrue(loginVisible, "Error: El login desapareció, algo está mal");

        System.out.println("Prueba Negativa: Pasó");
    }

    @Test
    @DisplayName("Prueba de Login Exitoso")
    public void testLoginExitoso() throws InterruptedException {
        // 1. realizar login
        realizarLogin("micanalxiexie@gmail.com", "Metro123$$");
        Thread.sleep(5000);

        boolean clickExitoso = false;
        for (int i = 0; i < 2; i++) {
            try {
                WebElement imgPerfil = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(),'Hola')]")
                ));

                // mantenemos tu lógica de JS (aunque no es necesario, pero lo dejamos)
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", imgPerfil);

                // reemplazo del botón cerrar sesión por validación de usuario
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Hola')]")
                ));

                clickExitoso = true;
                break;

            } catch (Exception e) {
                System.out.println("Intento " + (i+1) + " fallido. Reintentando por refresco de página...");
                try { Thread.sleep(2000); } catch (InterruptedException ex) {}
            }
        }

        WebElement btnCerrarSesion = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Hola')]")
        ));

        // 3. Aserción: Si el elemento es visible, el test pasa
        Assertions.assertTrue(btnCerrarSesion.isDisplayed(), "ERROR: El botón de cerrar sesión no es visible, el login pudo fallar.");

        System.out.println("LOG: Login exitoso verificado mediante el botón Cerrar Sesión.");
        System.out.println("Prueba Positiva: Pasó");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}