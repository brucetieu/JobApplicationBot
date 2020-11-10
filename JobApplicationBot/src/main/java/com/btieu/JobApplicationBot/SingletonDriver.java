package com.btieu.JobApplicationBot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class holds common bot actions like waiting and navigating.
 * 
 * @author bruce
 */
public class SingletonDriver {

    private static SingletonDriver singleInstance = null;
    
    private static final String _CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String _CHROME_DRIVER_PATH = "/Applications/chromedriver";
    public static final int _MAX_WAIT_TIME = 10;
    
    private WebDriver _webDriver;
    private WebDriverWait _wait;
    private ChromeOptions _chromeOptions;
    private Actions _actions;
//    private JavascriptExecutor _js;

    /**
     * This the default constructor which initializes all the required drivers and
     * chrome options.
     */
    private SingletonDriver() {
        System.setProperty(_CHROME_DRIVER_PROPERTY, _CHROME_DRIVER_PATH);
        _chromeOptions = new ChromeOptions();
        _chromeOptions.addArguments("--disable-blink-features");
        _chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
//        _chromeOptions.addArguments("--headless");
        // FIXME: Not applicable on every machine.
//        _chromeOptions.addArguments("--user-data-dir=/Users/bruce/Library/Application Support/Google/Chrome");
        _chromeOptions.addArguments("start-maximized");
        _webDriver = new ChromeDriver(_chromeOptions);
        _webDriver.manage().timeouts().implicitlyWait(_MAX_WAIT_TIME, TimeUnit.SECONDS);
        _wait = new WebDriverWait(_webDriver, _MAX_WAIT_TIME);
        _actions = new Actions(_webDriver);
//        _js = (JavascriptExecutor) _webDriver;
    }

    public static SingletonDriver getInstance() {
        if (singleInstance == null) {
            singleInstance = new SingletonDriver();
        }
        return singleInstance;
    }
    // Abstract methods
//    abstract public void navigateToJobPage();
//    abstract public void login() throws InterruptedException;
//    abstract public void searchJobs() throws InterruptedException;
//    abstract public JobPostingData getJobInformation(String jobLink, JobApplicationData.ApplicationType appType,
//            boolean isApplied) throws IOException;
//    abstract public void saveJob(String jobLink, JobApplicationData.ApplicationType appType) throws InterruptedException, IOException;

    
    /**
     * This is a getter method.
     * 
     * @return The driver object.
     */
    public WebDriver getWebDriver() {
        return _webDriver;
    }

    /**
     * This is a getter method.
     * 
     * @return The wait object.
     */
    public WebDriverWait getWait() {
        return _wait;
    }

    /**
     * This is a getter method.
     * 
     * @return The actions object.
     */
    public Actions getActions() {
        return _actions;
    }
    
//    public JavascriptExecutor useJS() {
//        return _js;
//    }
}