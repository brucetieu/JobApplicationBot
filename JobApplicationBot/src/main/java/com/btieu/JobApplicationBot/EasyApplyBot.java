package com.btieu.JobApplicationBot;

import org.openqa.selenium.By;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * An EasyApplyBot is an IndeedBot, except with a function to find easy apply jobs. 
 * 
 * @author Bruce Tieu
 *
 */
public class EasyApplyBot extends IndeedBot {

    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private WriteFiles _writeFiles;

    /**
     * This is a class constructor which initializes job application data, job
     * application type, and writing of files.
     * 
     * @param jobAppData  The object which holds job application data.
     * @param appType     The enum type which is a set of application types.
     * @param writeFiles  Class for writing files. 
     */
    public EasyApplyBot(JobApplicationData jobAppData, ApplicationType appType, WriteFiles writeFiles) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;
        _writeFiles = writeFiles;

    }

    /**
     * Find the easy apply jobs from Indeed.
     * 
     * @throws Exception Catch missing element errors. 
     */
    public void findEasyApply() throws Exception {

        int currPageNum = 0;
        int numOfJobs = jobsCard.size();

        // Loop through each of the job divs present on the page.
        int i = 0;
        while (i < numOfJobs) {

            boolean isEasyApply = (jobsCard.get(i).findElements(By.className("iaLabel")).size() > 0);
            boolean atLastCard = (i == jobsCard.size() - 1);
            boolean atLastPage = (currPageNum == JobPostingData.pageNum);

            if (isEasyApply) {
                String jobLink = getJobViewLink(i);
                clickOnApplyButton();
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

        // Write all jobs to excel file.
        _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
        quitBrowser();
    }

}
