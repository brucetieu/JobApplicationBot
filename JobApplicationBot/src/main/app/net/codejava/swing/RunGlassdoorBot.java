package net.codejava.swing;

import java.util.List;

import com.btieu.JobApplicationBot.GlassdoorApplyBot;
import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.JobIterator;
import com.btieu.JobApplicationBot.Pagination;
import com.btieu.JobApplicationBot.WriteFiles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Execute all methods to save and apply to a Glassdoor job.
 * 
 * @author Bruce Tieu
 *
 */
public class RunGlassdoorBot {
    
    public static final By _GLASSDOOR_JOB_CARD = By.className("react-job-listing");

    /**
     * When this constructor is called, it will execute the GlassdoorBot.
     * 
     * @param appType     The application enum type.
     * @param jobAppData  The job application data object.
     * @param jobIterator The iterator object to loop through a list of jobs.
     * @param page        The page class to go to the next page.
     * @param writeFiles  The write files object for .csv exporting.
     */
    public RunGlassdoorBot(JobApplicationData.ApplicationType appType, JobApplicationData jobAppData,
            JobIterator jobIterator, Pagination page, WriteFiles writeFiles) {

        // Handle easy apply applications.
        if (appType == JobApplicationData.ApplicationType.EASILY_APPLY) {
            MessageDialog.infoBox(MessageDialog.GLASSDOOR_EASY_APPLY_MSG, MessageDialog.SUCCESSFUL_LAUNCH_TITLE);
            GlassdoorApplyBot easyApp = new GlassdoorApplyBot(jobAppData, appType);
            easyApp.navigateToJobPage();
            easyApp.login();
            easyApp.searchJobs();
            jobIterator.loopThroughJob(easyApp.tryToFindElements(_GLASSDOOR_JOB_CARD),
                    (int index, List<WebElement> jobList) -> {

                        easyApp.saveEasyApplyJobs(index, easyApp.tryToFindElements(_GLASSDOOR_JOB_CARD));
                    }, (int pageNum) -> page.goToNextGlassdoorPage(pageNum));
            MessageDialog.infoBox(MessageDialog.SUCCESS_JOB_SAVE_MSG, MessageDialog.SUCCESS_TITLE);

            // Handle all applications.
        } else if (appType == JobApplicationData.ApplicationType.ALL) {
            MessageDialog.infoBox(MessageDialog.GLASSDOOR_ALL_MSG, MessageDialog.SUCCESSFUL_LAUNCH_TITLE);
            GlassdoorApplyBot greedy = new GlassdoorApplyBot(jobAppData, appType);
            greedy.navigateToJobPage();
            greedy.login();
            greedy.searchJobs();
            jobIterator.loopThroughJob(greedy.tryToFindElements(_GLASSDOOR_JOB_CARD),
                    (int index, List<WebElement> jobList) -> {

                        greedy.saveAllJobs(index, greedy.tryToFindElements(_GLASSDOOR_JOB_CARD));
                    }, (int pageNum) -> page.goToNextGlassdoorPage(pageNum));
            MessageDialog.infoBox(MessageDialog.SUCCESS_JOB_SAVE_MSG, MessageDialog.SUCCESS_TITLE);
            
            // Handle not easy apply apps.
        } else if (appType == JobApplicationData.ApplicationType.NOT_EASY_APPLY) {
            MessageDialog.infoBox(MessageDialog.GLASSDOOR_EASY_APPLY_MSG, MessageDialog.SUCCESSFUL_LAUNCH_TITLE);
            GlassdoorApplyBot notEa = new GlassdoorApplyBot(jobAppData, appType);
            notEa.navigateToJobPage();
            notEa.login();
            notEa.searchJobs();
            jobIterator.loopThroughJob(notEa.tryToFindElements(_GLASSDOOR_JOB_CARD),
                    (int index, List<WebElement> jobList) -> {

                        notEa.saveNonEasyApplyJobs(index, notEa.tryToFindElements(_GLASSDOOR_JOB_CARD));
                    }, (int pageNum) -> page.goToNextGlassdoorPage(pageNum));
            MessageDialog.infoBox(MessageDialog.SUCCESS_JOB_SAVE_MSG, MessageDialog.SUCCESS_TITLE);
        }
    }

}
