package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private String _parentWindow;

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
    
    /**
     * Get the page to view the job description.
     * 
     * @param index The index of the current job in the list of job cards.
     * @return The link of the job.
     * @throws IOException 
     */
    public String getJobViewLink(int index, List<WebElement> jobList) throws IOException {

        _parentWindow = getWebDriver().getWindowHandle(); // Get the current window.
        WebElement div = jobList.get(index).findElement(By.className("d-flex"));
        String href = div.findElement(By.className("jobLink")).getAttribute("href");
        navigateToLinkInNewTab(href); // Open that job in a new tab.
        return href;
    }
    
    /**
     * Save each job to a container.
     * 
     * @param jobLink The application page.
     * @param appType The type of application it was.
     * @throws InterruptedException Catch any elements that are not found.
     * @throws IOException          Catch and file writing errors.
     */
    public void saveJob(String jobLink, JobApplicationData.ApplicationType appType)
            throws InterruptedException, IOException {
        
        boolean hasJobInContainer = JobPostingData.jobPostingContainer.contains(getJobInformation(jobLink, appType, false));

        if (!hasJobInContainer) {
            JobPostingData.jobPostingContainer.add(getJobInformation(jobLink, appType, false)); // Save job.
            getWebDriver().close(); // Close that new window (the job that was opened).
            getWebDriver().switchTo().window(_parentWindow); // Switch back to the parent window (job listing window).
        }
    }

    /**
     * Scrape the job view page for the company name, job title, location, if it was
     * applied to, and the job status.
     */
    public JobPostingData getJobInformation(String jobLink, JobApplicationData.ApplicationType appType, boolean applied)
            throws IOException {

        String remote, submitted;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        WebElement jobContainer = tryToFindElement(By.id("JobView"));
        WebElement empInfo = jobContainer.findElement(By.className("smarterBannerEmpInfo"));
        WebElement flexColumn = empInfo.findElement(By.className("flex-column"));
        WebElement div = flexColumn.findElement(By.tagName("div"));
        List<WebElement> divs = div.findElements(By.tagName("div"));
        WebElement companyName = divs.get(0);

        String companyNameString = getTextExcludingChildren(companyName);
        String jobTitleString = divs.get(1).getText();
        String jobLocationString = divs.get(2).getText();

        if (jobLocationString.toLowerCase().contains("remote"))
            remote = "yes";
        else
            remote = "no";

        if (applied)
            submitted = "yes";
        else
            submitted = "no";

        // Return a new JobPostingData object.
        return new JobPostingData(jobMatchScore(By.id("JobDescriptionContainer")), jobTitleString, companyNameString,
                jobLocationString, remote, formatter.format(date), appType.name(), jobLink, submitted, "");
    }

}
