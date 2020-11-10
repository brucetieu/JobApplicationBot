package com.btieu.JobApplicationBot;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@FunctionalInterface
public interface PagingInterface {
    /**
     * Abstract method that is implemented by a lamda expression.
     * 
     * @param pageNum The page number to search for jobs.
     * @return A list of jobs of type WebElement.
     */
    public List<WebElement> myMethod(int pageNum);
}

/**
 * Define methods to click on next pages and implement this interface with a
 * lamda expression.
 * 
 * @author Bruce Tieu
 *
 */
class Page {
    private JobApplicationData _jobAppData;
    private Bot _bot;

    /**
     * Parameterized constructor to initialize JobApplicationData and Bot object.
     * 
     * @param jobAppData The JobApplicationData.
     */
    public Page(JobApplicationData jobAppData) {
        _jobAppData = jobAppData;
        _bot = new Bot();
    }

    /**
     * Go to next page listing in Indeed.
     * 
     * @param pageNum The desired number of page numbers to search.
     * @return An updated list of jobs to be iterated over again.
     */
    public List<WebElement> goToNextIndeedPage(int pageNum) {

        String nextPageUrl = "https://www.indeed.com/jobs?q=" + _jobAppData.whatJob + "&l=" + _jobAppData.locationOfJob
                + "&start=" + pageNum * 10;
        System.out.println("Continuing search on next page...");
        _bot.getWebDriver().get(nextPageUrl);

        // Click out of potential pop ups.
        _bot.getActions().moveByOffset(0, 0).click().build().perform();
        _bot.getWebDriver().switchTo().defaultContent();

        return _bot.tryToFindElements(By.className("jobsearch-SerpJobCard"));
    }
}
