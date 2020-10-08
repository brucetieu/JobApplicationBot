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
 * This class adds additional functionally to the Bot by getting job description
 * information from each job site.
 * 
 * @author bruce
 *
 */
public class BotScrape extends Bot {

    private JobPostingData _jobPostingData = new JobPostingData();

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
