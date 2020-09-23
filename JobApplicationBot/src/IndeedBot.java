import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * This class applies to jobs on Indeed.com.
 * 
 * @author Bruce Tieu
 *
 */
public class IndeedBot {
	// This creates an object of WebDriver
	WebDriver driver;
	
	// An object which holds job application information.
	private JobApplicationData jad;
	
	/**
	 * This is a class constructor which initializes the Job application data.
	 * 
	 * @param jad 
	 */
	public IndeedBot(JobApplicationData jad) {
		this.jad = jad;
	}
	
	/**
	 * This method navigates to the job page.
	 */
	public void navigateToUrl() {
		// Setting the Chrome driver
		System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
		// Create a ChromeDriver.
		driver = new ChromeDriver();
		// Navigate to the url.
		driver.get(jad.getURL());
		
		// Wait for page elements to load.
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	/**
	 * This method logs in to the job site.
	 * 
	 * @throws InterruptedException    if the thread executing the method is interrupted, stop the method and return early 
	 */
	public void login() throws InterruptedException {
		
		// Wait for 6 seconds before proceeding.
		Thread.sleep(6000);
		
		// Click the sign in button on the Indeed site.
		WebElement signInLink = driver.findElement(By.className("gnav-LoggedOutAccountLink-text"));
		signInLink.click();
		
		// Make sure the Email and Password fields are cleared out of any text.
		driver.findElement(By.id("login-email-input")).clear();
		driver.findElement(By.id("login-password-input")).clear();
		
		// Populate the fields with an email and a password
		driver.findElement(By.id("login-email-input")).sendKeys(jad.getEmail());
		driver.findElement(By.id("login-password-input")).sendKeys(jad.getPassword());
		
		// Wait for 5 seconds before proceeding.
		Thread.sleep(5000);
		
		// Sign in
		driver.findElement(By.id("login-submit-button")).click();
	}
	
	/**
	 * This method searches for jobs based on job position name and location
	 * 
	 * @throws InterruptedException    if the thread executing the method is interrupted, stop the method and return early 
	 */
	public void searchJobs() throws InterruptedException {
		// Create an actions object.
		Actions action = new Actions(driver);
		
		// Click on the "Find Jobs" tab.
		WebElement findJobsTab = driver.findElement(By.className("gnav-PageLink-text"));
		findJobsTab.click();
		
		// Locate the input for "What" and "Where"
        WebElement clearWhat = driver.findElement(By.id("text-input-what"));
        WebElement clearWhere = driver.findElement(By.id("text-input-where"));
        
        // Clear the "What" field and send in the position specified by the user.
        clearWhat.clear();
        clearWhat.sendKeys(jad.getWhat());
        
        // Clear the "Where" field and send in the location specified by the user.
        action.sendKeys(Keys.TAB);
        action.sendKeys(Keys.DELETE);
        action.build().perform();
        
        clearWhere.sendKeys(jad.getWhere());
        
        // Search for jobs based on the inputs.
        clearWhere.submit();
	}
	
	/**
	 * This method looks for Indeed Jobs that are easy to apply to (for now)
	 * 
	 * @throws InterruptedException    if the thread executing the method is interrupted, stop the method and return early 
	 */
	public void JobScrape() throws InterruptedException {
		
		// Create a list of type WebElement objects called JobsCard.
		List<WebElement> JobsCard = driver.findElements(By.className("jobsearch-SerpJobCard"));
		
		// Loop through each of the jobs present on the page.
		for (int i = 0; i < JobsCard.size(); i++) {
			
			// Check if there are jobs with "Easily Apply".
			if (jad.getAppType().toLowerCase() == "easily apply") {
				
				// If there are more than one of these "Easily Apply" jobs, then open that job in a new tab.
				if (JobsCard.get(i).findElements(By.className("iaLabel")).size() > 0) {
					System.out.println("Opening Easily Apply Job in another tab...");
					
					// COMMAND + RETURN opens a link in a new tab (MAC)
					// TODO: What about for Windows machines?
					String selectLinkOpeninNewTab = Keys.chord(Keys.COMMAND,Keys.RETURN); 
					JobsCard.get(i).findElement(By.className("jobtitle")).sendKeys(selectLinkOpeninNewTab);
					
					// Apply to each of those easy apply jobs until there are none on the first page.
					applyToJobs();	
					
					// Close the browser after all jobs are applied to
					quitBrowser();
				}
			}
		}
	}
	
	/**
	 * This method applies to "Easily Apply" jobs
	 * 
	 * @throws InterruptedException
	 */
	public void applyToJobs() throws InterruptedException {
		// Identify what the current window is
		String parentHandle = driver.getWindowHandle(); 
        
		// Locate the new window and switch the driver over to it.
	    for (String winHandle : driver.getWindowHandles()) {
	        driver.switchTo().window(winHandle);        
	    }
	    
	    // Now, the driver works on current window opened
	    
	    // This waits up to 30 seconds before throwing a TimeoutException or if it finds the element it will return it in 0 - 30 seconds
	    WebDriverWait wait = new WebDriverWait(driver, 30);
	    
	    // Wait until the following element appears before clicking on it.
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("indeedApplyButtonContainer")))).click();

	    // Switch to parent iframe element.
	    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='Job application form container']")));
	    // Switch to child iframe element.
	    driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='Job application form']")));

	    // Wait up to 30 seconds the name, email, and resume elements to appear, and fill them with job application information.
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
	    
	    
	    /**
	     * TODO: Figure out how to deal with differing application questions after the "continue" button is clicked,
	     * which varies with each "Easily apply" application
	     */
	    
	    // Close that new window (the job that was opened),
	    driver.close(); 
	    
	    // Switch back to the parent window (job listing window).
	    driver.switchTo().window(parentHandle);        

	}
	
	/**
	 * Quit the browser
	 */
	public void quitBrowser() {
		driver.quit();
	}
}
