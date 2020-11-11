package com.btieu.JobApplicationBot;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class LinkedInBot extends Bot {

    private JobApplicationData _jobAppData;
    private List<String> _peopleContainer;

    public LinkedInBot(JobApplicationData jobAppData) {
        _jobAppData = jobAppData;
        _peopleContainer = new ArrayList<String>();

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

    public void search() throws InterruptedException {
        String peoplePage = "https://www.linkedin.com/search/results/people/?keywords=" + "Software engineer";
        getDriver().get(peoplePage);
    }

    public void aggregatePeopleProfiles() throws InterruptedException {
        // Click on "People" button.
//        waitOnElementAndClick(By.className("search-vertical-filter__filter-item"));

        int i = 1;

        while (i <= JobPostingData.pageNum) {

            i++;
            System.out.println(i);

            WebElement html = getDriver().findElement(By.tagName("html"));
            html.sendKeys(Keys.END);

            WebElement ul = tryToFindElement(By.className("search-results__list"));
            List<WebElement> peopleList = ul.findElements(By.tagName("li"));

            for (WebElement people : peopleList) {
                String profileID = people.findElement(By.tagName("a")).getAttribute("href");
                _peopleContainer.add(profileID);
                System.out.println(profileID);
            }

            String peoplePage = "https://www.linkedin.com/search/results/people/?keywords=" + "computer science student"
                    + "&page=" + Integer.toString(i);
            getDriver().get(peoplePage);
        }

    }

    public void connect() {
        for (String person : _peopleContainer) {
            try {
                getDriver().get(person);
                waitOnElementAndClick(By.className("pv-s-profile-actions--connect")); // click on Connect
                waitOnElementAndClick(By.className("artdeco-button--secondary")); // Click on "Add a note"
                WebElement textarea = tryToFindElement(By.id("custom-message"));
                textarea.sendKeys(
                        "Hello, I came across your profile and wanted to connect with people who I aspire to be and share common career interests with - a Software Engineer! Kindly, accept my invitation. Thanks!");
                waitOnElementAndClick(By.className("ml1")); // Click on Send"
                System.out.println("Sent invitation!");
            } catch (Exception e) {
                continue;
            }

        }
    }

}
