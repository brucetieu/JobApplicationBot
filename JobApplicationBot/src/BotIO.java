import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * This class adds additional functionally to the Bot by giving it writing abilities.
 * 
 * @author bruce
 *
 */
public class BotIO extends Bot {

    private JobPostingData _jobPostingData = new JobPostingData();

    /**
     * This method writes the job posting information to a CSV so the applicant can
     * keep track of easy apply jobs.
     * 
     * @throws Exception
     */
    public void writeJobPostToCSV(JobPostingData jobPostingData) throws Exception {
        System.out.println("Inside the writeJobPostToCSV()");
        final String[] header = new String[] { "jobTitle", "companyName", "companyLocation", "remote", "dateApplied",
                "jobType", "jobLink", "submitted", "jobStatus" };
        ICsvMapWriter mapWriter = null;

        try {
            mapWriter = new CsvMapWriter(new FileWriter("jobPostOutput.csv"), CsvPreference.STANDARD_PREFERENCE);
            final CellProcessor[] processors = getProcessors();

            // Write the header.
            mapWriter.writeHeader(header);

            // Write each HashMap in the ArrayList
            for (int i = 0; i < jobPostingData.jobPostingContainer.size(); i++) {
                System.out.println(jobPostingData.jobPostingContainer.get(i));
                mapWriter.write(jobPostingData.jobPostingContainer.get(i), header, processors);
            }
        } finally {
            if (mapWriter != null) {
                mapWriter.close();
            }
        }

    }

    /**
     * Sets up the processors. There are 10 columns in the CSV, so 10 processors are
     * defined.
     * 
     * @return The cell processors.
     */
    private static CellProcessor[] getProcessors() {
        final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // jobTitle
                new NotNull(), // companyName
                new NotNull(), // companyLocation
                new NotNull(), // remote
                new NotNull(), // dateApplied
                new NotNull(), // jobType
                new NotNull(), // jobLink
                new NotNull(), // submitted
                new NotNull() // jobStatus
        };

        return processors;
    }

    /**
     * This method gets information from the job description like job title,
     * company, etc.
     * 
     * @param driver  This is the web driver.
     * @param wait    This is the wait element.
     * @param jobLink This is the link of the job of type string.
     * @param appType This is the application type of type string.
     * @param applied This is bool indicating whether or not the job has already
     *                been applied to.
     * @return This returns a hash map of job posting information.
     */
    public LinkedHashMap<String, String> getJobInformation(String jobLink, JobApplicationData.ApplicationType appType,
            boolean applied) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        WebElement jobTitle = tryToFindElement(By.className("jobsearch-JobInfoHeader-title"));
        _jobPostingData.job_title = jobTitle.getText();
        WebElement companyLocationDiv = getDriver()
                .findElement(By.className("jobsearch-DesktopStickyContainer-subtitle"));
        List<WebElement> nestedDiv = companyLocationDiv.findElements(By.tagName("div"));
        List<WebElement> innerDivs = nestedDiv.get(0).findElements(By.tagName("div"));

        _jobPostingData.companyName = innerDivs.get(0).getText();
        _jobPostingData.companyLoc = innerDivs.get(innerDivs.size() - 1).getText();
        String isRemote = nestedDiv.get(nestedDiv.size() - 1).getText();

        if (isRemote != null)
            _jobPostingData.remote = "yes";
        else
            _jobPostingData.remote = "no";

        if (applied)
            _jobPostingData.submitted = "no";
        else
            _jobPostingData.submitted = "yes";

        map.put("jobTitle", _jobPostingData.job_title);
        map.put("companyName", _jobPostingData.companyName);
        map.put("companyLocation", _jobPostingData.companyLoc);
        map.put("remote", _jobPostingData.remote);
        map.put("dateApplied", formatter.format(date));
        map.put("jobType", appType.name());
        map.put("jobLink", jobLink);
        map.put("submitted", _jobPostingData.submitted);
        map.put("jobStatus", "");

        return map;
    }

}
