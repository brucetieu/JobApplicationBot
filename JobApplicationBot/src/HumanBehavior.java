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

        for (int i = 0; i < 150; i++) {
            double randNum = Math.random() * (150 - 1 + 1) + 1;
            double anotherRandNum = Math.random() * (3 - 1 + 1) + 1;
            action.sendKeys(Keys.ARROW_DOWN);
            
            System.out.println(i);
            if (i > randNum) {
                System.out.println("sleeping");
                TimeUnit.SECONDS.sleep((long) anotherRandNum);
            }
            
            action.build().perform();
        }
        for (int i = 0; i < 75; i++) {
            double randNum = Math.random() * (75 - 1 + 1) + 1;
            double anotherRandNum = Math.random() * (3 - 1 + 1) + 1;
            action.sendKeys(Keys.ARROW_UP);
            
            System.out.println(i);
            
            if (i < randNum) {
                System.out.println("sleeping");
                TimeUnit.SECONDS.sleep((long) anotherRandNum);
            }
           
            action.build().perform();
        }

    
    }
    
    public void randomMouseMovement(WebDriver driver) {
        Actions action = new Actions(driver);
        action.moveByOffset(5,5).perform(); 
        action.moveByOffset(10, 15).perform(); 

        
    }

}
