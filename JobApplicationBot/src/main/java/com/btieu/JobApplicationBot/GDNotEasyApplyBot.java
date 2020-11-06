package com.btieu.JobApplicationBot;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;

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
    private GreenhouseForms _greenhouseForms;
    private LeverForms _leverForms;

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
        _greenhouseForms = new GreenhouseForms();
        _leverForms = new LeverForms();
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
            boolean atLastPage = (currPageNum == JobPostingData.pagesToScrape);
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
    
    public void massApply() throws IOException {
        for (int i = 0; i < JobPostingData.jobPostingContainer.size(); i++) {
            if (JobPostingData.jobPostingContainer.get(i).jobLink.contains("lever")) {
                applyToLeverJobs(JobPostingData.jobPostingContainer.get(i).jobLink);
            }
            else if (JobPostingData.jobPostingContainer.get(i).jobLink.contains("lever")) {
                applyToGreenhouseJobs(JobPostingData.jobPostingContainer.get(i).jobLink);
            }
        }
    }
    
    public void applyToLeverJobs(String leverLink) {
        getWebDriver().get(leverLink);
       
        waitOnElementAndClick(By.className("template-btn-submit"));
        
        _leverForms.fillAllBasicInfo(_jobAppData);
        _leverForms.fillAllWorkAuth(_jobAppData);
        _leverForms.uploadResume(_jobAppData);
        
    }
    
    public void applyToGreenhouseJobs(String greenhouseLink) throws IOException {
        getWebDriver().get(greenhouseLink);
        
        _greenhouseForms.fillAllBasicInfo(_jobAppData);
        _greenhouseForms.fillEducation(_jobAppData);
        _greenhouseForms.fillAllWorkAuth(_jobAppData);
        _greenhouseForms.fillAllHowDidYouFindUs(_jobAppData);
        _greenhouseForms.uploadResume(_jobAppData);
    
   
    }
        

    public static void main(String[] args) throws IOException {
        JobApplicationData jobAppData = new JobApplicationData();
        WriteFiles writeFiles = new WriteFiles("jobPostingOutput.csv");
        JobApplicationData.ApplicationType appType = JobApplicationData.ApplicationType.ALL;
        GDNotEasyApplyBot gdNotEZApplyBot = new GDNotEasyApplyBot(jobAppData, appType, writeFiles);
        gdNotEZApplyBot.applyToGreenhouseJobs("https://boards.greenhouse.io/singlestore/jobs/2328586?gh_src=a607b2971us");
        
    }

}
