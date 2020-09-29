import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HumanBehavior {

    public void humanTyping(WebElement element, String jobData) throws InterruptedException {
        for (int i = 0; i < jobData.length(); i++) {
            TimeUnit.SECONDS.sleep((long) (Math.random() * (3 - 1) * +1));
            char c = jobData.charAt(i);
            String s = new StringBuilder().append(c).toString();
            element.sendKeys(s);
        }
    }

    public void randomScrolling(WebDriver driver) throws InterruptedException {
        Actions action = new Actions(driver);

        long scrollHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");

        for (int i = 0; i < 200; i++) {
            double randNum = Math.random() * (200 - 1 + 1) + 1;
            double anotherRandNum = Math.random() * (3 - 1 + 1) + 1;
            action.sendKeys(Keys.ARROW_DOWN);
            
            System.out.println(i);
            if (i > randNum) {
                System.out.println("sleeping");
                TimeUnit.SECONDS.sleep((long) anotherRandNum);
            }
            
            action.build().perform();
        }
        for (int i = 0; i < 100; i++) {
            double randNum = Math.random() * (100 - 1 + 1) + 1;
            double anotherRandNum = Math.random() * (3 - 1 + 1) + 1;
            action.sendKeys(Keys.ARROW_UP);
            
            System.out.println(i);
            
            if (i < randNum) {
                System.out.println("sleeping");
                TimeUnit.SECONDS.sleep((long) anotherRandNum);
            }
           
            action.build().perform();
        }
        
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.scrollBy(0,0)", "");
    
    }
    
    public void randomMouseMovement() {
        
    }

}
