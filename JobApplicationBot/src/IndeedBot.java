import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * This class applies to jobs on Indeed.com.
 * @author Bruce Tieu
 */
public class IndeedBot {
	private JobApplicationData jobAppData;
	private JobApplicationData.ApplicationType appType;
	private static final String _CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	private static final String _CHROME_DRIVER_PATH = "/Applications/chromedriver";
	private static final int MAX_WAIT_TIME = 30;
	
	// This creates an object of WebDriver.
    private WebDriver driver;
	
	/**
	 * This is a class constructor which initializes job application data, job application type, a chrome driver, and sets the variables for the driver.
	 * @param jobAppData The object which holds job application data.
	 * @param appType The enum type which is a set of application types.
	 */
	public IndeedBot(JobApplicationData jobAppData, JobApplicationData.ApplicationType appType) {
		this.jobAppData = jobAppData;
		this.appType = appType;
		System.setProperty(
			    IndeedBot._CHROME_DRIVER_PROPERTY,
			    IndeedBot._CHROME_DRIVER_PATH
		);
		this.driver = new ChromeDriver();
	}
	
	/**
	 * This method navigates to the job page.
	 */
	public void navigateToUrl() {
		// Navigate to the url.
		this.driver.get(jobAppData.url);
		
		// Wait for page elements to load.
		this.driver.manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
	}
	
	/**
	 * This method logs in to the job site.
	 * @throws InterruptedException If the thread executing the method is interrupted, stop the method and return early 
	 */
	public void login() throws InterruptedException {
		// This waits up to 15 seconds before throwing a TimeoutException or if it finds the element it will return it in 0 - 15 seconds.
		WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIME);
		
		// Wait for 15 seconds until the Sign In tab appears before clicking.
		wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.className("gnav-LoggedOutAccountLink-text")))).click();
		
		// Make sure the Email and Password fields are cleared out of any text.
		this.driver.findElement(By.id("login-email-input")).clear();
		this.driver.findElement(By.id("login-password-input")).clear();
		
		// Populate the fields with an email and a password
		this.driver.findElement(By.id("login-email-input")).sendKeys(jobAppData.email);
		this.driver.findElement(By.id("login-password-input")).sendKeys(jobAppData.password);
	
		// Wait until the following element appears before signing in.
		wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("login-submit-button")))).click();
	}
	
	/**
	 * This method searches for jobs based on job position name and location.
	 * @throws InterruptedException If the thread executing the method is interrupted, stop the method and return early. 
	 */
	public void searchJobs() throws InterruptedException {
		// Create an actions object.
		Actions action = new Actions(this.driver);
		
		// Click on the "Find Jobs" tab.
		WebElement findJobsTab = this.driver.findElement(By.className("gnav-PageLink-text"));
		findJobsTab.click();
		
		// Locate the "What" and "Where" input fields.
        WebElement clearWhat = this.driver.findElement(By.id("text-input-what"));
        WebElement clearWhere = this.driver.findElement(By.id("text-input-where"));
        
        // Clear the "What" field and send in the job position specified by the user.
        clearWhat.clear();
        clearWhat.sendKeys(jobAppData.whatJob);
        
        // Clear the "Where" field and send in the location of the job specified by the user.
        action.sendKeys(Keys.TAB);
        action.sendKeys(Keys.DELETE);
        action.build().perform();
        
        // Also send in the location of the job in the input field.
        clearWhere.sendKeys(jobAppData.locationOfJob);
        
        // Search for jobs based on the inputs.
        clearWhere.submit();
	}
	
	/**
	 * This method looks for Indeed Jobs that are easy to apply to (for now).
	 * @throws InterruptedException If the thread executing the method is interrupted, stop the method and return early. 
	 */
	public void jobScrape() throws InterruptedException {
		
		// Create a list of type WebElement objects called JobsCard.
		List<WebElement> JobsCard = this.driver.findElements(By.className("jobsearch-SerpJobCard"));
		
		// Loop through each of the jobs present on the page.
		for (int i = 0; i < JobsCard.size(); i++) {
			
			// Check if the appType that is passed in is an "Easily Apply" one.
			if (this.appType == JobApplicationData.ApplicationType.EASILY_APPLY) {
				
					// If there are one or more of these "Easily Apply" jobs, then open that job in a new tab.
					if (JobsCard.get(i).findElements(By.className("iaLabel")).size() > 0) {
						System.out.println("Opening Easily Apply Job in another tab...");
						
						// Get the current window.
						String parentWindow = this.driver.getWindowHandle();
						
						// Get the job link.
						String href = JobsCard.get(i).findElement(By.className("jobtitle")).getAttribute("href");
						System.out.println(href);
						
						// Use JavaScript to open a new tab instead of "control + t".
						((JavascriptExecutor)this.driver).executeScript("window.open()");
						// Store the available windows in a list.
					    ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
					    // Switch to the newly opened tab.
					    this.driver.switchTo().window(tabs.get(1));
					    // Navigate to the job link in that newly opened tab.
					    this.driver.get(href);
						
						/*
						 * Apply to each of those easy apply jobs until there are none on the first page, and pass
						 * in the parentWindow to keep track of the original job listing page as tabs are closed.
						*/
						applyToJobs(parentWindow);	
				}
			}
		}
	}
	
	/**
	 * This method applies to the "Easily Apply" jobs on Indeed.com.
	 * @param currWindow The window which holds all listings of jobs on a single page.
	 * @throws InterruptedException If the thread executing the method is interrupted, stop the method and return early. 
	 */
	public void applyToJobs(String currWindow) throws InterruptedException {
	    
	    // This waits up to 30 seconds before throwing a TimeoutException or if it finds the element it will return it in 0 - 30 seconds.
	    WebDriverWait wait = new WebDriverWait(this.driver, MAX_WAIT_TIME);
	    
	    // Wait until the following element appears before clicking on it.
	    wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("indeedApplyButtonContainer")))).click();

	    // Switch to parent iframe element.
	    this.driver.switchTo().frame(this.driver.findElement(By.cssSelector("iframe[title='Job application form container']")));
	    // Switch to child iframe element.
	    this.driver.switchTo().frame(this.driver.findElement(By.cssSelector("iframe[title='Job application form']")));

	    // Wait up to 30 seconds the name, email, and resume elements to appear, and fill them with job application information.
	    wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("input-applicant.name"))));
	    this.driver.findElement(By.id("input-applicant.name")).sendKeys(jobAppData.fullname);
	    wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("input-applicant.email"))));
	    this.driver.findElement(By.id("input-applicant.email")).sendKeys(jobAppData.email);
	    wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("input-applicant.phoneNumber"))));
	    this.driver.findElement(By.id("input-applicant.phoneNumber")).sendKeys(jobAppData.phone);
	    WebElement chooseFile = this.driver.findElement(By.id("ia-CustomFilePicker-resume"));
	    chooseFile.sendKeys(jobAppData.resumePath);
	    wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("form-action-continue")))).click();
	    
	    /**
	     * TODO: Figure out how to deal with differing application questions after the "continue" button is clicked,
	     * which varies with each "Easily apply" application.
	     */
	    
	    // Close that new window (the job that was opened).
	    this.driver.close(); 
	    
	    // Switch back to the parent window (job listing window).
	    this.driver.switchTo().window(currWindow);        

	}
	
	/**
	 * This method quits the browser.
	 */
	public void quitBrowser() {
		this.driver.quit();
	}
}
