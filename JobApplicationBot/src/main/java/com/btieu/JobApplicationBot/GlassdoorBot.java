package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
    public List<WebElement> jobsCard;

    public GlassdoorBot(JobApplicationData _jobAppData, JobApplicationData.ApplicationType _appType) {
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
     * @throws InterruptedException If the thread executing the method is
     *                              interrupted, stop the method and return early.
     */
    @Override
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
    @Override
    public void searchJobs() throws InterruptedException {
        WebElement searchKey = tryToFindElement(By.id("sc.keyword"));
        WebElement searchLoc = tryToFindElement(By.id("sc.location"));
        searchKey.clear();
        searchLoc.clear();

//        searchKey.sendKeys(this._jobAppData.whatJob);
        typeLikeAHuman(searchKey, this._jobAppData.whatJob);

        // Clear the "Where" field and send in the location of the job
        getActions().sendKeys(Keys.TAB);
        getActions().sendKeys(Keys.TAB);
        getActions().sendKeys(Keys.DELETE);
        getActions().build().perform();

        typeLikeAHuman(searchLoc, this._jobAppData.locationOfJob);
//        searchLoc.sendKeys(this._jobAppData.locationOfJob);
//        WebElement submit = tryToFindElement(By.className("gd-ui-button"));
        searchLoc.submit();

        jobsCard = getDriver().findElements(By.className("react-job-listing"));
    }

    public void getJobLinks() throws InterruptedException, IOException {
        Set<String> jobLinks = new HashSet<String>();

        jobsCard = tryToFindElements((By.className("react-job-listing")));

        for (int i = 0; i < jobsCard.size(); i++) {
            WebElement div = jobsCard.get(i).findElement(By.className("d-flex"));
            String href = div.findElement(By.className("jobLink")).getAttribute("href");
            String hrefReplace = href.replaceAll("GD_JOB_AD", "GD_JOB_VIEW");
            jobLinks.add(getRequestURL(hrefReplace));
            System.out.println(getRequestURL(hrefReplace));
        }
    }

    public String getRequestURL(String href) throws IOException {
        URL url = new URL(href);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.getContent();
        return connection.getURL().toString();
    }

    public void goToNextPage(int pageNum) {
        String pageUrl = getDriver().getCurrentUrl();
        String newPageNum = "_P" + Integer.toString(pageNum) + ".htm";
        String newPageUrl = pageUrl.replace(".hml", newPageNum);
        getDriver().get(newPageUrl);
      
    }

    /**
     * Get the actual link of the job.
     * 
     * @param index The index it's at in the list of job cards.
     * @return The link of the job.
     */
    public String getJobViewLink(int index) {

        _parentWindow = getDriver().getWindowHandle(); // Get the current window.
        WebElement div = jobsCard.get(index).findElement(By.className("d-flex"));
        String href = div.findElement(By.className("jobLink")).getAttribute("href");

        navigateToLinkInNewTab(href); // Open that job in a new tab.
        System.out.println(href);
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

        JobPostingData.jobPostingContainer.add(getJobInformation(jobLink, appType, false)); // Save job.
        getDriver().close(); // Close that new window (the job that was opened).
        getDriver().switchTo().window(_parentWindow); // Switch back to the parent window (job listing window).

    }
    
    private JobPostingData getJobInformation(String jobLink, JobApplicationData.ApplicationType appType, boolean applied)
            throws IOException {

        String remote, submitted;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        
        WebElement container = tryToFindElement(By.id("JobView"));
        WebElement dflex1 = container.findElement(By.className("d-flex")).findElement(By.className("d-flex"));
        List<WebElement> dflex2 = dflex1.findElements(By.tagName("div"));
        List<WebElement> div = dflex2.get(0).findElements(By.tagName("div"));
        WebElement secondDiv = div.get(div.size()-1).findElement(By.className("d-flex"));
        WebElement thirdDiv = secondDiv.findElement(By.tagName("div"));
        List<WebElement> infoDiv = thirdDiv.findElements(By.tagName("div"));
        
//        WebElement div = dflex2.get(0).findElement(By.className("d-flex"));
//        WebElement div2 = div.findElement(By.tagName("div"));
//        List<WebElement> moreDivs = div2.findElements(By.tagName("div"));
//        List<WebElement> dflex3 = dflex2.get(dflex2.size()-1).findElements(By.tagName("div"));
//        WebElement nestedDiv = dflex3.get(dflex3.size()-1).findElement(By.className("d-flex"));
//      WebElement innerDiv = nestedDiv.findElement(By.tagName("div"));
//      List<WebElement> infoDivs = innerDiv.findElements(By.tagName("div"));
        
//        List<WebElement> divs = dflex3.findElements(By.tagName("div"));
//        WebElement nestedDiv = divs.get(divs.size()-1).findElement(By.className("d-flex"));
//        WebElement innerDiv = nestedDiv.findElement(By.tagName("div"));
//        List<WebElement> infoDivs = innerDiv.findElements(By.tagName("div"));
        String companyName = infoDiv.get(0).getAttribute("innerHTML").split("\n")[0];
        String jobTitle = infoDiv.get(1).getText();
        String jobLocation = infoDiv.get(infoDiv.size() - 1).getText();
        System.out.println(companyName + ", " + jobTitle + ", " + jobLocation);
        
//        WebElement flexContainer = tryToFindElement(By.className("d-flex"));
//        WebElement div = flexContainer.findElement(By.tagName("div"));
//        List<WebElement> listOfDivs = div.findElements(By.tagName("div"));
//
////        /html/body/div[3]/div/div/div[1]/div[1]/div[2]/div/div/div[2]/div/div[1]/div[2]/div/div/div[1]
//        String companyName = (String)((JavascriptExecutor)getDriver()).executeScript(""
//                + "let listOfDivs = document.querySelector('d-flex')[0].getElementByTagName('div').getElementsByTagName('div');"
//                + "return listOfDivs[0].childNodes[0].nodeValue;");
//        System.out.println(companyName);
//        String jobTitle = listOfDivs.get(1).getText();
//        String companyLoc = listOfDivs.get(listOfDivs.size() - 1).getText();


        if (jobLocation.toLowerCase() != "remote") remote = "no";
        else remote = "yes";

        if (applied) submitted = "no";
        else submitted = "yes";

        // Return a new JobPostingData object.
        return new JobPostingData(jobMatchScore(By.id("JobDescriptionContainer")), jobTitle, companyName, jobLocation, remote,
                formatter.format(date), appType.name(), jobLink, submitted, "");
    }


}
