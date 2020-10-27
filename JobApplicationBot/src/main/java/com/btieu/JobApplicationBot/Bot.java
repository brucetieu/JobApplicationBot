package com.btieu.JobApplicationBot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class holds common bot actions like waiting and navigating.
 * 
 * @author bruce
 *
 */
public class Bot {

    private static final String _CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String _CHROME_DRIVER_PATH = "/Applications/chromedriver";
    private static final int _MAX_WAIT_TIME = 10;
    private WebDriver _driver;
    private WebDriverWait _wait;
    private ChromeOptions _chromeOptions;
    private Actions _actions;

    /**
     * This the default constructor which initializes all the required drivers and
     * chrome options.
     */
    public Bot() {
        System.setProperty(_CHROME_DRIVER_PROPERTY, _CHROME_DRIVER_PATH);
        _chromeOptions = new ChromeOptions();
        _chromeOptions.addArguments("--disable-blink-features");
        _chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        // FIXME: Not applicable on every machine.
        _chromeOptions.addArguments("--user-data-dir=/Users/bruce/Library/Application Support/Google/Chrome");
        _chromeOptions.addArguments("start-maximized");
        _driver = new ChromeDriver(_chromeOptions);
        _driver.manage().timeouts().implicitlyWait(_MAX_WAIT_TIME, TimeUnit.SECONDS);
        _driver.manage().window().setPosition(new Point(-2000, 0));
        _wait = new WebDriverWait(_driver, _MAX_WAIT_TIME);
        _actions = new Actions(_driver);
    }

    /**
     * This is a getter method.
     * 
     * @return The driver object.
     */
    public WebDriver getDriver() {
        return _driver;
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

    /**
     * This method tries to find a single element on a page.
     * 
     * @param by The specific element to be located.
     * @return The element if found.
     */
    public WebElement tryToFindElement(By by) {
        WebElement element = null;
        try {
            element = _wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            System.out.println("Could not find element: " + by);
        }
        return element;
    }

    /**
     * This method tries to find a list of elements on a page.
     * 
     * @param by The elements to be located
     * @return The list of elements if found.
     */
    public List<WebElement> tryToFindElements(By by) {
        List<WebElement> element = null;
        try {
            element = _wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        } catch (Exception e) {
            System.out.println("Could not find element: " + by);
        }
        return element;
    }

    /**
     * This method navigates to the job page.
     * 
     * @param jobUrl A string which is the job platform link.
     */
    public void navigateToJobPage(String jobUrl) {
        _driver.get(jobUrl);
    }

    /**
     * This method waits for a specific element to appear before clicking.
     * 
     * @param by The specific element.
     */
    public void waitOnElementAndClick(By by) {
        try {
            _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(by))).click();
        } catch (Exception e) {
            System.out.println("Could not locate element to click: " + by);
        }
    }

    /**
     * This method switches tabs while applying.
     * 
     * @param driver The webdriver object.
     * @param link   The job link which is a string.
     */
    public void navigateToLinkInNewTab(String link) {
        // Use JavaScript to open a new tab instead of "control + t".
        ((JavascriptExecutor) _driver).executeScript("window.open()");
        // Store the available windows in a list.
        ArrayList<String> tabs = new ArrayList<String>(_driver.getWindowHandles());
        // Switch to the newly opened tab.
        _driver.switchTo().window(tabs.get(tabs.size() - 1));
        // Navigate to the job link in that newly opened tab.
        _driver.get(link);
    }

    /**
     * This method switches iframes.
     * 
     * @param by This is the specific iframe element to switch to.
     */
    public void switchIframes(By by) {
        _wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        _driver.switchTo().frame(_driver.findElement(by));

    }

    /**
     * This is the method which mimics human typing.
     * 
     * @param element The field which is typed into.
     * @param jobData The job application data that is supplied
     * @throws InterruptedException
     */
    public void humanTyping(WebElement element, String jobData) throws InterruptedException {
        if (element != null) {
            for (int i = 0; i < jobData.length(); i++) {
                TimeUnit.SECONDS.sleep((long) (Math.random() * (3 - 1) * +1));
                char c = jobData.charAt(i);
                String s = new StringBuilder().append(c).toString();
                element.sendKeys(s);
            }
        } else {
        }

    }
    

}