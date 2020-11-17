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
    private GreenhouseForms _greenhouseForms;

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
        _greenhouseForms = new GreenhouseForms();
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

        System.out.println(JobPostingData.jobPostingContainer.size());

        for (JobPostingData jobPost : JobPostingData.jobPostingContainer) {

            boolean isLever = jobPost.jobLink.contains("lever");
            boolean isGreenhouse = jobPost.jobLink.contains("greenhouse");

            try {
                if (isLever) {
                    System.out.println("Applying to lever job...");
                    applyToLeverJobs(jobPost.jobLink);
                    if (elementExists(By.xpath("//h3[@data-qa='msg-submit-success']"))) {
                        System.out.println("Successfully applied");
                        jobPost.submitted = "Yes";
                    }
                } 
                else if (isGreenhouse) {
                    System.out.println("Applying to greenhouse job...");
                    applyToGreenhouseJobs(jobPost.jobLink);
                    if (elementExists(By.id("application_confirmation"))) {
                        System.out.println("Successfully applied");
                        jobPost.submitted = "Yes";
                    }
                }

            } catch (Exception e) {
                continue;
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
        _leverForms.fillAllHowDidYouFindUs();
        _leverForms.uploadResume();
        _leverForms.submitApplication();

    }

    /**
     * Apply to Greenhouse jobs.
     * 
     * @param greenhouseLink The link to the Greenhouse application.
     */
    public void applyToGreenhouseJobs(String greenhouseLink) {
        getWebDriver().get(greenhouseLink);
        waitOnElementAndClick(By.className("template-btn-submit"));

        _greenhouseForms.fillAllBasicInfo(_jobAppData);
        _greenhouseForms.fillAllWorkAuth();
        _greenhouseForms.fillAllHowDidYouFindUs();
        _greenhouseForms.approveConsent();
        _greenhouseForms.uploadResume();
        _greenhouseForms.submitApplication();

    }
}
