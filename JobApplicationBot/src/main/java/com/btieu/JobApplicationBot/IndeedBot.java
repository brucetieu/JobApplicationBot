package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * An IndeedBot is a type of Bot with different functions for navigating an Indeed site.
 * 
 * @author Bruce Tieu
 */
public class IndeedBot extends Bot {

    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private String _parentWindow;

    /**
     * This is a class constructor which initializes job application data, job
     * application type.
     * 
     * @param jobAppData The object which holds job application data.
     * @param appType    The enum type which is a set of application types.
     */
    public IndeedBot(JobApplicationData _jobAppData, JobApplicationData.ApplicationType _appType) {
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
     * This method searches for jobs based on job position name and location.
     * 
     * @throws InterruptedException Catch errors if element is not found.
     */
    public void searchJobs() {

        // Click on the find jobs tab
        waitOnElementAndClick(By.className("gnav-PageLink-text"));

        // Locate the "What" and "Where" input fields.
        tryToFindElement(By.id("text-input-what")).clear();
        tryToFindElementAndSendKeys(By.id("text-input-what"), this._jobAppData.whatJob);

        // Clear the "Where" field and send in the location of the job.
        getActions().sendKeys(Keys.TAB);
        getActions().sendKeys(Keys.DELETE);
        getActions().build().perform();

        tryToFindElementAndSendKeys(By.id("text-input-where"),this._jobAppData.locationOfJob).submit();

    }


    /**
     * Click on the easy apply button on indeed.
     */
    public void clickOnApplyButton() {
        // Wait until the following elements to appear before clicking on it.
        waitOnElementAndClick(By.id("indeedApplyButtonContainer"));
        switchIframes(By.cssSelector("iframe[title='Job application form container']"));
        switchIframes(By.cssSelector("iframe[title='Job application form']"));
    }

    /**
     * Save each easy apply job to a container.
     * 
     * @param jobLink The application page.
     * @param appType The type of application it was.
     */
    public void saveEZApplyJob(String jobLink, JobApplicationData.ApplicationType appType) {

        // Check if job has been applied to.
        boolean isApplied = _hasJobBeenAppliedTo();
     
        try {
            boolean containerHasJob = JobPostingData.jobPostingContainer.contains(_getJobInformation(jobLink, appType, isApplied));
            
            if (!isApplied) {
                
                if (!containerHasJob) {
                    JobPostingData.jobPostingContainer.add(_getJobInformation(jobLink, appType, isApplied)); // Save job.
                    getWebDriver().close(); // Close that new window (the job that was opened).
                    getWebDriver().switchTo().window(_parentWindow); // Switch back to job lists window
                }
            }
            // Continue searching for jobs if already applied to.
            else {
                getWebDriver().close();
                getWebDriver().switchTo().window(_parentWindow);
            }
        } catch (Exception e) {}
    }

    /**
     * Save any type of job to a container.
     * 
     * @param jobLink The application page.
     * @param appType The type of application it was.
     */
    public void saveJob(String jobLink, JobApplicationData.ApplicationType appType) {

        try {
            boolean containerHasJob = JobPostingData.jobPostingContainer.contains(_getJobInformation(jobLink, appType, false));
            // Add unique JobPostings to container.
            if (!containerHasJob) {
                JobPostingData.jobPostingContainer.add(_getJobInformation(jobLink, appType, false)); // Save job.
                getWebDriver().close(); // Close that new window (the job that was opened).
                getWebDriver().switchTo().window(_parentWindow); // Switch back to job listing window.
            }
        } catch (Exception e) {}
        
    }

    /**
     * Get the actual link of the job.
     * 
     * @param index The index it's at in the list of job cards.
     * @return The link of the job.
     */
    public String getJobViewLink(int index, List<WebElement> jobList) {
        _parentWindow = getWebDriver().getWindowHandle(); // Get the current window.
        String href = jobList.get(index).findElement(By.className("jobtitle")).getAttribute("href"); // Get job link.
        href = href.replace("rc/clk", "viewjob");
        navigateToLinkInNewTab(href); // Open that job in a new tab.
        return href;
    }

    /**
     * This method gets information from the job description like job title and
     * company name.
     * 
     * @param driver  This is the web driver.
     * @param jobLink This is the link of the job of type string.
     * @param appType This is the application type of type string.
     * @param applied This is bool indicating whether or not the job has already
     *                been applied to.
     * @return This returns a new JobPostingData object.
     * @throws IOException Catch file errors.
     */
    private JobPostingData _getJobInformation(String jobLink, JobApplicationData.ApplicationType appType,
            boolean applied) throws IOException {

        String remote, submitted;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        String jobTitle = tryToFindElement(By.className("jobsearch-JobInfoHeader-title")).getText();
        WebElement companyLocationDiv = getWebDriver()
                .findElement(By.className("jobsearch-DesktopStickyContainer-subtitle"));
        List<WebElement> nestedDiv = companyLocationDiv.findElements(By.tagName("div"));
        List<WebElement> innerDivs = nestedDiv.get(0).findElements(By.tagName("div"));

        String companyName = innerDivs.get(0).getText();
        String companyLoc = innerDivs.get(innerDivs.size() - 1).getText();
        String isRemote = nestedDiv.get(nestedDiv.size() - 1).getText().toLowerCase();

        if (isRemote.contains("remote"))
            remote = "yes";
        else
            remote = "no";

        if (applied)
            submitted = "yes";
        else
            submitted = "no";

        // Return a new JobPostingData object.
        return new JobPostingData(jobMatchScore(By.id("jobDescriptionText")), jobTitle, companyName, companyLoc, remote,
                formatter.format(date), appType.name(), jobLink, submitted, "");
    }

    /**
     * Check if a job has been applied to.
     * 
     * @return True if applied, false otherwise.
     */
    private boolean _hasJobBeenAppliedTo() {

        // Check if job has been applied already.
        if (getWebDriver().findElements(By.id("ia_success")).size() > 0) {
            WebElement popUp = tryToFindElement(By.id("close-popup"));
            popUp.click();
            return true;
        } else {
            getActions().moveByOffset(0, 0).click().build().perform();
            getWebDriver().switchTo().defaultContent();
            return false;
        }
    }


}