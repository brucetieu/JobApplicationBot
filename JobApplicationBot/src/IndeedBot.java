
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class applies to jobs on Indeed.com.
 * 
 * @author Bruce Tieu
 */
public class IndeedBot {
    private JobApplicationData _jobAppData;
    private HumanBehavior _humanBehavior;
    private Helpers _helpers;
    private JobApplicationData.ApplicationType _appType;
    private static final String _CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String _CHROME_DRIVER_PATH = "/Applications/chromedriver";
    private static final int _MAX_WAIT_TIME = 10;
    private WebDriver _driver;
    private WebDriverWait _wait;
    private ChromeOptions _chromeOptions;
    private ArrayList<HashMap<String, String>> jobDescContainer = new ArrayList<HashMap<String, String>>();

    /**
     * This is a class constructor which initializes job application data, job
     * application type, a chrome driver, and sets the variables for the driver.
     * 
     * @param jobAppData    The object which holds job application data.
     * @param humanBehavior The class which has all the human movement methods.
     * @param helpers       The class which has all the helper methods.
     * @param appType       The enum type which is a set of application types.
     */
    public IndeedBot(JobApplicationData jobAppData, HumanBehavior humanBehavior, Helpers helpers,
            JobApplicationData.ApplicationType appType) {
        this._jobAppData = jobAppData;
        this._humanBehavior = humanBehavior;
        this._helpers = helpers;
        this._appType = appType;
        System.setProperty(IndeedBot._CHROME_DRIVER_PROPERTY, IndeedBot._CHROME_DRIVER_PATH);
        this._chromeOptions = new ChromeOptions();
        this._chromeOptions.addArguments("--disable-blink-features");
        this._chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        this._chromeOptions.addArguments("--user-data-dir=/Users/bruce/Library/Application Support/Google/Chrome");
        this._chromeOptions.addArguments("start-maximized");
        this._driver = new ChromeDriver(this._chromeOptions);
        this._driver.manage().timeouts().implicitlyWait(_MAX_WAIT_TIME, TimeUnit.SECONDS);
        this._wait = new WebDriverWait(this._driver, _MAX_WAIT_TIME);

    }

    /**
     * This method navigates to the job page.
     */
    public void navigateToUrl() {
        this._driver.get(this._jobAppData.url);
    }

    /**
     * This method logs in to the job site.
     * 
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early
     */
    public void login() throws InterruptedException {

        // Wait for element to appear before clicking on it.
        this._helpers.waitOnElementAndClick(this._driver, this._wait, By.className("gnav-LoggedOutAccountLink-text"));

        // Make sure the Email and Password fields are cleared out of any text.
        this._driver.findElement(By.id("login-email-input")).clear();
        this._driver.findElement(By.id("login-password-input")).clear();

        // Populate the fields with an email and a password
        WebElement email = this._driver.findElement(By.id("login-email-input"));
        WebElement password = this._driver.findElement(By.id("login-password-input"));
        this._humanBehavior.humanTyping(email, this._jobAppData.email);
        this._humanBehavior.humanTyping(password, this._jobAppData.password);

        this._helpers.waitOnElementAndClick(this._driver, this._wait, By.id("login-submit-button"));
    }

