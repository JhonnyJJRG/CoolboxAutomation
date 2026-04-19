package com.lab.app.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import java.time.Duration;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void buscarProducto(String producto) {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'search')] | //button[contains(@aria-label,'buscar')]")
        )).click();

        By buscador = By.xpath("//input[@placeholder='¿Qué estás buscando?']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(buscador)).sendKeys(producto);
        wait.until(ExpectedConditions.visibilityOfElementLocated(buscador)).sendKeys(Keys.ENTER);
    }

    public void comprarProducto(String producto) {

        buscarProducto(producto);

        By productoLocator = By.xpath("//span[contains(text(),'Msi Thin15')]//ancestor::a");

        WebElement productoElemento = wait.until(ExpectedConditions.elementToBeClickable(productoLocator));
        productoElemento.click();


        wait.until(ExpectedConditions.stalenessOf(productoElemento));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(),'Añadir al carrito')]")
        ));

        By btnLocator = By.xpath("//span[contains(text(),'Añadir al carrito')]/ancestor::button");

        for (int i = 0; i < 3; i++) {
            try {
                WebElement btnAgregar = wait.until(ExpectedConditions.elementToBeClickable(btnLocator));

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnAgregar);

                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAgregar);

                break;

            } catch (Exception e) {
                System.out.println("Reintentando añadir al carrito...");
            }
        }

        WebElement btnPagar = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("proceed-to-checkout")
        ));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnPagar);


        wait.until(ExpectedConditions.urlContains("checkout"));

    }
}