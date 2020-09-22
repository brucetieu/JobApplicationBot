import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;


public class IndeedBot {
	// Create an object of WebDriver
	WebDriver driver;
	
    // Member variables
	String url, username, password, what, where, application_type;
	
	// No parameter constructor
	IndeedBot() {}
	
	// Constructor with parameters
	public IndeedBot(String url, String username, String password, String what, String where,
			String application_type) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.what = what;
		this.where = where;
		this.application_type = application_type;
	}
	
	public void navigateToUrl() {
		System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
		driver = new ChromeDriver();
		driver.get(this.url);
		
		// Wait for elements to load
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		

	}
	
	public void login() throws InterruptedException {
		
		Thread.sleep(6000);
		
		WebElement signInLink = driver.findElement(By.className("gnav-LoggedOutAccountLink-text"));
		signInLink.click();
		
		driver.findElement(By.id("login-email-input")).clear();
		driver.findElement(By.id("login-password-input")).clear();
		
		driver.findElement(By.id("login-email-input")).sendKeys(this.username);
		driver.findElement(By.id("login-password-input")).sendKeys(this.password);
		
		Thread.sleep(5000);
		
		driver.findElement(By.id("login-submit-button")).click();
		
	}
	
	public void searchJobs() throws InterruptedException {
		WebElement findJobsTab = driver.findElement(By.className("gnav-PageLink-text"));
//		WebElement findJobsTab = driver.findElement(By.xpath("(//a[@data-gnav-action='nav'])[0]"));
		WebElement findJobsBtn = driver.findElement(By.className("icl-WhatWhere-button"));
//		WebElement findJobsBtn =  driver.findElement(By.xpath("//div[@class='icl-WhatWhere-buttonWrapper']//button[@type='submit']"));

		// explicit wait -  wait for the "Find Jobs" link to load before clicking it
//		WebDriverWait wait = new WebDriverWait(driver,10);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='gnav-PageLink-text'])[0]")));
//		Thread.sleep(8000);
		
		findJobsTab.click();
		
//		Thread.sleep(8000);
		
        WebElement clearWhat = driver.findElement(By.id("text-input-what"));
        WebElement clearWhere = driver.findElement(By.id("text-input-where"));
        
        clearWhat.clear();
        clearWhat.sendKeys(this.what);
//        Thread.sleep(3000);
        clearWhere.clear();
//        clearWhere.sendKeys(this.where);
//        Thread.sleep(3000);
//        driver.findElement(By.xpath("//input[@value='text-input-where']")).clear();
        clearWhere.submit();
//        clearWhere.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
//        if (clearWhere.getAttribute("value") != "" ) {
//        	clearWhere.clear();
//        	
//        }
//        clearWhere.sendKeys(this.where);
        
//        Thread.sleep(10000);
        
        
//        driver.findElement(By.id("text-input-what")).sendKeys(this.what);
//        driver.findElement(By.id("text-input-where")).sendKeys(this.where);
        
//        Thread.sleep(5000);
        
//        findJobsBtn.click();
//        driver.quit();
        

	}
	
	public void JobScrape() throws InterruptedException {
		
		// Create a list of JobCards
		List<WebElement> JobsCard = driver.findElements(By.className("jobsearch-SerpJobCard"));
		
		// For each job card
		for (int i = 0; i < JobsCard.size(); i++) {
			
			// Find the links to each job
//			WebElement link = JobsCard.get(i).findElement(By.className("jobtitle"));

			if (this.application_type.toLowerCase() == "easily apply") {
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
	
	public void applyToJobs() {
		String parentHandle = driver.getWindowHandle(); // get the current window handle
	    System.out.println(parentHandle);               //Prints the parent window handle 
                           //Clicking on this window
	    for (String winHandle : driver.getWindowHandles()) { //Gets the new window handle
	        System.out.println(winHandle);
	        driver.switchTo().window(winHandle);        // switch focus of WebDriver to the next found window handle (that's your newly opened window)              
	    }
	//Now your driver works on the current new handle
	    
	    WebDriverWait wait = new WebDriverWait(driver, 15);
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("indeedApplyButtonContainer")))).click();
//	    WebElement apply_to_job = driver.findElement(By.className("jobsearch-IndeedApplyButton-contentWrapper"));
//	    apply_to_job.click();
	    
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("input-applicant.name"))));
	    driver.findElement(By.id("input-applicant.name")).sendKeys("Bruce Tieu");
	    
//	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("input-applicant.name"))));
//	   
//	    
//	    
//	    name_field.sendKeys("Bruce Tieu");
//	    phone_field.sendKeys("7202610380");
	    
//	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("ia-ControlledFilePicker-fakeControl")))).click();
	    
	    
	//Time to go back to parent window
	    driver.close();                                 // close newly opened window when done with it
	    driver.switchTo().window(parentHandle);         // switch back to the original window


	}
	
	public void quitBrowser() {
		driver.quit();
	}
	
	public static void main(String[] args) throws InterruptedException {
		IndeedBot IB = new IndeedBot("https://www.indeed.com/?from=gnav-util-homepage",
				"email", "password", "Software Developer", "remote", "easily apply");
		IB.navigateToUrl();
//		IB.login();
		IB.searchJobs();
		IB.JobScrape();
//		IB.quitBrowser();
	
	}

	
		
	
}
