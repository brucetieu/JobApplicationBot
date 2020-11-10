package com.btieu.JobApplicationBot;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface PagingInterface {
    public List<WebElement> myMethod(int pageNum);
}

class Page {
    private JobApplicationData _jobAppData;
    private Bot _bot;
    
    public Page(JobApplicationData jobAppData) {
        _jobAppData = jobAppData;
        _bot = new Bot();
    }
    public List<WebElement> goToNextIndeedPage(int pageNum) {
        String nextPageUrl = "https://www.indeed.com/jobs?q=" + this._jobAppData.whatJob + "&l="
                + this._jobAppData.locationOfJob + "&start=" + pageNum * 10;
        System.out.println("Continuing search on next page...");
        _bot.getWebDriver().get(nextPageUrl);
        _bot.getActions().moveByOffset(0, 0).click().build().perform();
        _bot.getWebDriver().switchTo().defaultContent();

        return _bot.tryToFindElements(By.className("jobsearch-SerpJobCard"));
    }
}
