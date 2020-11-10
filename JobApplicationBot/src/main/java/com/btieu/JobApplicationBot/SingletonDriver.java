package com.btieu.JobApplicationBot;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;


/**
 * Singleton class - allow only one instance of this class to be created at a time.
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

    /**
     * This the default constructor which initializes all the required drivers and
     * chrome options.
     */
    private SingletonDriver() {
        System.setProperty(_CHROME_DRIVER_PROPERTY, _CHROME_DRIVER_PATH);
        _chromeOptions = new ChromeOptions();
        _chromeOptions.addArguments("--disable-blink-features");
        _chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        _chromeOptions.addArguments("--headless"); // Run browser in the background.
        _chromeOptions.addArguments("start-maximized");
        _webDriver = new ChromeDriver(_chromeOptions);
        _webDriver.manage().timeouts().implicitlyWait(_MAX_WAIT_TIME, TimeUnit.SECONDS);
        _wait = new WebDriverWait(_webDriver, _MAX_WAIT_TIME);
        _actions = new Actions(_webDriver);

    }

    /**
     * Ensure only one instance is created.
     * @return The instance.
     */
    public static SingletonDriver getInstance() {
        if (singleInstance == null) {
            singleInstance = new SingletonDriver();
        }
        return singleInstance;
    }
    
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
    
}