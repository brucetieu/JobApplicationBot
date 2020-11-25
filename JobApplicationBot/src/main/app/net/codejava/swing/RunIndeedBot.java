package net.codejava.swing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.IndeedApplyBot;
import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.JobIterator;
import com.btieu.JobApplicationBot.Pagination;

/**
 * Run the IndeedBot.
 * 
 * @author Bruce Tieu
 *
 */
public class RunIndeedBot {
    
    // Magic string: the job div which contains all the a tags to each job link.
    private static final By _INDEED_JOB_CARD = By.className("jobsearch-SerpJobCard");

    /**
     * When this constructor is called, it will execute the IndeedBot.
     * 
     * @param appType     The application enum type.
     * @param jobAppData  The job application data object.
     * @param jobIterator The iterator object to loop through a list of jobs.
     * @param page        The page class to go to the next page.
     */
    public RunIndeedBot(JobApplicationData.ApplicationType appType, JobApplicationData jobAppData,
            JobIterator jobIterator, Pagination page) {

        // Handle easy apply applications.
        if (appType == JobApplicationData.ApplicationType.EASILY_APPLY) {
            MessageDialog.infoBox(MessageDialog.INDEED_EASY_APPLY_MSG, MessageDialog.SUCCESSFUL_LAUNCH_MSG);
            IndeedApplyBot easyApp = new IndeedApplyBot(jobAppData, appType);
            easyApp.navigateToJobPage();
            easyApp.searchJobs();
            jobIterator.loopThroughJob(easyApp.tryToFindElements(_INDEED_JOB_CARD),
                    (int index, List<WebElement> jobList) -> {

                        easyApp.saveEasyApplyJobs(index,
                                easyApp.tryToFindElements(_INDEED_JOB_CARD));
                    }, (int pageNum) -> page.goToNextIndeedPage(pageNum));
            MessageDialog.infoBox(MessageDialog.SUCCESS_JOB_SAVE_MSG, MessageDialog.SUCCESS_MSG);

            // Handle all applications.
        } else if (appType == JobApplicationData.ApplicationType.ALL) {
            MessageDialog.infoBox(MessageDialog.INDEED_ALL_MSG, MessageDialog.SUCCESSFUL_LAUNCH_MSG);
            IndeedApplyBot greedy = new IndeedApplyBot(jobAppData, appType);
            greedy.navigateToJobPage();
            greedy.searchJobs();
            jobIterator.loopThroughJob(greedy.tryToFindElements(_INDEED_JOB_CARD),
                    (int index, List<WebElement> jobList) -> {

                        greedy.saveAllJobs(index, greedy.tryToFindElements(_INDEED_JOB_CARD));
                    }, (int pageNum) -> page.goToNextIndeedPage(pageNum));
            MessageDialog.infoBox(MessageDialog.SUCCESS_JOB_SAVE_MSG, MessageDialog.SUCCESS_MSG);
            
            // Handle not easy apply apps.
        } else if (appType == JobApplicationData.ApplicationType.NOT_EASY_APPLY) {
            MessageDialog.infoBox(MessageDialog.INDEED_NOT_EASY_APPLY_MSG, MessageDialog.SUCCESSFUL_LAUNCH_MSG);
            IndeedApplyBot notEa = new IndeedApplyBot(jobAppData, appType);
            notEa.navigateToJobPage();
            notEa.searchJobs();
            jobIterator.loopThroughJob(notEa.tryToFindElements(_INDEED_JOB_CARD),
                    (int index, List<WebElement> jobList) -> {

                        notEa.saveNonEasyApplyJobs(index,
                                notEa.tryToFindElements(_INDEED_JOB_CARD));
                    }, (int pageNum) -> page.goToNextIndeedPage(pageNum));
            MessageDialog.infoBox(MessageDialog.SUCCESS_JOB_SAVE_MSG, MessageDialog.SUCCESS_MSG);
        }
    }
}
