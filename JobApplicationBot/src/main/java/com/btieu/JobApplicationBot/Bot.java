package com.btieu.JobApplicationBot;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class holds common bot actions like waiting and navigating.
 * 
 * @author Bruce Tieu
 */
public class Bot {

    private SingletonDriver _driver;

    /**
     * This the default constructor which only initializes the Singleton class.
     */
    public Bot() {
        // Every time we want to use a method from this class, we only want to open the same instance
        // (don't want to open multiple browsers). 
        _driver = SingletonDriver.getInstance();
    }

    /**
     * This is a getter method.
     * 
     * @return The driver object.
     */
    public WebDriver getWebDriver() {
        return _driver.getWebDriver();
    }

    /**
     * This is a getter method.
     * 
     * @return The wait object.
     */
    public WebDriverWait getWait() {
        return _driver.getWait();
    }

    /**
     * This is a getter method.
     * 
     * @return The actions object.
     */
    public Actions getActions() {
        return _driver.getActions();
    }

    /**
     * Quit the browser.
     */
    public void quitBrowser() {
        _driver.getWebDriver().quit();
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
            element = getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
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
            element = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        } catch (Exception e) {
            System.out.println("Could not find element: " + by);
        }
        return element;
    }
    
    /**
     * Try to select an option from a dropdown.
     * 
     * @param by        The element to be located.
     * @param selection The text to be selected.
     * @return The selected option, if found.
     */
    public Select tryToSelectFromDpn(By by, String selection) {
        Select dropdown = null;
        try {
            dropdown = new Select(tryToFindElement(by));
            dropdown.selectByVisibleText(selection);
        } catch (Exception e) {
            System.out.println("Could not find element to select: " + by);
        }
        return dropdown;
    }
    
    /**
     * Try looking for an element and send text to it.
     * 
     * @param by  The element to be found.
     * @param key The text to be sent.
     * @return The element, if found.
     */
    public WebElement tryToFindElementAndSendKeys(By by, String key) {
        WebElement element = null;
        try {
            element = _driver.getWait().until(ExpectedConditions.visibilityOf(_driver.getWebDriver().findElement(by)));
            typeLikeAHuman(element, key);
        } catch (Exception e) {
            System.out.println("Could not send keys to: " + by);
        }
        return element;
    }

    /**
     * This method waits for a specific element to appear before clicking.
     * 
     * @param by The specific element.
     */
    public void waitOnElementAndClick(By by) {
        try {
            getWait().until(ExpectedConditions.visibilityOf(getWebDriver().findElement(by))).click();
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
        ((JavascriptExecutor) _driver.getWebDriver()).executeScript("window.open()");
        // Store the available windows in a list.
        ArrayList<String> tabs = new ArrayList<String>(getWebDriver().getWindowHandles());
        // Switch to the newly opened tab.
        getWebDriver().switchTo().window(tabs.get(tabs.size() - 1));
        // Navigate to the job link in that newly opened tab.
        getWebDriver().get(link);
    }

    /**
     * This method switches iframes.
     * 
     * @param by This is the specific iframe element to switch to.
     */
    public void switchIframes(By by) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        getWebDriver().switchTo().frame(getWebDriver().findElement(by));

    }

    /**
     * This is the method which mimics human typing.
     * 
     * @param element The field which is typed into.
     * @param jobData The job application data that is supplied
     * @throws InterruptedException
     */
    public void typeLikeAHuman(WebElement element, String jobData) throws InterruptedException {
        if (element != null) {
            for (int i = 0; i < jobData.length(); i++) {
                TimeUnit.SECONDS.sleep((long) (Math.random() * (3 - 1) * +1));
                char c = jobData.charAt(i);
                String s = new StringBuilder().append(c).toString();
                element.sendKeys(s);
            }
        }

    }
    
    /**
     * Match the job description text to the resume.
     * 
     * @return The cosine similarity number.
     * @throws IOException Catch any file errors.
     */
    public double jobMatchScore(By by) throws IOException {

        String jobDescriptionString = tryToFindElement(by).getText();
        TextDocument jobDescriptionText = new TextDocument(jobDescriptionString);
        
        // JobApplicationData.resumePath is the resume uploaded by the user. 
        TextDocument resumeText = new TextDocument(new File(JobApplicationData.resumePath));
        return CosineSimilarity.cosineSimilarity(jobDescriptionText, resumeText);
    }
    
    /**
     * Assemble a request and get the url of it.
     * 
     * @param href The url.
     * @return The request url.
     */
    public String getRequestURL(String href) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(href);
            connection = (HttpURLConnection) url.openConnection();
            connection.getContent();
        } catch (IOException e) { 
            e.getMessage(); 
        }
        return connection.getURL().toString();
    }
    
    /**
     * Get the text of a parent node but not the text of any of the child nodes.
     * 
     * @param element The parent element.
     * @return The text.
     */
    public String getTextExcludingChildren(WebElement element) {
        return (String) ((JavascriptExecutor) _driver.getWebDriver()).executeScript(
                "let parent = arguments[0];"
                + "let child = parent.firstChild;" 
                + "let text = '';" 
                + "while(child) {"
                + "    if (child.nodeType === Node.TEXT_NODE) {" 
                + "        text += child.textContent;" + "    }"
                + "    child = child.nextSibling;" 
                + "}" 
                + "return text;", element);
    }
    
    /**
     * Check if an element exists.
     * @param by The element.
     * @return True, if it exists and false otherwise.
     */
    public boolean elementExists(By by) {
        try {
            getWebDriver().findElement(by);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
    
    public boolean isClicked(By by) {
        try {
            waitOnElementAndClick(by);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

}
