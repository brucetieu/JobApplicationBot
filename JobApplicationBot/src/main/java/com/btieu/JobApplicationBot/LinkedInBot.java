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
    
    private void _getPeopleViewed() {
        WebElement pvContainer = tryToFindElement(By.className("pv-browsemap-section"));
        List<WebElement> pvList = pvContainer.findElements(By.className("pv-browsemap-section__member-container"));

        for (WebElement people : pvList) {
            String profileLink = people.findElement(By.tagName("a")).getAttribute("href");
            String name = people.findElement(By.className("name")).getText();
            String occupation = people.findElement(By.className("pv-browsemap-section__member-headline")).getText();
            if (!_profilesToBeVisited.stream().anyMatch(l -> l.profileLink.equals(profileLink))
                    && !_visitedProfiles.contains(profileLink)) {
                _profilesToBeVisited.add(new LinkedInPerson(_splitFullname(name), profileLink, occupation,
                        _assembleMessage(_splitFullname(name))));
            }
        }
    }


    private String _splitFullname(String name) {
        String[] splitted = name.toLowerCase().split("\\s+");
        String firstName = Character.toUpperCase(splitted[0].charAt(0)) + splitted[0].substring(1);
        return firstName;
        
    }
    
    private String _assembleMessage(String name) {
        String message = "Hi " + name + ", your profile appeared in my search of software engineers. "
                + "I am currently pursuing a career in software engineering and "
                + "it would be great to hear about your journey and experience in the field. "
                + "Kindly, accept my invitation. You would be a big help! Sincerely, " + _jobAppData.fullname + ".";

        return message;
    }



  

}
