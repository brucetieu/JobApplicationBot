package com.btieu.JobApplicationBot;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * A GDNotEasyApplyBot is a GlassdoorBot.
 * @author bruce
 *
 */
public class GDNotEasyApplyBot extends GlassdoorBot {
//    private BotActions _botActions;
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
//        _botActions = new BotActions();
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
        
        _leverForms.fillAllBasicInfo();
        _leverForms.fillAllWorkAuth();
        
        getWebDriver().findElement(By.name("resume")).sendKeys("/Users/bruce/Documents/Resumes/WithObj3_Bruce_Tieu_2020_Resume.pdf");
        
       
  
        
//        getDriver().findElement(By.name("resume")).sendKeys("/Users/bruce/Documents/Resumes/WithObj3_Bruce_Tieu_2020_Resume.pdf");
//        waitOnElementAndClick(By.className("template-btn-submit"));
         
    }
    
    public void applyToGreenhouseJobs(String greenhouseLink) throws IOException {
        getWebDriver().get(greenhouseLink);
        
        _greenhouseForms.fillAllBasicInfo();
        _greenhouseForms.fillAllWorkAuth();
        _greenhouseForms.fillAllHowDidYouFindUs();


        
        waitOnElementAndClick(By.cssSelector("a[data-source='paste']"));
        tryToFindElement(By.id("resume_text")).sendKeys(ExtractPDFText.extractPDFTextToString(new File("/Users/bruce/Documents/Resumes/WithObj3_Bruce_Tieu_2020_Resume.pdf")));
    
//            _botActions.waitOnElementAndClick(By.id("submit-app"));
    }
        


    
    public static void main(String[] args) throws IOException {
        JobApplicationData jobAppData = new JobApplicationData();
        WriteFiles writeFiles = new WriteFiles("jobPostingOutput.csv");
        JobApplicationData.ApplicationType appType = JobApplicationData.ApplicationType.ALL;
        GDNotEasyApplyBot gdNotEZApplyBot = new GDNotEasyApplyBot(jobAppData, appType, writeFiles);
        gdNotEZApplyBot.applyToGreenhouseJobs("https://boards.greenhouse.io/grammarly/jobs/476589?gh_src=tx7sab1");
        
    }

}
