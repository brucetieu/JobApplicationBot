package com.btieu.JobApplicationBot;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;

/**
 * This class applies to jobs on Indeed.com.
 * 
 * @author Bruce Tieu
 */
public class IndeedBot extends Bot {
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

    /**
     * This is a class constructor which initializes job application data, job
     * application type, a chrome driver, and sets the variables for the driver.
     * 
     * @param jobAppData  The object which holds job application data.
     * @param jobPostData The object which holds job posting data.
     * @param appType     The enum type which is a set of application types.
     */
    public IndeedBot(JobApplicationData _jobAppData, JobApplicationData.ApplicationType _appType) {
        this._jobAppData = _jobAppData;
        this._appType = _appType;
    }

    /**
     * Navigate to the Indeed site.
     */
    public void navToIndeed() {
        navigateToJobPage(this._jobAppData.platformUrl);
    }

    /**
     * This method logs in to the job site.
     * 
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    public void login() throws InterruptedException {

        // Wait for element to appear before clicking on it.
        waitOnElementAndClick(By.className("gnav-LoggedOutAccountLink-text"));

        // Make sure the Email and Password fields are cleared out of any text.
        getDriver().findElement(By.id("login-email-input")).clear();
        getDriver().findElement(By.id("login-password-input")).clear();

        // Populate the fields with an email and a password
        WebElement email = getDriver().findElement(By.id("login-email-input"));
        WebElement password = getDriver().findElement(By.id("login-password-input"));
        humanTyping(email, this._jobAppData.email);
        humanTyping(password, this._jobAppData.password);

        waitOnElementAndClick(By.id("login-submit-button"));
    }

    /**
     * This method searches for jobs based on job position name and location.
     * 
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    public void searchJobs() throws InterruptedException {

        // Click on the find jobs tab
        waitOnElementAndClick(By.className("gnav-PageLink-text"));

        // Locate the "What" and "Where" input fields.
        WebElement clearWhat = tryToFindElement(By.id("text-input-what"));
        WebElement clearWhere = tryToFindElement(By.id("text-input-where"));

        clearWhat.clear();

        // Delay typing
        humanTyping(clearWhat, this._jobAppData.whatJob);

        // Clear the "Where" field and send in the location of the job
        getActions().sendKeys(Keys.TAB);
        getActions().sendKeys(Keys.DELETE);
        getActions().build().perform();

        humanTyping(clearWhere, this._jobAppData.locationOfJob);
        clearWhere.submit();
    }

    /**
     * This method looks for Indeed Jobs that are easy to apply to (for now).
     * 
     * @throws Exception This checks for errors.
     * 
     */
    public void findEasyApply() throws Exception {

        // Create a list of type WebElement objects called JobsCard.
        List<WebElement> jobsCard = getDriver().findElements(By.className("jobsearch-SerpJobCard"));
        int currPageNum = 0;

        // Loop through each of the job divs present on the page.
        int i = 0;
        while (i < jobsCard.size()) {

            // Check if the appType that is passed in is an "Easily Apply" one.
            if (this._appType == JobApplicationData.ApplicationType.EASILY_APPLY) {

                // If there are one or more of these "Easily Apply" jobs, then open that job in
                // a new tab.
                if (jobsCard.get(i).findElements(By.className("iaLabel")).size() > 0) {
                    System.out.println("Opening Easily Apply Job in another tab...");

                    // Get the current window.
                    String parentWindow = getDriver().getWindowHandle();

                    // Get the job link.
                    String href = jobsCard.get(i).findElement(By.className("jobtitle")).getAttribute("href");
                    System.out.println(href);

                    // Open that job in a new tab.
                    navigateToLinkInNewTab(href);

                    /*
                     * Save each easy apply job until there are none on the first page, and pass in
                     * the parentWindow to keep track of the original job listing page as tabs are
                     * closed.
                     */
                    saveJob(parentWindow, href, this._appType);
                }

                // TODO: If we're at the last card, and the current page is the same as what
                // user specified, then stop.
            }
            // Go to the next page to continue saving jobs.
            if (i == jobsCard.size() - 1) {
                i = -1;
                currPageNum += 1;
                String nextPageUrl = "https://www.indeed.com/jobs?q=" + this._jobAppData.whatJob + "&l="
                        + this._jobAppData.locationOfJob + "&start=" + currPageNum * 10;
                getDriver().get(nextPageUrl);
                jobsCard = tryToFindElements(By.className("jobsearch-SerpJobCard"));
            }
            i++;
        }

        // TODO: Output saved jobs to a csv.
    }

    /**
     * This method saves "Easily Apply" jobs on Indeed.com.
     * 
     * @param currWindow The window which holds all listings of jobs on a single
     *                   page.
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    public void saveJob(String currWindow, String jobLink, JobApplicationData.ApplicationType appType)
            throws InterruptedException {

        // Check if job has been applied to.
        boolean applied = hasJobBeenAppliedTo();
        if (applied) {

            // TODO: Save the job to a container.

            // Close that new window (the job that was opened).
            getDriver().close();

            // Switch back to the parent window (job listing window).
            getDriver().switchTo().window(currWindow);

        }

        // If the job has already been applied to, close the current window and switch
        // to the job listing page to continue searching for jobs.
        else {
            // TODO: Save job to container.

            getDriver().close();
            getDriver().switchTo().window(currWindow);
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
        if (getDriver().findElements(By.id("ia_success")).size() > 0) {
            // Close the popup
            WebElement closePopup = tryToFindElement(By.id("close-popup"));
            closePopup.click();
            return false;
        } else
            return true;
    }

    /**
     * This method quits the browser.
     */
    public void quitBrowser() {
        getDriver().quit();
    }
}
