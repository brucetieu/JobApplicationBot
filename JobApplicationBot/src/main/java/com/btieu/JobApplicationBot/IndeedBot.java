package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * An Indeed bot is a type of Bot with different functions for applying to an Indeed job.
 * 
 * @author Bruce Tieu
 */
public class IndeedBot extends Bot {
    
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private String _parentWindow;
    public List<WebElement> jobsCard;

    /**
     * This is a class constructor which initializes job application data, job
     * application type.
     * 
     * @param jobAppData  The object which holds job application data.
     * @param appType     The enum type which is a set of application types.
     */
    public IndeedBot(JobApplicationData _jobAppData, JobApplicationData.ApplicationType _appType) {
        this._jobAppData = _jobAppData;
        this._appType = _appType;
    }

    /**
     * Navigate to the Indeed site.
     */
    @Override
    public void navigateToJobPage() {
        getDriver().get(this._jobAppData.platformUrl);
    }

    /**
     * This method logs in to the job site.
     * 
     * @throws InterruptedException
     */
    @Override
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
    @Override
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
        
        jobsCard = getDriver().findElements(By.className("jobsearch-SerpJobCard"));
    }

    /**
     * Go the the next page.
     * 
     * @param pageNum The page number.
     */
    public void goToNextPage(int pageNum) {
        String nextPageUrl = "https://www.indeed.com/jobs?q=" + this._jobAppData.whatJob + "&l="
                + this._jobAppData.locationOfJob + "&start=" + pageNum * 10;
        getDriver().get(nextPageUrl);
        jobsCard = tryToFindElements(By.className("jobsearch-SerpJobCard"));
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
     * Save each job to a container.
     * 
     * @param jobLink The application page.
     * @param appType The type of application it was.
     * @throws InterruptedException Catch any elements that are not found.
     * @throws IOException Catch and file writing errors.
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
     * Get the actual link of the job.
     * 
     * @param index The index it's at in the list of job cards.
     * @return The link of the job.
     */
    public String getJobViewLink(int index) {
        _parentWindow = getDriver().getWindowHandle(); // Get the current window.
        String href = jobsCard.get(index).findElement(By.className("jobtitle")).getAttribute("href"); // Get the job link.
        href = href.replace("rc/clk", "viewjob");
        navigateToLinkInNewTab(href); // Open that job in a new tab.
        System.out.println(href);
        return href;
    }
    
    /**
     * This method gets information from the job description like job title and company name.
     * 
     * @param driver  This is the web driver.
     * @param jobLink This is the link of the job of type string.
     * @param appType This is the application type of type string.
     * @param applied This is bool indicating whether or not the job has already been applied to.
     * @return This returns a new JobPostingData object.
     * @throws IOException Catch file errors.
     */

    private JobPostingData getJobInformation(String jobLink, JobApplicationData.ApplicationType appType, boolean applied)
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
        return new JobPostingData(jobMatchScore(By.id("jobDescriptionText")), jobTitle, companyName, companyLoc, remote,
                formatter.format(date), appType.name(), jobLink, submitted, "");
    }
    
    /**
     * Check if a job has been applied to.
     * 
     * @return False, if it has, true if not.
     */
    private boolean hasJobBeenAppliedTo() {

        // Check if job has been applied already.
        if (getDriver().findElements(By.id("ia_success")).size() > 0) {
            WebElement popUp = tryToFindElement(By.id("close-popup"));
            popUp.click();
            return false;
        } else {
            getActions().moveByOffset(0, 0).click().build().perform();
            getDriver().switchTo().defaultContent();
            return true;
        }
    }


}