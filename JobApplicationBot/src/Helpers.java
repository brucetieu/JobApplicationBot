import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Helpers {
    
    
    public void tryFindElement(WebDriverWait wait) {

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("textarea"))).sendKeys("Monday");
        } catch (Exception e) {
            System.out.println("Textarea box not found");
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form-action-continue"))).click();
        } catch (Exception e) {
            System.out.println("Continue button not found");
        }
        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("ia-InterventionActionButtons-buttonDesktop")))
                    .click();
        } catch (Exception e) {
            System.out.println("Continue Applying button not found");
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form-action-submit"))).click();
        } catch (Exception e) {
            System.out.println("Apply button not found");
        }
    }

}
