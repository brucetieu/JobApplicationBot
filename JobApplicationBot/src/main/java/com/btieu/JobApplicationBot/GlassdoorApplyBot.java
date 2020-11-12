package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

public class GlassdoorApplyBot extends GlassdoorBot {

    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

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
