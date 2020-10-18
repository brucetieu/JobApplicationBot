package com.btieu.JobApplicationBot;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;


public class GlassdoorBot extends Bot {
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

    public GlassdoorBot(JobApplicationData _jobAppData, JobApplicationData.ApplicationType _appType) {
        this._jobAppData = _jobAppData;
        this._appType = _appType;
    }

    /**
     * Navigate to the Glassdoor site.
     */
    public void navToGlassdoor() {
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

        humanTyping(searchKey, this._jobAppData.whatJob);

        // Clear the "Where" field and send in the location of the job
        getActions().sendKeys(Keys.TAB);
        getActions().sendKeys(Keys.TAB);
        getActions().sendKeys(Keys.DELETE);
        getActions().build().perform();

        humanTyping(searchLoc, this._jobAppData.locationOfJob);
        searchLoc.submit();
    }

    /**
     * This method aggregates all the job links and puts them in a container.
     * @throws InterruptedException
     */
    public void getJobLinks() throws InterruptedException {
        ArrayList<String> jobLinks = new ArrayList<String>();

        List<WebElement> aTags = tryToFindElements((By.className("react-job-listing")));

        for (int i = 0; i < aTags.size(); i++) {
            WebElement div = aTags.get(i).findElement(By.className("d-flex"));
            String href = div.findElement(By.className("jobLink")).getAttribute("href");
            jobLinks.add(href);
        }

        System.out.println("End");

    }


}
