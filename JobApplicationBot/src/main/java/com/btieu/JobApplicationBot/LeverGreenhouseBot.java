package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * A lot of the not easy apply jobs on Glassdoor are Greenhouse or Lever links,
 * so this class will apply to those jobs.
 * 
 * @author Bruce Tieu
 *
 */
public class LeverGreenhouseBot extends GlassdoorBot {

    private JobApplicationData.ApplicationType _appType;
    private JobApplicationData _jobAppData;
    private WriteFiles _writeFiles;
    private LeverForms _leverForms;

    /**
     * Parameterized constructor which initializes JobApplicationData, WriteFiles,
     * and Form objects.
     * 
     * @param jobAppData The JobApplicationData object.
     * @param appType    The job application type enum.
     * @param writeFiles The writing files object.
     */
    public LeverGreenhouseBot(JobApplicationData jobAppData, ApplicationType appType, WriteFiles writeFiles) {
        super(jobAppData, appType);
        _appType = appType;
        _jobAppData = jobAppData;
        _writeFiles = writeFiles;
        _leverForms = new LeverForms();
    }

    /**
     * Save the lever and greenhouse jobs without assigning a job match score.
     */
    public void saveLGJobs(int index, List<WebElement> jobList) {
        saveJob(_appType, jobList, index);
    }

    /**
     * Apply to Lever and Greenhouse jobs.
     * 
     */
    public void apply() {

        for (JobPostingData jobPost : JobPostingData.jobPostingContainer) {

            boolean isLever = jobPost.jobLink.contains("lever");
            String joblink = jobPost.jobLink;

            if (isLever) {
                System.out.println("Applying to lever job...");
                applyToLeverJobs(joblink);
                if (elementExists(By.xpath("//h3[@data-qa='msg-submit-success']"))) {
                    System.out.println("Successfully applied");
                    jobPost.submitted = "Yes";
                }
            } 
        }
        try {
            _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Apply to Lever jobs.
     * 
     * @param leverLink The link to lever application.
     */
    public void applyToLeverJobs(String leverLink) {
        getWebDriver().get(leverLink);
        waitOnElementAndClick(By.className("template-btn-submit"));

        _leverForms.fillAllBasicInfo(_jobAppData);
        _leverForms.fillAllWorkAuth(_jobAppData);
        _leverForms.fillAllHowDidYouFindUs();
        _leverForms.uploadResume();
        _leverForms.submitApplication();

    }

}
