package com.btieu.JobApplicationBot;

import java.util.List;

import org.openqa.selenium.WebElement;

@FunctionalInterface
public interface ApplyInterface {
    /**
     * Abstract method that is implemented by lamda expression.
     * 
     * @param index   Index of the job.
     * @param jobList The job list.
     */
    public void handleJob(int index, List<WebElement> jobList);
}

