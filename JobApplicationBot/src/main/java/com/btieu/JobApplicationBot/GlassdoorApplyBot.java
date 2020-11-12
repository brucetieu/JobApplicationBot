package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * A GlassdoorApplyBot is a GlassdoorBot. Define methods which will implement the
 * interface with lambda expressions.
 * 
 * @author Bruce Tieu
 *
 */
public class GlassdoorApplyBot extends GlassdoorBot {

    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private WriteFiles _writeFiles;
    private GreenhouseForms _greenhouseForms;
    private LeverForms _leverForms;


    /**
     * Parameterized constructor to initialize JobApplicationData.
     * 
     * @param jobAppData The JobApplicationData object.
     * @param appType    The enum holding application types.
     */
    public GlassdoorApplyBot(JobApplicationData jobAppData, ApplicationType appType, WriteFiles writeFiles) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;
        _writeFiles = writeFiles;
        _greenhouseForms = new GreenhouseForms();
        _leverForms = new LeverForms();

    }

    /**
     * Find all the Glassdoor easy apply jobs on a given page.
     * 
     * @param index   The particular index in the list of jobs.
     * @param jobList The list of all jobs.
     */
    public void saveEasyApplyJobs(int index, List<WebElement> jobList) {

        boolean isEasyApply = jobList.get(index).findElements(By.className("jobLabel")).size() > 0;

        if (isEasyApply) {
            String jobLink = getJobViewLink(index, jobList);
            saveJob(jobLink, _appType);
            System.out.println(jobLink);
        }
    }
    
    public void saveNonEasyApplyJobs(int index, List<WebElement> jobList) {
        boolean isEasyApply = (jobList.get(index).findElements(By.className("jobLabel")).size() > 0);

        if (!isEasyApply) {
            String jobLink = getJobViewLink(index, jobList);
            jobLink = jobLink.replaceAll("GD_JOB_AD", "GD_JOB_VIEW");
            System.out.println(getRequestURL(jobLink));
            saveJob(getRequestURL(jobLink), _appType);
        }

    }
    
    /**
     * Find both easy apply and not easy apply jobs.
     * 
     * @param index   The particular index in the list of jobs.
     * @param jobList The list of all jobs.
     */
    public void saveAllJobs(int index, List<WebElement> jobList) {

        String jobLink = getJobViewLink(index, jobList);
        jobLink = jobLink.replaceAll("GD_JOB_AD", "GD_JOB_VIEW");
        saveJob(getRequestURL(jobLink), _appType);
        System.out.println(jobLink);

    }
    
    /**
     * Apply to Lever and Greenhouse jobs
     * @throws IOException
     */
    public void massApply() {

        for (int i = 0; i < JobPostingData.jobPostingContainer.size(); i++) {
            try {
                if (JobPostingData.jobPostingContainer.get(i).jobLink.contains("lever")) {
                    System.out.println("Applying to lever job...");
                    applyToLeverJobs(JobPostingData.jobPostingContainer.get(i).jobLink);
                    if (getWebDriver().findElements(By.xpath("//h3[@data-qa='msg-submit-success']")).size() > 0) {
                        System.out.println("Successfully applied");
                        JobPostingData.jobPostingContainer.get(i).submitted = "Yes";
                    }
                } else if (JobPostingData.jobPostingContainer.get(i).jobLink.contains("greenhouse")) {
                    System.out.println("Applying to greenhouse job...");
                    applyToGreenhouseJobs(JobPostingData.jobPostingContainer.get(i).jobLink);
                    if (tryToFindElements(By.id("application_confirmation")).size() > 0) {
                        System.out.println("Successfully applied");
                        JobPostingData.jobPostingContainer.get(i).submitted = "Yes";
                    }
                }
            } 
            catch (IOException e) {
                continue;
//                _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
            }
        }
            try {
                _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
            } catch (IOException e) {
                e.getMessage();
            }
//        } catch (Exception e) {
//            _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
//        }
    }
    

    public void applyToLeverJobs(String leverLink) {
        getWebDriver().get(leverLink);
        waitOnElementAndClick(By.className("template-btn-submit"));

        _leverForms.fillAllBasicInfo(_jobAppData);
        _leverForms.fillAllWorkAuth(_jobAppData);
        _leverForms.fillAllHowDidYouFindUs();
        _leverForms.uploadResume();
        _leverForms.submitApplication();

    }

    public void applyToGreenhouseJobs(String greenhouseLink) throws IOException {
        getWebDriver().get(greenhouseLink);
        waitOnElementAndClick(By.className("template-btn-submit"));

        _greenhouseForms.fillAllBasicInfo(_jobAppData);
        _greenhouseForms.fillAllWorkAuth(_jobAppData);
        _greenhouseForms.fillAllHowDidYouFindUs();
        _greenhouseForms.approveConsent();
        _greenhouseForms.uploadResume();
        _greenhouseForms.submitApplication();

    }


}
