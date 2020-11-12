package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Define methods to click on next pages and implement this interface with a
 * lambda expression.
 * 
 * @author Bruce Tieu
 *
 */
class Pagination {
    private JobApplicationData _jobAppData;
    private Bot _bot;

    /**
     * Parameterized constructor to initialize JobApplicationData and Bot object.
     * 
     * @param jobAppData The JobApplicationData.
     */
    public Pagination(JobApplicationData jobAppData) {
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

    /**
     * Click on the next page on Glassdoor.
     * 
     * @param pageNum The page number to go to.
     * @return An updated list of jobs to be iterated over again.
     */
    public List<WebElement> goToNextGlassdoorPage(int pageNum) {

        String pageUrl = _bot.getRequestURL(_bot.getWebDriver().getCurrentUrl());
        String newPageNum = "_IP" + Integer.toString(pageNum) + ".htm";
        String newPageUrl = pageUrl.replace(".htm", newPageNum);
        System.out.println("Continuing search on next page...");
        _bot.getWebDriver().get(newPageUrl);
        return _bot.tryToFindElements(By.className("react-job-listing"));

    }

}
