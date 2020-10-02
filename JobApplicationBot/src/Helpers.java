
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class has helper methods which help with other functions in IndeedBot.
 * 
 * @author bruce
 *
 */
public class Helpers {
    /**
     * This method tries to find certain elements in the easy apply applications.
     * 
     * @param wait This parameter is need for waiting for elements to appear.
     */
    public void tryFindElement(WebDriverWait wait) {

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("textarea"))).sendKeys("Monday");
        } catch (Exception e) {
            System.out.println("Textarea box not found");
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form-action-continue"))).click();
        } catch (Exception e) {
            System.out.println("Continue button not found");
        }
        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("ia-InterventionActionButtons-buttonDesktop")))
                    .click();
        } catch (Exception e) {
            System.out.println("Continue Applying button not found");
        }
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form-action-submit"))).click();
        } catch (Exception e) {
            System.out.println("Apply button not found");
        }
    }

    /**
     * This method populates easy apply jobs that haven't been applied by the bot to
     * a CSV.
     * 
     * @param list This is a list of hash maps.
     */
    public void readJobsToCSV(ArrayList<HashMap<String, String>> list) {
        try {
            FileWriter csvWriter = new FileWriter("jobOutput.csv");
            csvWriter.append("job_type");
            csvWriter.append(",");
            csvWriter.append("submitted");
            csvWriter.append(",");
            csvWriter.append("job_link");
            csvWriter.append(",");
            csvWriter.append("company_name");
            csvWriter.append(",");
            csvWriter.append("date_applied");
            csvWriter.append(",");
            csvWriter.append("company_location");
            csvWriter.append(",");
            csvWriter.append("remote");
            csvWriter.append(",");
            csvWriter.append("job_title");
            csvWriter.append(",");
            csvWriter.append("status");
            csvWriter.append("\n");

            for (HashMap<String, String> map : list) {
                for (String key : map.keySet()) {
                    String value = map.get(key);
                    csvWriter.append(value);
                    csvWriter.append(",");
                }
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            System.err.println("Error writing the CSV file: " + e);
        }

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
    public HashMap<String, String> getJobInformation(WebDriver driver, WebDriverWait wait, String jobLink,
            JobApplicationData.ApplicationType appType, boolean applied) {

        HashMap<String, String> map = new HashMap<>();
        String timeStamp = new SimpleDateFormat("MMddyyy").format(Calendar.getInstance().getTime());

        WebElement jobTitle = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("jobsearch-JobInfoHeader-title")));
        String job_title = jobTitle.getText();
        WebElement companyLocationDiv = driver.findElement(By.className("jobsearch-DesktopStickyContainer-subtitle"));
        List<WebElement> nestedDiv = companyLocationDiv.findElements(By.tagName("div"));
        List<WebElement> innerDivs = nestedDiv.get(0).findElements(By.tagName("div"));

        String remote;
        String submitted;
        String companyName = innerDivs.get(0).getText();
        String companyLoc = innerDivs.get(2).getText();
        String isRemote = nestedDiv.get(1).getText();

        if (isRemote != null)
            remote = "yes";
        else
            remote = "no";

        if (applied)
            submitted = "no";
        else
            submitted = "yes";

        map.put("job_title", job_title);
        map.put("company_name", companyName);
        map.put("company_location", companyLoc);
        map.put("remote", remote);
        map.put("date_applied", timeStamp);
        map.put("job_type", appType.name());
        map.put("job_link", jobLink);
        map.put("submitted", submitted);
        map.put("status", "");

        return map;
    }

}
