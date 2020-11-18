package net.codejava.swing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.IndeedApplyBot;
import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.JobIterator;
import com.btieu.JobApplicationBot.Pagination;

/**
 * Run the IndeedBot
 * 
 * @author Bruce Tieu
 *
 */
public class RunIndeedBot {

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
            IndeedApplyBot easyApp = new IndeedApplyBot(jobAppData, appType);
            easyApp.navigateToJobPage();
            easyApp.login();
            easyApp.searchJobs();
            jobIterator.loopThroughJob(easyApp.tryToFindElements(By.className("jobsearch-SerpJobCard")),
                    (int index, List<WebElement> jobList) -> {

                        easyApp.saveEasyApplyJobs(index,
                                easyApp.tryToFindElements(By.className("jobsearch-SerpJobCard")));
                    }, (int pageNum) -> page.goToNextIndeedPage(pageNum));

            // Handle all applications.
        } else if (appType == JobApplicationData.ApplicationType.ALL) {
            IndeedApplyBot greedy = new IndeedApplyBot(jobAppData, appType);
            greedy.navigateToJobPage();
            greedy.searchJobs();
            jobIterator.loopThroughJob(greedy.tryToFindElements(By.className("jobsearch-SerpJobCard")),
                    (int index, List<WebElement> jobList) -> {

                        greedy.saveAllJobs(index, greedy.tryToFindElements(By.className("jobsearch-SerpJobCard")));
                    }, (int pageNum) -> page.goToNextIndeedPage(pageNum));

            // Handle not easy apply apps.
        } else if (appType == JobApplicationData.ApplicationType.NOT_EASY_APPLY) {
            IndeedApplyBot notEa = new IndeedApplyBot(jobAppData, appType);
            notEa.navigateToJobPage();
            notEa.searchJobs();
            jobIterator.loopThroughJob(notEa.tryToFindElements(By.className("jobsearch-SerpJobCard")),
                    (int index, List<WebElement> jobList) -> {

                        notEa.saveNonEasyApplyJobs(index,
                                notEa.tryToFindElements(By.className("jobsearch-SerpJobCard")));
                    }, (int pageNum) -> page.goToNextIndeedPage(pageNum));
        }
        // Don't do anything with the lever greenhouse ones.
        else
            return;
    }
}
