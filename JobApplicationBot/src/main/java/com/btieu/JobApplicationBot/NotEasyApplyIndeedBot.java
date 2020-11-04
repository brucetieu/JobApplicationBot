package com.btieu.JobApplicationBot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * An EasyApplyBot is an IndeedBot, except with a function to find easy apply
 * jobs.
 * 
 * @author Bruce Tieu
 *
 */
public class NotEasyApplyIndeedBot extends IndeedBot {

    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private WriteFiles _writeFiles;

    /**
     * This is a class constructor which initializes job application data, job
     * application type, and writing of files.
     * 
     * @param jobAppData The object which holds job application data.
     * @param appType    The enum type which is a set of application types.
     * @param writeFiles Class for writing files.
     */
    public NotEasyApplyIndeedBot(JobApplicationData jobAppData, ApplicationType appType, WriteFiles writeFiles) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;
        _writeFiles = writeFiles;

    }

    /**
     * Do not find the easy apply jobs from Indeed.
     * 
     * @throws Exception Catch missing element errors.
     */
    public void skipEasyApply() throws Exception {

        // Create iterator to iterate through the cards.
        JobElementIterator itr = new JobElementIterator(jobsCard);
        int currPageNum = 0;

        // Loop through each of the job divs present on the page.
        try {
            while (itr.hasNext()) {

                WebElement jobElement = itr.next();

                boolean isEasyApply = (jobElement.findElements(By.className("iaLabel")).size() > 0);
                boolean atLastCard = (itr.getCurrIndex() - 1 == jobsCard.size() - 1);
                boolean atLastPage = (currPageNum == JobPostingData.pagesToScrape);

                if (!isEasyApply) {
                    String jobLink = getJobViewLink(itr.getCurrIndex() - 1);

                    // Get the actual job application link to the company website, not Indeed.
                    jobLink = jobLink.replace("viewjob", "rc/clk");
                    jobLink = jobLink.replace("vjs", "assa");
                    saveJob(jobLink, _appType);
                }

                // Stop at the last job listing & pagenum specified.
                if (atLastCard && atLastPage)
                    break;

                // Go to the next page to continue saving jobs.
                if (atLastCard) {
                    itr.setCurrIndex(0);
                    currPageNum += 1;
                    goToNextPage(currPageNum);
                    itr = new JobElementIterator(jobsCard); // Create new iterator instance.
                }
            }
        } catch (Exception e) {
            // If there's an error while looping, write current jobs saved to the csv.
            _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
            quitBrowser();
        }

        // Write all jobs to excel file.
        _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
        quitBrowser();
    }

}
