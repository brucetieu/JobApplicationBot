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
        getDriver().get(leverLink);
       
        waitOnElementAndClick(By.className("template-btn-submit"));

        tryToFindElementAndSendKeys(By.name("name"), "Bruce Tieu");
        tryToFindElementAndSendKeys(By.name("email"), "ucdbrucetieu");
        tryToFindElementAndSendKeys(By.name("phone"), "5");
        tryToFindElementAndSendKeys(By.name("org"), "x"); // current company
        tryToFindElementAndSendKeys(By.name("urls[LinkedIn]"), "x");
        tryToFindElementAndSendKeys(By.name("urls[GitHub]"),"x");
        tryToFindElementAndSendKeys(By.name("urls[Portfolio]"), "x"); // portfolio
//        getDriver().findElement(By.name("resume")).sendKeys("/Users/bruce/Documents/Resumes/WithObj3_Bruce_Tieu_2020_Resume.pdf");
        tryToFindElementAndSendKeys(By.name("resume"), "/Users/bruce/Documents/Resumes/WithObj3_Bruce_Tieu_2020_Resume.pdf");
        tryToFindElementAndSendKeys(By.xpath("//textarea[@class='card-field-input']"), "No"); // visa
//        waitOnElementAndClick(By.xpath("//*[contains(@value,'Yes')]"));
        waitOnElementAndClick(By.xpath("//input[@type='radio' and @value='Yes']"));
        
  
        
//        getDriver().findElement(By.name("resume")).sendKeys("/Users/bruce/Documents/Resumes/WithObj3_Bruce_Tieu_2020_Resume.pdf");
//        waitOnElementAndClick(By.className("template-btn-submit"));
         
    }
    
    public void applyToGreenhouseJobs(String greenhouseLink) throws IOException {
        getDriver().get(greenhouseLink);
            
        tryToFindElementAndSendKeys(By.id("first_name"), "Bruce Tieu");
        tryToFindElementAndSendKeys(By.id("last_name"), "ucdbrucetieu");
        tryToFindElementAndSendKeys(By.id("email"), "");
        tryToFindElementAndSendKeys(By.id("phone"), "");
        tryToFindElementAndSendKeys(By.id("job_application_answers_attributes_0_text_value"), ""); // linkedin
        tryToFindElementAndSendKeys(By.id("job_application_location"), "");  // city
          
        waitOnElementAndClick(By.cssSelector("a[data-source='paste']"));
        tryToFindElement(By.id("resume_text")).sendKeys(ExtractPDFText.extractPDFTextToString(new File("/Users/bruce/Documents/Resumes/WithObj3_Bruce_Tieu_2020_Resume.pdf")));
    
        tryToSelectFromDpn(By.id("job_application_answers_attributes_2_answer_selected_options_attributes_2_question_option_id"), "Glassdoor");
        tryToFindElementAndSendKeys(By.id("job_application_answers_attributes_2_text_value"), "Glassdoor");

//            waitOnElementAndClick(By.id("submit-app"));
        
//        try {
//          _jobAppData.school = "University of Colorado Denver";
////            WebElement education = tryToFindElement(By.id("s2id_education_school_name_0"));
////            education.click();
//            waitOnElementAndClick(By.id("s2id_education_school_name_0"));
//            WebElement dropdown = tryToFindElement(By.id("select2List1"));
//
////            WebElement ul = getDriver().findElement(By.id("s2id_education_school_name_0"));
////            System.out.println(ul.getText());
////            WebElement li = dropdown.findElement(By.tagName("li"));
//            
//            int i = 0;
//            while (true) {
//                i++;
//                WebElement li = dropdown.findElement(By.tagName("li"));
//
////            for (int i = 0; i < li.size(); i++) {
//                getActions().moveToElement(li);
//                getActions().sendKeys(Keys.DOWN).perform();
//                System.out.println(li.getText());
//                if (li.getText().contains(_jobAppData.school)) {
//                    li.click();
//                    break;
//                }
//           
//                System.out.println(i);
//            }
////            waitOnElementAndClick(By.xpath("//*[/span[contains(text(),"  + _jobAppData.school + ")]]"));
//        } catch (Exception e) {}
    }

    
    public static void main(String[] args) throws IOException {
        JobApplicationData jobAppData = new JobApplicationData();
        WriteFiles writeFiles = new WriteFiles("jobPostingOutput.csv");
        JobApplicationData.ApplicationType appType = JobApplicationData.ApplicationType.ALL;
        GDNotEasyApplyBot gdNotEZApplyBot = new GDNotEasyApplyBot(jobAppData, appType, writeFiles);
        gdNotEZApplyBot.applyToLeverJobs("https://jobs.lever.co/kiddom/c45db39d-db8d-444d-8540-5ee9455f472a/apply");
        
    }

}
