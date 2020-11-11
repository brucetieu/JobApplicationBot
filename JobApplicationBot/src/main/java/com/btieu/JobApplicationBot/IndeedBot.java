package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * This class applies to jobs on Indeed.com.
 * 
 * @author Bruce Tieu
 */
public class IndeedBot extends Bot {
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private WriteFiles _writeFiles;
    private String _parentWindow;

    /**
     * This is a class constructor which initializes job application data, job
     * application type, a chrome driver, and sets the variables for the driver.
     * 
     * @param jobAppData  The object which holds job application data.
     * @param jobPostData The object which holds job posting data.
     * @param appType     The enum type which is a set of application types.
     */
    public IndeedBot(JobApplicationData _jobAppData, JobApplicationData.ApplicationType _appType,
            WriteFiles _writeFiles) {
        this._jobAppData = _jobAppData;
        this._appType = _appType;
        this._writeFiles = _writeFiles;
    }

    
    /**
     * Navigate to the Indeed site.
     */
    public void navigateToJobPage() {
        getDriver().get(this._jobAppData.platformUrl);
    }

 
    /**
     * This method logs in to the job site.
     * 
     * @throws InterruptedException
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
        typeLikeAHuman(email, this._jobAppData.email);
        typeLikeAHuman(password, this._jobAppData.password);

        waitOnElementAndClick(By.id("login-submit-button"));
    }

    /**
     * This method searches for jobs based on job position name and location.
     * 
     * @throws InterruptedException Catch errors if element is not found.
     */
    public void searchJobs() throws InterruptedException {

        // Click on the find jobs tab
        waitOnElementAndClick(By.className("gnav-PageLink-text"));

        // Locate the "What" and "Where" input fields.
        WebElement clearWhat = tryToFindElement(By.id("text-input-what"));
        WebElement clearWhere = tryToFindElement(By.id("text-input-where"));

        clearWhat.clear();

        // Delay typing.
        typeLikeAHuman(clearWhat, this._jobAppData.whatJob);

        // Clear the "Where" field and send in the location of the job.
        getActions().sendKeys(Keys.TAB);
        getActions().sendKeys(Keys.DELETE);
        getActions().build().perform();

        typeLikeAHuman(clearWhere, this._jobAppData.locationOfJob);
        clearWhere.submit();
    }

    /**
     * This method looks for Indeed Jobs that are easy to apply to (for now).
     * 
     * @throws Exception This checks for errors.
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

                // Find Easily Apply job card and open in new tab.
                if (jobsCard.get(i).findElements(By.className("iaLabel")).size() > 0) {

                    _parentWindow = getDriver().getWindowHandle(); // Get the current window.
                    String href = jobsCard.get(i).findElement(By.className("jobtitle")).getAttribute("href"); // Get the job link.
                    href = href.replace("rc/clk", "viewjob");
                    navigateToLinkInNewTab(href);  // Open that job in a new tab.
                    System.out.println(href);
                    // Save each job, remember original job listing page as tabs close.
                    saveJob(href, this._appType);
                }

                // Stop at the last job listing & pagenum specified.
                if (i == jobsCard.size() - 1 && currPageNum == JobPostingData.pageNum)
                    break;
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

        // Output saved jobs to a csv.
        this._writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
        quitBrowser();
    }

    /**
     * This method saves "Easily Apply" jobs to a container on Indeed.com.
     * 
     * @param currWindow The window with original job listings.
     * @throws InterruptedException Catch element not found errors.
     * @throws IOException          Catch file errors.
     */
    public void saveJob(String jobLink, JobApplicationData.ApplicationType appType)
            throws InterruptedException, IOException {

        // Check if job has been applied to.
        boolean isApplied = hasJobBeenAppliedTo();
        if (isApplied) {

            JobPostingData.jobPostingContainer.add(getJobInformation(jobLink, appType, isApplied)); // Save job. 
            getDriver().close(); // Close that new window (the job that was opened).
            getDriver().switchTo().window(_parentWindow); // Switch back to the parent window (job listing window).

        }
        // Continue searching for jobs if already applied to.
        else {
            getDriver().close();
            getDriver().switchTo().window(_parentWindow);
        }
    }

    /**
     * This method checks if a job has already been applied to.
     * 
     * @return True, if a job hasn't been applied to and false if it has.
     */
    public boolean hasJobBeenAppliedTo() {

        // Check if job has been applied already.
        if (getDriver().findElements(By.id("ia_success")).size() > 0) {
            WebElement popUp = tryToFindElement(By.id("close-popup"));
            popUp.click();
            return false;
        } else
            return true;
    }
    
    /**
     * This method gets information from the job description like job title.
     * 
     * @param driver  This is the web driver.
     * @param jobLink This is the link of the job of type string.
     * @param appType This is the application type of type string.
     * @param applied This is bool indicating whether or not the job has already been applied to.
     * @return This returns a new JobPostingData object.
     * @throws IOException Catch file errors.
     */
    public JobPostingData getJobInformation(String jobLink, JobApplicationData.ApplicationType appType, boolean applied)
            throws IOException {

        String remote, submitted;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        String jobTitle = tryToFindElement(By.className("jobsearch-JobInfoHeader-title")).getText();
        WebElement companyLocationDiv = getDriver()
                .findElement(By.className("jobsearch-DesktopStickyContainer-subtitle"));
        List<WebElement> nestedDiv = companyLocationDiv.findElements(By.tagName("div"));
        List<WebElement> innerDivs = nestedDiv.get(0).findElements(By.tagName("div"));

        String companyName = innerDivs.get(0).getText();
        String companyLoc = innerDivs.get(innerDivs.size() - 1).getText();
        String isRemote = nestedDiv.get(nestedDiv.size() - 1).getText();

        if (isRemote != null) remote = "yes";
        else remote = "no";

        if (applied) submitted = "no";
        else submitted = "yes";

        // Return a new JobPostingData object.
        return new JobPostingData(jobMatchScore(By.id("jobDescriptionText")), jobTitle, companyName, companyLoc, remote, formatter.format(date),
                appType.name(), jobLink, submitted, "");
    }
    

}
