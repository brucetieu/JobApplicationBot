package com.btieu.JobApplicationBot;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * A GreedyBot is a IndeedBot except it can apply to all possible jobs given on a listing.
 * 
 * @author Bruce Tieu
 *
 */
public class GreedyBot extends IndeedBot {

    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private WriteFiles _writeFiles;

    /**
     * This is a class constructor which initializes job application data, job
     * application type, and writing of files.
     * 
     * @param jobAppData  The object which holds job application data.
     * @param appType     The enum type which is a set of application types.
     * @param writeFiles  Class for writing files. 
     */
    public GreedyBot(JobApplicationData jobAppData, ApplicationType appType, WriteFiles writeFiles) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;
        _writeFiles = writeFiles;

    }

    public void findAllJobs() throws Exception {

        int currPageNum = 0;
        int numOfJobs = jobsCard.size();

        // Loop through each of the job divs present on the page.
        int i = 0;
        while (i < numOfJobs) {

            boolean atLastCard = (i == jobsCard.size() - 1);
            boolean atLastPage = (currPageNum == JobPostingData.pageNum);

            String jobLink = getJobViewLink(i);
            saveJob(jobLink, _appType);

            // Stop at the last job listing & pagenum specified.
            if (atLastCard && atLastPage)
                break;

            // Go to the next page to continue saving jobs.
            if (atLastCard) {
                i = -1;
                currPageNum += 1;
                goToNextPage(currPageNum);
            }
            i++;
        }

        // Write all jobs to excel file.
        _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
        quitBrowser();
    }
}