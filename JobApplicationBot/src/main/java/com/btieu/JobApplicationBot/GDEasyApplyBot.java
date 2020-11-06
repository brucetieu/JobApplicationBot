package com.btieu.JobApplicationBot;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * A GDEasyApplyBot is a GlassdoorBot.
 * @author bruce
 *
 */
public class GDEasyApplyBot extends GlassdoorBot {
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
    public GDEasyApplyBot(JobApplicationData jobAppData, ApplicationType appType, WriteFiles writeFiles) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;
        _writeFiles = writeFiles;
    }

    /**
     * Aggregate only easy apply jobs on a page.
     * @throws InterruptedException Catch any not found element errors.
     * @throws IOException Catch any file writing errors.
     */
    public void findEasyApply() throws InterruptedException, IOException {
        int currPageNum = 1;
        int numOfJobs = jobsCard.size();

        int i = 0;

        try {
            while (i < numOfJobs) {

                boolean atLastCard = (i == jobsCard.size() - 1);
                boolean atLastPage = (currPageNum == JobPostingData.pagesToScrape);
                boolean isEasyApply = (jobsCard.get(i).findElements(By.className("jobLabel")).size() > 0);

                if (isEasyApply) {
                    String jobLink = getJobViewLink(i);
                    saveJob(jobLink, _appType);
                }

                // Stop at the last job listing & pagenum specified.
                if (atLastCard && atLastPage)
                    break;

                // Go to the next page to continue saving jobs.
                if (atLastCard) {
                    i = -1;
                    currPageNum += 1;
                    goToNextPage(currPageNum);
                }
                i++;
            }
            // Write saved jobs to csv.
            _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
            quitBrowser();
        } catch (Exception e) {
            _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
            quitBrowser();
        }

    }

}
