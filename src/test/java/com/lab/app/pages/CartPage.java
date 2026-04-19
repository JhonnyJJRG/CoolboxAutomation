package com.lab.app.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private By btnIrAComprar = By.xpath("//a[contains(@href, 'shipping')] | //span[contains(text(), 'Ir a comprar')]");

    public CartPage(WebDriver driver) {
        this.driver = driver;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clicIrAComprar() {
        try {

            WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(btnIrAComprar));
            boton.click();
        } catch (Exception e) {
            System.out.println("El clic normal fue bloqueado o tardó, forzando interacción con Javascript...");


            WebElement botonJS = wait.until(ExpectedConditions.presenceOfElementLocated(btnIrAComprar));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonJS);
        }
    }
}