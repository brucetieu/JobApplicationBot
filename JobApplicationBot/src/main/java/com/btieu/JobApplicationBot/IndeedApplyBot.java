package com.btieu.JobApplicationBot;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * An IndeedApplyBot is an IndeedBot. Define methods which will implement this
 * interface with lambda expressions.
 * 
 * @author Bruce Tieu
 *
 */
class IndeedApplyBot extends IndeedBot {

    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

    /**
     * Parameterized constructor to initialize JobApplicationData.
     * 
     * @param jobAppData The JobApplicationData object.
     * @param appType    The enum holding application types.
     */
    public IndeedApplyBot(JobApplicationData jobAppData, ApplicationType appType) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;

    }

    /**
     * Find all the Indeed easy apply jobs on a given page.
     * 
     * @param index   The particular index in the list of jobs.
     * @param jobList The list of all jobs.
     */
    public void saveEasyApplyJobs(int index, List<WebElement> jobList) {

        boolean isEasyApply = jobList.get(index).findElements(By.className("iaLabel")).size() > 0;

        if (isEasyApply) {
            String jobLink = getJobViewLink(index, jobList);
            System.out.println(jobLink);
            clickOnApplyButton();
            saveEZApplyJob(jobLink, _appType);
        }
    }

    /**
     * Skip easy apply jobs - ie get the not easy apply jobs.
     * 
     * @param index   The particular index in the list of jobs.
     * @param jobList The list of all jobs.
     */
    public void saveNonEasyApplyJobs(int index, List<WebElement> jobList) {

        boolean isEasyApply = jobList.get(index).findElements(By.className("iaLabel")).size() > 0;

        if (!isEasyApply) {

            String jobLink = getJobViewLink(index, jobList);
            jobLink = jobLink.replace("viewjob", "rc/clk");
            jobLink = jobLink.replace("vjs", "assa");
            System.out.println(jobLink);
            saveJob(jobLink, _appType);
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
        jobLink = jobLink.replace("viewjob", "rc/clk");
        jobLink = jobLink.replace("vjs", "assa");
        System.out.println(jobLink);
        saveJob(jobLink, _appType);
    }
}