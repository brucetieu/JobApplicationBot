package net.codejava.swing;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.JobIterator;
import com.btieu.JobApplicationBot.LeverGreenhouseBot;
import com.btieu.JobApplicationBot.Pagination;
import com.btieu.JobApplicationBot.WriteFiles;

/**
 * Execute all methods to apply to Lever and Greenhouse jobs.
 * 
 * @author Bruce Tieu
 *
 */
public class RunLeverGreenhouseBot {

    public RunLeverGreenhouseBot(JobApplicationData.ApplicationType appType, JobApplicationData jobAppData,
            JobIterator jobIterator, Pagination page, WriteFiles writeFiles) {
        if (appType == JobApplicationData.ApplicationType.LEVER_GREENHOUSE) {
            MessageDialog.infoBox(MessageDialog.LEVER_GREENHOUSE_MSG, MessageDialog.SUCCESSFUL_LAUNCH_TITLE);
            LeverGreenhouseBot lg = new LeverGreenhouseBot(jobAppData, appType, writeFiles);
            lg.navigateToJobPage();
            lg.login();
            lg.searchJobs();
            jobIterator.loopThroughJob(lg.tryToFindElements(RunGlassdoorBot._GLASSDOOR_JOB_CARD),
                    (int index, List<WebElement> jobList) -> {

                        lg.saveLGJobs(index, lg.tryToFindElements(RunGlassdoorBot._GLASSDOOR_JOB_CARD));
                    }, (int pageNum) -> page.goToNextGlassdoorPage(pageNum));

            // Apply to lever and greenhouse jobs.
            lg.apply();
            MessageDialog.infoBox(MessageDialog.SUCCESS_JOB_SAVE_MSG, MessageDialog.SUCCESS_TITLE);
        }
    }
}
