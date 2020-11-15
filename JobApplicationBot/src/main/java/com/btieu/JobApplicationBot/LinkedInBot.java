package com.btieu.JobApplicationBot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class LinkedInBot extends Bot {

    private JobApplicationData _jobAppData;
    private LinkedInPerson _linkedInPerson;

    private List<String> _visitedProfiles;
    private Queue<LinkedInPerson> _profilesToBeVisited;


    public LinkedInBot(JobApplicationData jobAppData, LinkedInPerson linkedInPerson) {
        _jobAppData = jobAppData;
        _linkedInPerson = linkedInPerson;
        _visitedProfiles = new ArrayList<String>();
        _profilesToBeVisited = new LinkedList<LinkedInPerson>();
    }

    /**
     * Navigate to the job platform site.
     */
    public void navigateToJobPage() {
        getWebDriver().get(_jobAppData.platformUrl);
    }

    /**
     * This method logs in to the job site.
     * 
     * @throws InterruptedException
     */
    public void login() throws InterruptedException {

        // Make sure the Email and Password fields are cleared out of any text.
        getWebDriver().findElement(By.id("username")).clear();
        getWebDriver().findElement(By.id("password")).clear();

        // Populate the fields with an email and a password
        WebElement email = getWebDriver().findElement(By.id("username"));
        WebElement password = getWebDriver().findElement(By.id("password"));
        typeLikeAHuman(email, this._jobAppData.email);
        typeLikeAHuman(password, this._jobAppData.password);

        waitOnElementAndClick(By.className("btn__primary--large"));
    }

    public void goToProfile() {
        getWebDriver().get(_jobAppData.linkedin);
    }

  

}
