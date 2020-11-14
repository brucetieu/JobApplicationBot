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

    /**
     * Parameterized constructor to initialize JobApplicationData.
     * 
     * @param jobAppData The JobApplicationData object.
     * @param appType    The enum holding application types.
     */
    public GlassdoorApplyBot(JobApplicationData jobAppData, ApplicationType appType) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;
    }

    /**
     * Find all the Glassdoor easy apply jobs on a given page.
     * 
     * @param index   The particular index in the list of jobs.
     * @param jobList The list of all jobs.
     * @throws IOException
     * @throws InterruptedException
     */
    public void saveEasyApplyJobs(int index, List<WebElement> jobList) {

        boolean isEasyApply = jobList.get(index).findElements(By.className("jobLabel")).size() > 0;

        if (isEasyApply) {
            String jobLink = getJobViewLink(index, jobList);
            saveJob(jobLink, _appType);
            System.out.println(jobLink);
        }
    }
    
    /**
     * Find all glassdoor non easy jobs and save them.
     * 
     * @param index   The particular index in the list of jobs.
     * @param jobList The list of jobs.
     */
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

}
