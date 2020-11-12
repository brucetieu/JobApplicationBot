package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Iterator class to iterate through a list of jobs.
 * 
 * @author Bruce Tieu
 *
 */
public class JobIterator {
    private Bot _bot;
    private WriteFiles _writeFiles;
    private JobApplicationData.ApplicationType _appType;

    /**
     * Parameterized constructor which initializes a Bot and WriteFiles object.
     * 
     * @param writeFiles
     */
    public JobIterator(WriteFiles writeFiles, JobApplicationData.ApplicationType appType) {
        _bot = new Bot();
        _writeFiles = writeFiles;
        _appType = appType;
    }

    /**
     * Loop through the job list which is passed in.
     * 
     * @param jobList         The list of jobs on the page.
     * @param applyInterface  Methods to apply to different types of jobs.
     * @param pagingInterface Methods for pagination.
     * @throws IOException Catch file errors.
     */
    public void loopThroughJob(List<WebElement> jobList, ApplyInterface applicationHandler,
            PagingInterface paginationHandler) throws IOException {

        int currPageNum = 0;
        List<WebElement> tempList = jobList;
        int numOfJobs = tempList.size();

        // Loop through each of the job divs present on the page.
        int i = 0;

        while (i < numOfJobs) {
            try {

                boolean atLastCard = (i == tempList.size() - 1);
                boolean atLastPage = (currPageNum == JobPostingData.pagesToScrape);

                // Call the specific apply method here.
                applicationHandler.handleJob(i, tempList);

                // Stop at the last job listing & pagenum specified.
                if (atLastCard && atLastPage)
                    break;
                // Go to the next page to continue saving jobs.
                if (atLastCard) {
                    i = -1;
                    currPageNum += 1;

                    // Update the job list when specific paging function is called.
                    tempList = paginationHandler.handlePage(currPageNum);
                }
                i++;
            } catch (NoSuchElementException e) {
                // If error, go to next index.
                System.out.println(e.getMessage());
                continue;
            }
        }

        // Write all jobs to excel file.
        if (_appType != JobApplicationData.ApplicationType.LEVER_GREENHOUSE) {
        _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
        System.out.println("Finished export");
        _bot.quitBrowser();
        }

    }

}
