package com.btieu.JobApplicationBot;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * A GreedyIndeedBot is an IndeedBot except it can apply to all possible jobs
 * given on a page.
 * 
 * @author Bruce Tieu
 *
 */
public class GreedyIndeedBot extends IndeedBot {

    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private WriteFiles _writeFiles;

    /**
     * This is a class constructor which initializes job application data, job
     * application type, and writing of files.
     * 
     * @param jobAppData The object which holds job application data.
     * @param appType    The enum type which is a set of application types.
     * @param writeFiles Class for writing files.
     */
    public GreedyIndeedBot(JobApplicationData jobAppData, ApplicationType appType, WriteFiles writeFiles) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;
        _writeFiles = writeFiles;

    }

    /**
     * Aggregate all jobs in a page.
     * 
     * @throws Exception
     */
    public void findAllJobs() throws Exception {

        JobElementIterator itr = new JobElementIterator(jobsCard);
        int currPageNum = 0;

        // Loop through each of the job divs present on the page.
        try {
            while (itr.hasNext()) {

                boolean atLastCard = (itr.getCurrIndex() == jobsCard.size() - 1);
                boolean atLastPage = (currPageNum == JobPostingData.pagesToScrape);

                String jobLink = getJobViewLink(itr.getCurrIndex());

                // Get link to application on company site, not indeed.
                jobLink = jobLink.replace("viewjob", "rc/clk");
                jobLink = jobLink.replace("vjs", "assa");
                saveJob(jobLink, _appType);

                // Stop at the last job listing & pagenum specified.
                if (atLastCard && atLastPage)
                    break;

                // Go to the next page to continue saving jobs.
                if (atLastCard) {
                    itr.setCurrIndex(0);
                    currPageNum += 1;
                    goToNextPage(currPageNum);
                    itr = new JobElementIterator(jobsCard); // Create new iterator instance.
                }
                itr.getNext();
            }
        } catch (Exception e) {
            // If there's an error while looping, write current jobs saved to the csv.
            _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
            quitBrowser();
        }

        // Write all jobs to excel file.
        _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
        quitBrowser();
    }
}