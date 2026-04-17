package com.lab.app.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // 1. Inicialización: Se ejecuta ANTES de cada test
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
                By.xpath("//div[contains(@class, 'icon-profile-login-custom')]")
        ));
        btnMiCuenta.click();

        // 3. Esperar campos
        WebElement inputEmail = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[contains(@placeholder, 'ejemplo@mail.com')]")
        ));
        inputEmail.clear();
        inputEmail.sendKeys(email);

        WebElement inputPass = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[type='password'][class*='vtex-styleguide-9-x-input']")
        ));
        inputPass.sendKeys(password);

        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.name("chck_terms_cond")
        ));
        // 2. Scroll para asegurar que sea visible en el viewport
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);

        // 3. Clic con JavaScript
        // Esto es vital porque en Metro el input suele estar tapado por un <span> estético
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);

        System.out.println("Checkbox de términos marcado correctamente.");

        // 5. Click login
        WebElement btnSubmit = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[type='submit'], .vtex-login-2-x-sendButton button")
        ));
        btnSubmit.click();
    }

    @Test
    @DisplayName("Prueba de Login Fallido")
    public void testLoginConCredentialsInvalid() {
        realizarLogin("correo_falso@test.com", "Metro123$$");
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Usuario y/o contraseña equivocada')]")));
        assert errorMsg.isDisplayed() : "Error: El mensaje de error no apareció";
        System.out.println("Prueba Negativa: Pasó");
    }

    @Test
    @DisplayName("Prueba de Login Exitoso")
    public void testLoginExitoso() throws InterruptedException {
        // 1. realizar login
        realizarLogin("richard.palomino0218@gmail.com", "Metro123$$");
        Thread.sleep(5000);

        boolean clickExitoso = false;
        for (int i = 0; i < 2; i++) {
            try {
                WebElement imgPerfil = wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("img.vtex-store-components-3-x-imageElement--image-icon-account")
                ));

                // Usamos JavaScript para asegurar que el evento se dispare sin importar las capas
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", imgPerfil);

                // Verificamos si el botón de cerrar sesión aparece tras el clic
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-fake-session-0")));
                clickExitoso = true;
                break;
            } catch (Exception e) {
                System.out.println("Intento " + (i+1) + " fallido. Reintentando por refresco de página...");
                try { Thread.sleep(2000); } catch (InterruptedException ex) {}
            }
        }

        WebElement btnCerrarSesion = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-fake-session-0")));

        // 3. Aserción: Si el elemento es visible, el test pasa
        Assertions.assertTrue(btnCerrarSesion.isDisplayed(), "ERROR: El botón de cerrar sesión no es visible, el login pudo fallar.");

        System.out.println("LOG: Login exitoso verificado mediante el botón Cerrar Sesión.");
        System.out.println("Prueba Positiva: Pasó");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}