    /**
     * This method searches for jobs based on job position name and location.
     * 
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    public void searchJobs() throws InterruptedException {
        // Create an actions object.
        Actions action = new Actions(this._driver);

        // Click on the find jobs tab
        this._wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("gnav-PageLink-text"))).click();

        // Locate the "What" and "Where" input fields.
        WebElement clearWhat = this._wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-what")));
        WebElement clearWhere = this._wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-where")));

        clearWhat.clear();

        // Delay typing
        this._humanBehavior.humanTyping(clearWhat, this._jobAppData.whatJob);

        // Clear the "Where" field and send in the location of the job
        action.sendKeys(Keys.TAB);
        action.sendKeys(Keys.DELETE);
        action.build().perform();

        this._humanBehavior.humanTyping(clearWhere, this._jobAppData.locationOfJob);
        clearWhere.submit();
    }

    /**
     * This method looks for Indeed Jobs that are easy to apply to (for now).
     * 
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     * @throws IOException
     */
    public void findEasyApply() throws InterruptedException {

        // Create a list of type WebElement objects called JobsCard.
        List<WebElement> jobsCard = this._driver.findElements(By.className("jobsearch-SerpJobCard"));
        int currPageNum = 0;

        // Loop through each of the jobs present on the page.
        int i = 0;
        while (i < jobsCard.size()) {

            if (i == jobsCard.size() - 1 && currPageNum == this._jobAppData.pageNum)
                break;

            // Check if the appType that is passed in is an "Easily Apply" one.
            if (this._appType == JobApplicationData.ApplicationType.EASILY_APPLY) {

                // If there are one or more of these "Easily Apply" jobs, then open that job in
                // a new tab.
                if (jobsCard.get(i).findElements(By.className("iaLabel")).size() > 0) {
                    System.out.println("Opening Easily Apply Job in another tab...");

                    // Get the current window.
                    String parentWindow = this._driver.getWindowHandle();

                    // Get the job link.
                    String href = jobsCard.get(i).findElement(By.className("jobtitle")).getAttribute("href");
                    System.out.println(href);

                    this._helpers.switchWindows(this._driver, href);

                    /*
                     * Apply to each of those easy apply jobs until there are none on the first
                     * page, and pass in the parentWindow to keep track of the original job listing
                     * page as tabs are closed.
                     */
                    applyToJobs(parentWindow, href, this._appType);
                }
            }
            if (i == jobsCard.size() - 1) {
                // Go to the next page.
                i = -1;
                currPageNum += 1;
                String url = "https://www.indeed.com/jobs?q=" + this._jobAppData.whatJob + "&l="
                        + this._jobAppData.locationOfJob + "&start=" + currPageNum * 10;
                this._driver.get(url);
                jobsCard = this._wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("jobsearch-SerpJobCard")));
            }
            i++;
        }
        // Populate CSV file with Easy apply jobs which haven't been applied to.
        this._helpers.readJobsToCSV(jobDescContainer);
    }

    /**
     * This method applies to the "Easily Apply" jobs on Indeed.com.
     * 
     * @param currWindow The window which holds all listings of jobs on a single
     *                   page.
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    public void applyToJobs(String currWindow, String jobLink, JobApplicationData.ApplicationType appType)
            throws InterruptedException {

        Actions action = new Actions(this._driver);

        int numOfQuals = countQualifications();

        // Wait until the following elements to appear before clicking on it.
        this._helpers.waitOnElementAndClick(this._driver, this._wait, By.id("indeedApplyButtonContainer"));
        this._helpers.switchIframes(this._driver, this._wait,
                By.cssSelector("iframe[title='Job application form container']"));
        this._helpers.switchIframes(this._driver, this._wait, By.cssSelector("iframe[title='Job application form']"));

        boolean applied = hasJobBeenAppliedTo();
        if (applied) {

            // Some forms ask for a fullname while others ask for first name and last name.
            // So these try/catch/finally blocks are intended to resolve those different
            // cases.
            WebElement fullName = this._helpers.tryToFindElement(this._driver, this._wait,
                    By.id("input-applicant.name"));
            this._humanBehavior.humanTyping(fullName, this._jobAppData.fullname);
            WebElement firstName = this._helpers.tryToFindElement(this._driver, this._wait,
                    By.id("input-applicant.firstName"));
            this._humanBehavior.humanTyping(firstName, this._jobAppData.firstname);
            WebElement lastName = this._helpers.tryToFindElement(this._driver, this._wait,
                    By.id("input-applicant.lastName"));
            this._humanBehavior.humanTyping(lastName, this._jobAppData.lastname);

            WebElement email = this._helpers.tryToFindElement(this._driver, this._wait, By.id("input-applicant.email"));
            if (email.getAttribute("readonly") == null) {
                this._humanBehavior.humanTyping(email, this._jobAppData.email);
            } else {
                action.sendKeys(Keys.TAB);
                action.build().perform();
            }

            WebElement phoneNumber = this._helpers.tryToFindElement(this._driver, this._wait,
                    By.id("input-applicant.phoneNumber"));
            this._humanBehavior.humanTyping(phoneNumber, this._jobAppData.phone);

            WebElement uploadResume = this._helpers.tryToFindElement(this._driver, this._wait,
                    By.id("ia-CustomFilePicker-resume"));
            uploadResume.sendKeys(this._jobAppData.resumePath);

            this._helpers.waitOnElementAndClick(this._driver, this._wait, By.id("form-action-continue"));

            // Attempt to submit application after filling the initial user information.
            submitApplication(numOfQuals);

            action.moveByOffset(0, 0).click().build().perform();
            this._driver.switchTo().defaultContent();

            jobDescContainer.add(this._helpers.getJobInformation(this._driver, this._wait, jobLink, appType, applied));

            // Close that new window (the job that was opened).
            this._driver.close();

            // Switch back to the parent window (job listing window).
            this._driver.switchTo().window(currWindow);

        }

        // If the job has already been applied to, close the current window and switch
        // to the job listing page to continue searching for jobs.
        else {
            action.moveByOffset(0, 0).click().build().perform();
            this._driver.switchTo().defaultContent();
            jobDescContainer.add(this._helpers.getJobInformation(this._driver, this._wait, jobLink, appType, applied));
            this._driver.close();
            this._driver.switchTo().window(currWindow);
        }
        for (int i = 0; i < jobDescContainer.size(); i++) {
            System.out.println(jobDescContainer.get(i));
        }
    }

    /**
     * This method checks if a job has already been applied to. If it has, skip the
     * application and search for the next one.
     * 
     * @return True, if a job hasn't been applied to and false if it has.
     */
    public boolean hasJobBeenAppliedTo() {

        // Check if the form contains a message about the Application already being
        // applied to.
        if (this._driver.findElements(By.id("ia_success")).size() > 0) {
            // Close the popup
            WebElement closePopup = this._wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("close-popup")));
            closePopup.click();
            return false;
        } else
            return true;
    }

    /**
     * This method submits the application after filling out the initial form in the
     * beginning.
     * 
     * @param count The number of qualifications in the position
     */
    public void submitApplication(int count) {

        WebElement textArea = this._helpers.tryToFindElement(this._driver, this._wait, By.tagName("textarea"));
        if (textArea != null)
            textArea.sendKeys("test");
        else
            ;
        this._helpers.waitOnElementAndClick(this._driver, this._wait, By.id("form-action-continue"));
        this._helpers.waitOnElementAndClick(this._driver, this._wait,
                By.id("ia-InterventionActionButtons-buttonDesktop"));
        this._helpers.waitOnElementAndClick(this._driver, this._wait, By.id("form-action-submit"));
        /**
         * TODO: If count > 0, that means the job description contains information about
         * years of experience needed to be considered for the job.
         */
    }

    /**
     * This method scrapes the job description with qualifications that have
     * (Required) or (Preferred). If these key words are present, then almost always
     * does the form ask the user to fill in their info according to those
     * qualifications.
     * 
     * @return int The number of qualifications that will appear in the form.
     */
    public int countQualifications() {

        WebElement jobDescDiv = this._wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("jobDescriptionText")));
        List<WebElement> paragraphs = jobDescDiv.findElements(By.tagName("ul"));

        int count = 0;
        for (int i = 0; i < paragraphs.size(); i++) {
            if (paragraphs.get(i).getText().contains("(Required)")
                    || paragraphs.get(i).getText().contains("(Preferred)")) {
                count++;
                System.out.println(paragraphs.get(i).getText());
            }
        }
        return count;

    }

    /**
     * This method quits the browser.
     */
    public void quitBrowser() {
        this._driver.quit();
    }
}
