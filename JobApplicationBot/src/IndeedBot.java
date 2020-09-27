
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
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
    private JobApplicationData.ApplicationType _appType;
    private static final String _CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String _CHROME_DRIVER_PATH = "/Applications/chromedriver";
    private static final int _MAX_WAIT_TIME = 30;
    private WebDriver _driver;

    /**
     * This is a class constructor which initializes job application data, job
     * application type, a chrome driver, and sets the variables for the driver.
     * 
     * @param jobAppData The object which holds job application data.
     * @param appType    The enum type which is a set of application types.
     */
    public IndeedBot(JobApplicationData jobAppData, JobApplicationData.ApplicationType appType) {
        this._jobAppData = jobAppData;
        this._appType = appType;
        System.setProperty(IndeedBot._CHROME_DRIVER_PROPERTY, IndeedBot._CHROME_DRIVER_PATH);
        this._driver = new ChromeDriver();
        this._driver.manage().timeouts().implicitlyWait(_MAX_WAIT_TIME, TimeUnit.SECONDS);
    }

    /**
     * This method navigates to the job page.
     */
    public void navigateToUrl() {
        // Navigate to the url.
        this._driver.get(this._jobAppData.url);
    }

    /**
     * This method logs in to the job site.
     * 
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early
     */
    public void login() throws InterruptedException {

        // This waits up to 30 seconds before throwing a TimeoutException or if it finds
        // the element it will return it in 0 - 30 seconds.
        WebDriverWait wait = new WebDriverWait(this._driver, _MAX_WAIT_TIME);

        // Wait for 15 seconds until the Sign In tab appears before clicking.
        wait.until(ExpectedConditions
                .visibilityOf(this._driver.findElement(By.className("gnav-LoggedOutAccountLink-text")))).click();

        // Make sure the Email and Password fields are cleared out of any text.
        this._driver.findElement(By.id("login-email-input")).clear();
        this._driver.findElement(By.id("login-password-input")).clear();

        // Populate the fields with an email and a password
        this._driver.findElement(By.id("login-email-input")).sendKeys(this._jobAppData.email);
        this._driver.findElement(By.id("login-password-input")).sendKeys(this._jobAppData.password);

        // Wait until the following element appears before signing in.
        wait.until(ExpectedConditions.visibilityOf(this._driver.findElement(By.id("login-submit-button")))).click();
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

        WebDriverWait wait = new WebDriverWait(this._driver, _MAX_WAIT_TIME);

        // Click on the find jobs tab
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("gnav-PageLink-text"))).click();

        // Locate the "What" and "Where" input fields.
        WebElement clearWhat = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-what")));
        WebElement clearWhere = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("text-input-where")));

        clearWhat.clear();

        // Clear the "What" field and send in the job position specified by the user.
        clearWhat.sendKeys(this._jobAppData.whatJob);

        // Clear the "Where" field and send in the location of the job specified by the
        // user.
        action.sendKeys(Keys.TAB);
        action.sendKeys(Keys.DELETE);
        action.build().perform();

        // Also send in the location of the job in the input field.
        clearWhere.sendKeys(this._jobAppData.locationOfJob);

        // Search for jobs based on the inputs.
        clearWhere.submit();
    }

    /**
     * This method looks for Indeed Jobs that are easy to apply to (for now).
     * 
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    public void findEasyApply() throws InterruptedException {

        // Create a list of type WebElement objects called JobsCard.
        List<WebElement> JobsCard = this._driver.findElements(By.className("jobsearch-SerpJobCard"));

        // Loop through each of the jobs present on the page.
        for (int i = 0; i < JobsCard.size(); i++) {

            // Check if the appType that is passed in is an "Easily Apply" one.
            if (this._appType == JobApplicationData.ApplicationType.EASILY_APPLY) {

                // If there are one or more of these "Easily Apply" jobs, then open that job in
                // a new tab.
                if (JobsCard.get(i).findElements(By.className("iaLabel")).size() > 0) {
                    System.out.println("Opening Easily Apply Job in another tab...");

                    // Get the current window.
                    String parentWindow = this._driver.getWindowHandle();

                    // Get the job link.
                    String href = JobsCard.get(i).findElement(By.className("jobtitle")).getAttribute("href");
                    System.out.println(href);

                    // Use JavaScript to open a new tab instead of "control + t".
                    ((JavascriptExecutor) this._driver).executeScript("window.open()");
                    // Store the available windows in a list.
                    ArrayList<String> tabs = new ArrayList<String>(this._driver.getWindowHandles());
                    // Switch to the newly opened tab.
                    this._driver.switchTo().window(tabs.get(1));
                    // Navigate to the job link in that newly opened tab.
                    this._driver.get(href);

                    /*
                     * Apply to each of those easy apply jobs until there are none on the first
                     * page, and pass in the parentWindow to keep track of the original job listing
                     * page as tabs are closed.
                     */
                    applyToJobs(parentWindow);
                }
            }
        }
    }

    /**
     * This method applies to the "Easily Apply" jobs on Indeed.com.
     * 
     * @param currWindow The window which holds all listings of jobs on a single
     *                   page.
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    public void applyToJobs(String currWindow) throws InterruptedException {
        Actions action = new Actions(this._driver);
        // This waits up to 30 seconds before throwing a TimeoutException or if it finds
        // the element it will return it in 0 - 30 seconds.
        WebDriverWait wait = new WebDriverWait(this._driver, _MAX_WAIT_TIME);

        int numOfQuals = jobScrape();

        // Wait until the following element appears before clicking on it.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("indeedApplyButtonContainer"))).click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("iframe[title='Job application form container']")));
        // Switch to parent iframe element.
        this._driver.switchTo()
                .frame(this._driver.findElement(By.cssSelector("iframe[title='Job application form container']")));
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe[title='Job application form']")));
        // Switch to child iframe element.
        this._driver.switchTo().frame(this._driver.findElement(By.cssSelector("iframe[title='Job application form']")));

        // Check if the job has been applied to and filter out any jobs which have forms
        // asking for years of experience in a language or field.
        if (checkIfJobHasBeenAppliedTo() & numOfQuals == 0) {

            // Some forms ask for a fullname while others ask for first name and last name.
            // So these try/catch/finally blocks are intended to resolve those different
            // cases.
            try {

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-applicant.name")));
                this._driver.findElement(By.id("input-applicant.name")).sendKeys(this._jobAppData.fullname);
            } catch (Exception e) {
                // If there's no full name input field, then check if there's a first name and
                // last name field.
                System.out.println("Full name field does not exist");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-applicant.firstName")))
                        .sendKeys(this._jobAppData.firstname);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-applicant.lastName")))
                        .sendKeys(this._jobAppData.lastname);

                // There's always going to be an email and phone number field, so fill those in
                // regardless of an exception being thrown.
            } finally {
                WebElement email = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.id("input-applicant.email")));

                // Sometimes the email field is readonly, meaning its already been filled in.
                // This will skip over it and fill the phone number.
                if (this._driver.findElement(By.id("input-applicant.email")).getAttribute("readonly") == null) {
                    email.sendKeys(this._jobAppData.email);
                } else {
                    action.sendKeys(Keys.TAB);
                    action.build().perform();

                }
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-applicant.phoneNumber")))
                        .sendKeys(this._jobAppData.phone);

                // Upload the resume and click on the continue button.
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ia-CustomFilePicker-resume")))
                        .sendKeys(this._jobAppData.resumePath);

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form-action-continue"))).click();
            }

            // Attempt to submit application after filling the initial user information.
            submitApplication(numOfQuals);
            /**
             * TODO: Figure out how to deal with differing application questions after the
             * "continue" button is clicked, which varies with each "Easily apply"
             * application.
             */

            // Close that new window (the job that was opened).
            this._driver.close();

            // Switch back to the parent window (job listing window).
            this._driver.switchTo().window(currWindow);

        }

        // If the job has already been applied to, close the current window and switch
        // to the job listing page to continue searching for jobs.
        else {
            this._driver.close();
            this._driver.switchTo().window(currWindow);
        }

    }

    /**
     * This method checks if a job has already been applied to. If it has, skip the
     * application and search for the next one
     * 
     * @return True, if a job hasn't been applied to and false if it has.
     */
    public boolean checkIfJobHasBeenAppliedTo() {
        // This waits up to 30 seconds before throwing a TimeoutException or if it finds
        // the element it will return it in 0 - 30 seconds.
        WebDriverWait wait = new WebDriverWait(this._driver, _MAX_WAIT_TIME);

        // Check if the form contains a message about the Application already being
        // applied to.
        if (this._driver.findElements(By.id("ia_success")).size() > 0) {
            // Close the popup
            WebElement closePopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("close-popup")));
            closePopup.click();
            return false;
        } else
            return true;
    }

    /**
     * This method submits the application after filling out the initial form in the
     * beginning.
     * 
     * @param count
     */
    public void submitApplication(int count) {

        WebDriverWait wait = new WebDriverWait(this._driver, _MAX_WAIT_TIME);

        // Case where the application doesn't ask to fill years of qualifications.
        if (count == 0) {
            // Forms with "Enter the times you're available for a call"
            noQualsTimeAvail();

        }
        /**
         * TODO: If count > 0, that means the job description contains information about
         * years of experience needed to be considered for the job. There are so many
         * different scenarios other than filling out the years such as work
         * authorization, gender, sponsorship info, etc, that I still need to think
         * about.
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
    public int jobScrape() {
        WebDriverWait wait = new WebDriverWait(this._driver, _MAX_WAIT_TIME);

        WebElement jobDescDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("jobDescriptionText")));
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
     * This method is intended to decrease the probability that a reCAPTCHA will
     * appear when the bot attempts to submit the application.
     */
    public void mimicHumanBehavior() {
        /**
         * TODO: Add non-linear mouse movements and random waits to mimic human
         * behavior.
         */
    }

    /**
     * The method fills out the form for applications which only have one question
     * which asks the user to enter their available times for a callback.
     */
    public void noQualsTimeAvail() {
        WebDriverWait wait = new WebDriverWait(this._driver, _MAX_WAIT_TIME);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("textarea"))).sendKeys("Monday");
        } catch (Exception e) {
            System.out.println("Textarea box not found");
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form-action-continue"))).click();
        } catch (Exception e) {
            System.out.println("Continue button not found");
        }
        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("ia-InterventionActionButtons-buttonDesktop")))
                    .click();
        } catch (Exception e) {
            System.out.println("Continue Applying button not found");
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form-action-submit"))).click();
        } catch (Exception e) {
            System.out.println("Apply button not found");
        }
    }

    /**
     * This method quits the browser.
     */
    public void quitBrowser() {
        this._driver.quit();
    }
    
    // Test the bot
    public static void main(String[] args) throws InterruptedException {
        // Create a new object which is a job application data.
        JobApplicationData jobAppData = new JobApplicationData();
        jobAppData.firstname = "John";
        jobAppData.lastname = "Doe";
        jobAppData.fullname = "John Doe";
        jobAppData.email = "John Doe";
        jobAppData.phone = "5555555555";
        jobAppData.resumePath = "/Users/bruce/Documents/WithObj2_Bruce_Tieu_2020_Resume.pdf";
        jobAppData.url = "https://www.indeed.com/?from=gnav-util-homepage";
        jobAppData.password = "password";
        jobAppData.whatJob = "Software engineer";
        jobAppData.locationOfJob = "remote";

        // Create an IndeedBot to apply for jobs.
        IndeedBot IB = new IndeedBot(jobAppData, JobApplicationData.ApplicationType.EASILY_APPLY);
        IB.navigateToUrl();
        IB.login();
        IB.searchJobs();
        IB.findEasyApply();
    }

}
