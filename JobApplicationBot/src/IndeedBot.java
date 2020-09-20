import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class IndeedBot {
	// Create an object of WebDriver
	WebDriver driver;
	
    // Member variables
	String username, password, what, where, url;
	
	// No parameter constructor
	IndeedBot() {}
	
	// Constructor with parameters
	public IndeedBot(String url, String username, String password, String what, String where) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.what = what;
		this.where = where;
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
		WebElement findJobsBtn = driver.findElement(By.className("icl-WhatWhere-buttonWrapper"));
	
		// explicit wait -  wait for the "Find Jobs" link to load before clicking it
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("gnav-PageLink-text")));

		
        driver.findElement(By.id("text-input-what")).clear();
        driver.findElement(By.id("text-input-where")).clear();
        
        
        Thread.sleep(10000);

        driver.findElement(By.id("text-input-what")).sendKeys(this.what);
        driver.findElement(By.id("text-input-where")).sendKeys(this.where);
        
        Thread.sleep(5000);
        
        findJobsBtn.click();
        
        
	}
	
		
	
}
