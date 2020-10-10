
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 * This class adds additional functionally to the Bot by getting job description
 * information from each job site.
 * 
 * @author bruce
 *
 */
public class BotScrape extends Bot {

    /**
     * This method gets information from the job description like job title,
     * company, etc.
     * 
     * @param driver  This is the web driver.
     * @param jobLink This is the link of the job of type string.
     * @param appType This is the application type of type string.
     * @param applied This is bool indicating whether or not the job has already
     *                been applied to.
     * @return This returns a new JobPostingData object.
     */
    public JobPostingData getJobInformation(String jobLink, JobApplicationData.ApplicationType appType,
            boolean applied) {

        String job_title, companyName, companyLoc, remote, submitted;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        WebElement jobTitle = tryToFindElement(By.className("jobsearch-JobInfoHeader-title"));
        job_title = jobTitle.getText();
        WebElement companyLocationDiv = getDriver()
                .findElement(By.className("jobsearch-DesktopStickyContainer-subtitle"));
        List<WebElement> nestedDiv = companyLocationDiv.findElements(By.tagName("div"));
        List<WebElement> innerDivs = nestedDiv.get(0).findElements(By.tagName("div"));

        companyName = innerDivs.get(0).getText();
        companyLoc = innerDivs.get(innerDivs.size() - 1).getText();
        String isRemote = nestedDiv.get(nestedDiv.size() - 1).getText();

        if (isRemote != null)
            remote = "yes";
        else
            remote = "no";

        if (applied)
            submitted = "no";
        else
            submitted = "yes";
        
        // Return a new JobPostingData object.
        return new JobPostingData(job_title, companyName, companyLoc, remote, formatter.format(date), appType.name(),
                jobLink, submitted, "");
    }

}
