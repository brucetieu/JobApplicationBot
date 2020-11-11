package com.btieu.JobApplicationBot;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class LinkedInBot extends Bot {

    private JobApplicationData _jobAppData;

    public LinkedInBot(JobApplicationData jobAppData) {
        _jobAppData = jobAppData;

    }

    /**
     * Navigate to the Indeed site.
     */
    public void navigateToJobPage() {
        getDriver().get(_jobAppData.platformUrl);
    }

    /**
     * This method logs in to the job site.
     * 
     * @throws InterruptedException
     */
    public void login() throws InterruptedException {

        // Make sure the Email and Password fields are cleared out of any text.
        getDriver().findElement(By.id("username")).clear();
        getDriver().findElement(By.id("password")).clear();

        // Populate the fields with an email and a password
        WebElement email = getDriver().findElement(By.id("username"));
        WebElement password = getDriver().findElement(By.id("password"));
        typeLikeAHuman(email, this._jobAppData.email);
        typeLikeAHuman(password, this._jobAppData.password);

        waitOnElementAndClick(By.className("btn__primary--large"));
    }

    public void search() {
        WebElement search = tryToFindElement(By.className("search-global-typeahead__input"));
        search.sendKeys("Software engineer");
        search.sendKeys(Keys.RETURN);

        // Click on "People" button.
        waitOnElementAndClick(By.className("search-vertical-filter__filter-item"));
      
    }

}
