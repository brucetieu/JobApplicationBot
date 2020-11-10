package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;

public class JobIterator {
    private Bot _bot;
    private WriteFiles _writeFiles;
    
    public JobIterator(WriteFiles writeFiles) {
        _bot = new Bot();
        _writeFiles = writeFiles;
    }
    
    
    public void loopThroughJob(List<WebElement> genericList, ApplyInterface applyInterface,
            PagingInterface pagingInterface) throws IOException {

        int currPageNum = 0;
                List<WebElement> tempList = genericList;
                int numOfJobs = tempList.size();

                // Loop through each of the job divs present on the page.
                int i = 13;
                while (i < numOfJobs) {

                    boolean atLastCard = (i == tempList.size() - 1);
                    boolean atLastPage = (currPageNum == JobPostingData.pagesToScrape);

                    // Call specific method here
                    applyInterface.myMethod(i, tempList);
                    // Stop at the last job listing & pagenum specified.
                    if (atLastCard && atLastPage)
                        break;
                    // Go to the next page to continue saving jobs.
                    if (atLastCard) {
                        i = -1;
                        currPageNum += 1;
                        tempList = pagingInterface.myMethod(currPageNum);
                    }
                    i++;
                }

                // Write all jobs to excel file.
                _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
                _bot.quitBrowser();
            }
    
}
