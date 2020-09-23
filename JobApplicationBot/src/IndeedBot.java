import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class IndeedBot {
	// Create an object of WebDriver
	WebDriver driver;
	
	// Private job data
	private JobApplicationData jad;
	
	// Constructor
	public IndeedBot(JobApplicationData jad) {
		this.jad = jad;
	}
	
	// Navigate to Indeed url
	public void navigateToUrl() {
		System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
		driver = new ChromeDriver();
		System.out.println(jad.getURL());
		driver.get(jad.getURL());
		
		// Wait for elements to load
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		

	}
	
	public void login() throws InterruptedException {
		
		Thread.sleep(6000);
		
		WebElement signInLink = driver.findElement(By.className("gnav-LoggedOutAccountLink-text"));
		signInLink.click();
		
		driver.findElement(By.id("login-email-input")).clear();
		driver.findElement(By.id("login-password-input")).clear();
		
		driver.findElement(By.id("login-email-input")).sendKeys(jad.getEmail());
		driver.findElement(By.id("login-password-input")).sendKeys(jad.getPassword());
		
		Thread.sleep(5000);
		
		driver.findElement(By.id("login-submit-button")).click();
		
	}
	
	public void searchJobs() throws InterruptedException {
		WebElement findJobsTab = driver.findElement(By.className("gnav-PageLink-text"));

		findJobsTab.click();
		
        WebElement clearWhat = driver.findElement(By.id("text-input-what"));
        WebElement clearWhere = driver.findElement(By.id("text-input-where"));
        
        clearWhat.clear();
        clearWhat.sendKeys(jad.getWhat());
        clearWhere.clear();
        clearWhere.submit();

	}
	
	public void JobScrape() throws InterruptedException {
		
		// Create a list of JobCards
		List<WebElement> JobsCard = driver.findElements(By.className("jobsearch-SerpJobCard"));
		
		// For each job card
		for (int i = 0; i < JobsCard.size(); i++) {
			

			if (jad.getAppType().toLowerCase() == "easily apply") {
				// Check if an easily apply exists (has a classname of iaLabel)
				Boolean isPresent = JobsCard.get(i).findElements(By.className("iaLabel")).size() > 0;
				
				// If it does exist, open a new page for each easily apply
				if (isPresent) {
					System.out.println("yes");
					String selectLinkOpeninNewTab = Keys.chord(Keys.COMMAND,Keys.RETURN); 
					JobsCard.get(i).findElement(By.className("jobtitle")).sendKeys(selectLinkOpeninNewTab);
					
					applyToJobs();
					
				}
			}
		}
	}
	
	public void applyToJobs() throws InterruptedException {
		String parentHandle = driver.getWindowHandle(); // Get the current window
	    System.out.println(parentHandle);               // Prints the parent window handle 
                          
	    for (String winHandle : driver.getWindowHandles()) { // Gets the new window
	        System.out.println(winHandle);
	        driver.switchTo().window(winHandle);        // switch focus of WebDriver to the new window             
	    }
	    
	    // Now the driver works on current window opened
	    WebDriverWait wait = new WebDriverWait(driver, 3000);
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("indeedApplyButtonContainer")))).click();

	    // Switch to parent iframe element
	    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='Job application form container']")));
	    // Then switch to child iframe element 
	    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='Job application form']")));

	    // Then locate the name, email, and resume fields
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("input-applicant.name"))));
	    driver.findElement(By.id("input-applicant.name")).sendKeys(jad.getName());
	    
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("input-applicant.email"))));
	    driver.findElement(By.id("input-applicant.email")).sendKeys(jad.getEmail());
	    
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("input-applicant.phoneNumber"))));
	    driver.findElement(By.id("input-applicant.phoneNumber")).sendKeys(jad.getPhone());
	    
	    WebElement chooseFile = driver.findElement(By.id("ia-CustomFilePicker-resume"));
	    chooseFile.sendKeys(jad.getResume());
	    
	    // Click on continue button
	    WebElement continueBtn = driver.findElement(By.id("form-action-continue"));
	    Thread.sleep(5000);
	    continueBtn.click();
	    
	    Thread.sleep(5000);
	    
	    // Close that new window
	    driver.close(); 
	    // Switch back to the parent window
	    driver.switchTo().window(parentHandle);        

	}
	
	public void quitBrowser() {
		driver.quit();
	}
		
	
}
