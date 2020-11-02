package com.btieu.JobApplicationBot;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * A GDNotEasyApplyBot is a GlassdoorBot.
 * @author bruce
 *
 */
public class GDNotEasyApplyBot extends GlassdoorBot {
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private WriteFiles _writeFiles;

    /**
     * Intialize data and file writing objects.
     * 
     * @param jobAppData The applicant information.
     * @param appType The type of application.
     * @param writeFiles The object which writes the jobs to a csv.
     */
    public GDNotEasyApplyBot(JobApplicationData jobAppData, ApplicationType appType, WriteFiles writeFiles) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;
        _writeFiles = writeFiles;
    }
    
    /**
     * Aggregate jobs that are not easily apply ones.
     * @throws InterruptedException
     * @throws IOException
     */
    public void skipEasyApply() throws InterruptedException, IOException {
        int currPageNum = 1;
        int numOfJobs = jobsCard.size();

        int i = 0;
        
        try {
        while (i < numOfJobs) {
            
            boolean atLastCard = (i == jobsCard.size() - 1);
            boolean atLastPage = (currPageNum == JobPostingData.pageNum);
            boolean isEasyApply = (jobsCard.get(i).findElements(By.className("jobLabel")).size() > 0);

            if (!isEasyApply) {
                String jobLink = getJobViewLink(i);
                jobLink = jobLink.replaceAll("GD_JOB_AD", "GD_JOB_VIEW");
                saveJob(getRequestURL(jobLink), _appType);
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
        } catch (Exception e){
            _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
            quitBrowser();
        }

    }

}
