import com.lab.app.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    By btnMiCuenta = By.xpath("//a[contains(text(),'Mi cuenta')]");

    By inputEmail = By.id("email");
    By inputPassword = By.id("pass");
    By btnLogin = By.id("send2");


    By mensajeError = By.xpath("//div[contains(@class,'error')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void abrirLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(btnMiCuenta));
        driver.findElement(btnMiCuenta).click();
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));

        driver.findElement(inputEmail).sendKeys(email);
        driver.findElement(inputPassword).sendKeys(password);
        driver.findElement(btnLogin).click();
    }


    public String obtenerError() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(mensajeError));
        return driver.findElement(mensajeError).getText();
    }
}