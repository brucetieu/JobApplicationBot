package com.btieu.JobApplicationBot;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * A GDNotEasyApplyBot is a GlassdoorBot.
 * 
 * @author bruce
 *
 */
public class GDNotEasyApplyBot extends GlassdoorBot {

    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private WriteFiles _writeFiles;
    private GreenhouseForms _greenhouseForms;
    private LeverForms _leverForms;

    /**
     * Initialize data and file writing objects.
     * 
     * @param jobAppData The applicant information.
     * @param appType    The type of application.
     * @param writeFiles The object which writes the jobs to a csv.
     */
    public GDNotEasyApplyBot(JobApplicationData jobAppData, ApplicationType appType, WriteFiles writeFiles) {
        super(jobAppData, appType);
        _jobAppData = jobAppData;
        _appType = appType;
        _writeFiles = writeFiles;
        _greenhouseForms = new GreenhouseForms();
        _leverForms = new LeverForms();
    }

    /**
     * Aggregate jobs that are not easily apply ones.
     * 
     * @throws InterruptedException
     * @throws IOException
     */
    public void skipEasyApply() throws InterruptedException, IOException {
        int currPageNum = 1;
        int numOfJobs = jobsCard.size();
        System.out.println(numOfJobs);
        int i = 0;

        try {
            while (i < numOfJobs) {
                System.out.println(i);
                boolean atLastCard = (i == jobsCard.size() - 1);
                boolean atLastPage = (currPageNum == JobPostingData.pagesToScrape);
                boolean isEasyApply = (jobsCard.get(i).findElements(By.className("jobLabel")).size() > 0);

                if (!isEasyApply) {
                    String jobLink = getJobViewLink(i);
                    jobLink = jobLink.replaceAll("GD_JOB_AD", "GD_JOB_VIEW");
                    System.out.println(getRequestURL(jobLink));
                    saveJob(getRequestURL(jobLink), _appType);
                }

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

        } catch (Exception e) {}

    }

    /**
     * Apply to Lever and Greenhouse jobs
     * @throws IOException
     */
    public void massApply() throws IOException {

        for (int i = 0; i < JobPostingData.jobPostingContainer.size(); i++) {
            try {
                if (JobPostingData.jobPostingContainer.get(i).jobLink.contains("lever")) {
                    System.out.println("Applying to lever job...");
                    applyToLeverJobs(JobPostingData.jobPostingContainer.get(i).jobLink);
                    if (getWebDriver().findElements(By.xpath("//h3[@data-qa='msg-submit-success']")).size() > 0) {
                        System.out.println("Successfully applied");
                        JobPostingData.jobPostingContainer.get(i).submitted = "Yes";
                    }
                } else if (JobPostingData.jobPostingContainer.get(i).jobLink.contains("greenhouse")) {
                    System.out.println("Applying to greenhouse job...");
                    applyToGreenhouseJobs(JobPostingData.jobPostingContainer.get(i).jobLink);
                    if (tryToFindElements(By.id("application_confirmation")).size() > 0) {
                        System.out.println("Successfully applied");
                        JobPostingData.jobPostingContainer.get(i).submitted = "Yes";
                    }
                }
            } catch (Exception e) {
                continue;
//                _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
            }
        }
            _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
//        } catch (Exception e) {
//            _writeFiles.writeJobPostToCSV(JobPostingData.jobPostingContainer);
//        }
    }

    public void applyToLeverJobs(String leverLink) {
        getWebDriver().get(leverLink);
        waitOnElementAndClick(By.className("template-btn-submit"));

        _leverForms.fillAllBasicInfo(_jobAppData);
        _leverForms.fillAllWorkAuth(_jobAppData);
        _leverForms.fillAllHowDidYouFindUs();
        _leverForms.uploadResume();
        _leverForms.submitApplication();

    }

    public void applyToGreenhouseJobs(String greenhouseLink) throws IOException {
        getWebDriver().get(greenhouseLink);
        waitOnElementAndClick(By.className("template-btn-submit"));

        _greenhouseForms.fillAllBasicInfo(_jobAppData);
        _greenhouseForms.fillAllWorkAuth(_jobAppData);
        _greenhouseForms.fillAllHowDidYouFindUs();
        _greenhouseForms.approveConsent();
        _greenhouseForms.uploadResume();
        _greenhouseForms.submitApplication();

    }

//    public static void main(String[] args) throws IOException {
//        JobApplicationData jobAppData = new JobApplicationData();
//        WriteFiles writeFiles = new WriteFiles("jobPostingOutput.csv");
//        JobApplicationData.ApplicationType appType = JobApplicationData.ApplicationType.ALL;
////        GDNotEasyApplyBot gdNotEZApplyBot = new GDNotEasyApplyBot(jobAppData, appType, writeFiles);
////        gdNotEZApplyBot.applyToGreenhouseJobs("https://boards.greenhouse.io/singlestore/jobs/2328586?gh_src=a607b2971us");
//        GDNotEasyApplyBot gdNotEZApplyBot = new GDNotEasyApplyBot(jobAppData, appType, writeFiles);
//        gdNotEZApplyBot.applyToLeverJobs("https://jobs.lever.co/pieinsurance/5f8e1fe8-4545-488c-8b78-b9bdf682f651/apply?lever-source=Glassdoor");
////        gdNotEZApplyBot.applyToGreenhouseJobs("https://boards.greenhouse.io/checkr/jobs/2389884?gh_jid=2389884&gh_src=332b8c221us");
//    }

}
