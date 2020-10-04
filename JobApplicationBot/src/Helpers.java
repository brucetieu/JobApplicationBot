
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class has helper methods which help with other functions in IndeedBot.
 * 
 * @author bruce
 *
 */
public class Helpers extends Bot {

    private JobPostingData _jobPostingData = new JobPostingData();

    /**
     * This method populates easy apply jobs that haven't been applied by the bot to
     * a CSV.
     * 
     * @param list This is a list of hash maps, and inside the map contains job
     *             description data.
     */

//    public void writeJobsToCSV() {
//        try {
//            FileWriter csvWriter = new FileWriter("jobOutput.csv");
//            csvWriter.append("job_type");
//            csvWriter.append(",");
//            csvWriter.append("submitted");
//            csvWriter.append(",");
//            csvWriter.append("job_link");
//            csvWriter.append(",");
//            csvWriter.append("company_name");
//            csvWriter.append(",");
//            csvWriter.append("date_applied");
//            csvWriter.append(",");
//            csvWriter.append("company_location");
//            csvWriter.append(",");
//            csvWriter.append("remote");
//            csvWriter.append(",");
//            csvWriter.append("job_title");
//            csvWriter.append(",");
//            csvWriter.append("status");
//            csvWriter.append("\n");
//
//            for (HashMap<String, String> map : _jobPostingData.jobPostingContainer) {
//                for (String key : map.keySet()) {
//                    String value = map.get(key);
//                    csvWriter.append(value);
//                    csvWriter.append(",");
//                }
//                csvWriter.append("\n");
//            }
//            csvWriter.flush();
//            csvWriter.close();
//        } catch (IOException e) {
//            System.err.println("Error writing the CSV file: " + e);
//        }
//
//    }

    public void writeJobPostToCSV() throws Exception {
        final String[] header = new String[] { "jobTitle", "companyName", "companyLocation", "remote", "dateApplied",
                "jobType", "jobLink", "submitted", "jobStatus" };
        ICsvMapWriter mapWriter = null;
        
        try {
            mapWriter = new CsvMapWriter(new FileWriter("jobPostOutput.csv"), CsvPreference.STANDARD_PREFERENCE);
            final CellProcessor[] processors = getProcessors();
            
            // Write the header.
            mapWriter.writeHeader(header);
            
            // Write each HashMap in the ArrayList
            for (int i = 0; i < _jobPostingData.jobPostingContainer.size(); i++) {
                mapWriter.write(_jobPostingData.jobPostingContainer.get(i), header, processors);
            }
        } finally {
            if (mapWriter != null) {
                mapWriter.close();
            }
        }

    }

    private static CellProcessor[] getProcessors() {
        final CellProcessor[] processors = new CellProcessor[] { 
                new NotNull(), // jobTitle
                new NotNull(), // companyName
                new NotNull(), // companyLocation
                new NotNull(), // remote
                new FmtDate("MM/dd/yyyy"), // dateApplied
                new NotNull(), // mailingAddress
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
     * @return This returns a hash map of job information.
     */
    public LinkedHashMap<String, String> getJobInformation(String jobLink, JobApplicationData.ApplicationType appType,
            boolean applied) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String timeStamp = new SimpleDateFormat("MMddyyy").format(Calendar.getInstance().getTime());

        WebElement jobTitle = tryToFindElement(By.className("jobsearch-JobInfoHeader-title"));
        _jobPostingData.job_title = jobTitle.getText();
        WebElement companyLocationDiv = getDriver()
                .findElement(By.className("jobsearch-DesktopStickyContainer-subtitle"));
        List<WebElement> nestedDiv = companyLocationDiv.findElements(By.tagName("div"));
        List<WebElement> innerDivs = nestedDiv.get(0).findElements(By.tagName("div"));

//        String remote;
//        String submitted;
        _jobPostingData.companyName = innerDivs.get(0).getText();
        _jobPostingData.companyLoc = innerDivs.get(2).getText();
        String isRemote = nestedDiv.get(1).getText();

        if (isRemote != null)
            _jobPostingData.remote = "yes";
        else
            _jobPostingData.remote = "no";

        if (applied)
            _jobPostingData.submitted = "no";
        else
            _jobPostingData.submitted = "yes";

        map.put("job_title", _jobPostingData.job_title);
        map.put("company_name", _jobPostingData.companyName);
        map.put("company_location", _jobPostingData.companyLoc);
        map.put("remote", _jobPostingData.remote);
        map.put("date_applied", timeStamp);
        map.put("job_type", appType.name());
        map.put("job_link", jobLink);
        map.put("submitted", _jobPostingData.submitted);
        map.put("status", "");

        return map;
    }

}
