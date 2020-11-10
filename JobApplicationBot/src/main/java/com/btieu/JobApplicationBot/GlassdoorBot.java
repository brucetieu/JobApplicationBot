package com.btieu.JobApplicationBot;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Apply for jobs on Glassdoor.com.
 * 
 * @author bruce
 *
 */
public class GlassdoorBot extends Bot {
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

    public GlassdoorBot(JobApplicationData _jobAppData, JobApplicationData.ApplicationType _appType) {
        this._jobAppData = _jobAppData;
        this._appType = _appType;
    }

    /**
     * Navigate to the Indeed site.
     */
    public void navigateToJobPage() {
        getWebDriver().get(this._jobAppData.platformUrl);
    }

    /**
     * This method logs in to the job site.
     * 
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    public void login() throws InterruptedException {

        // Wait for element to appear before clicking on it.
        waitOnElementAndClick(By.className("locked-home-sign-in"));

        WebElement userEmail = tryToFindElement(By.id("userEmail"));
        WebElement userPassword = tryToFindElement(By.id("userPassword"));

        userEmail.clear();
        userPassword.clear();

        userEmail.sendKeys(this._jobAppData.email);
        userPassword.sendKeys(this._jobAppData.password);

        userPassword.submit();

    }

    /**
     * This method searches for jobs based on job position name and location.
     * 
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    public void searchJobs() throws InterruptedException {
        WebElement searchKey = tryToFindElement(By.id("sc.keyword"));
        WebElement searchLoc = tryToFindElement(By.id("sc.location"));
        searchKey.clear();
        searchLoc.clear();

        typeLikeAHuman(searchKey, this._jobAppData.whatJob);

        // Clear the "Where" field and send in the location of the job
        getActions().sendKeys(Keys.TAB);
        getActions().sendKeys(Keys.TAB);
        getActions().sendKeys(Keys.DELETE);
        getActions().build().perform();

        typeLikeAHuman(searchLoc, this._jobAppData.locationOfJob);
        searchLoc.submit();
    }
    

}
