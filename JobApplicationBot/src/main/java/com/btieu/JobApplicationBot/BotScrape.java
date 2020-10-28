package com.btieu.JobApplicationBot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * This class adds additional functionally to the Bot by getting job description
 * information from each job site.
 * 
 * @author bruce
 *
 */
public class BotScrape extends Bot {

    /**
     * This method gets information from the job description like job title,
     * company, etc.
     * 
     * @param driver  This is the web driver.
     * @param jobLink This is the link of the job of type string.
     * @param appType This is the application type of type string.
     * @param applied This is bool indicating whether or not the job has already
     *                been applied to.
     * @return This returns a new JobPostingData object.
     * @throws IOException
     */
    public JobPostingData getJobInformation(String jobLink, JobApplicationData.ApplicationType appType, boolean applied)
            throws IOException {

        String job_title, companyName, companyLoc, remote, submitted;
        double jobMatch = jobMatchAlgo();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        WebElement jobTitle = tryToFindElement(By.className("jobsearch-JobInfoHeader-title"));
        job_title = jobTitle.getText();
        WebElement companyLocationDiv = getDriver()
                .findElement(By.className("jobsearch-DesktopStickyContainer-subtitle"));
        List<WebElement> nestedDiv = companyLocationDiv.findElements(By.tagName("div"));
        List<WebElement> innerDivs = nestedDiv.get(0).findElements(By.tagName("div"));

        companyName = innerDivs.get(0).getText();
        companyLoc = innerDivs.get(innerDivs.size() - 1).getText();
        String isRemote = nestedDiv.get(nestedDiv.size() - 1).getText();

        if (isRemote != null)
            remote = "yes";
        else
            remote = "no";

        if (applied)
            submitted = "no";
        else
            submitted = "yes";

        // Return a new JobPostingData object.
        return new JobPostingData(jobMatch, job_title, companyName, companyLoc, remote, formatter.format(date),
                appType.name(), jobLink, submitted, "");
    }

    /**
     * Match the job description text to the resume.
     * 
     * @return The cosine similarity number.
     * @throws IOException Catch any file errors.
     */
    public double jobMatchAlgo() throws IOException {

        String jobDescriptionString = tryToFindElement(By.id("jobDescriptionText")).getText();
        TextDocument jobDescriptionText = new TextDocument(jobDescriptionString);

        // TODO! This example resume is used here just for testing. This will be
        // replaced with what the job seeker uploads in the GUI.
        TextDocument resumeText = new TextDocument(new File("/Users/bruce/Downloads/Resume-Software-Developer17.pdf"));
        return CosineSimilarity.cosineSimilarity(jobDescriptionText, resumeText);
    }

}
