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

    /**
     * Go to your Linkedin profile, need to access the "People Also Viewed" and "People You May Know" sections.
     */
    public void goToProfile() {
        getWebDriver().get(_jobAppData.linkedin);
    }

    /**
     * Get all the profiles links from the "People Also Viewed" and "People You May Know" sections.
     */
    public void aggregatePeopleProfiles() {
        _getPeopleYouMayKnow();
        _getPeopleViewed();

    }

    /**
     * Connect with profiles you have visited. 
     */
    public void connect() {
        
        // Connection requests counter.
        int connections = 0;
        
        // While the list of profiles to be visited is not empty...
        while (_profilesToBeVisited != null && !_profilesToBeVisited.isEmpty()) {
            
            // Get the first profile in the queue.
            LinkedInPerson queuedProfile = _profilesToBeVisited.poll();
            try {
                
                // If the person you're wanting to connect with has a desired keyword, then connect.
                if (_containsKeywords(queuedProfile.occupation.toLowerCase())) {
                    
                    // Add the profile to the visited list.
                    _visitedProfiles.add(queuedProfile.profileLink);
                    getWebDriver().get(queuedProfile.profileLink);
                    
                    // Keep updating the list of profiles to visit.
                    aggregatePeopleProfiles();

                    // Send connection request.
                    try {
                        _easyConnectRequest(queuedProfile.message);
                    } catch (Exception e) {
                        _hardConnectRequest(queuedProfile.message);
                    }
//                    waitOnElementAndClick(By.className("pv-s-profile-actions--connect")); // click on Connect
//                    waitOnElementAndClick(By.className("artdeco-button--secondary")); // Click on "Add a note"
//                    WebElement textarea = tryToFindElement(By.id("custom-message"));
//                    textarea.sendKeys(queuedProfile.message);

//                    if (isClicked(By.className("ml1"))) {
//                        connections += 1;
//                        System.out.println("Sent invitation!");
//                    }
//
//                    if (connections == LinkedInPerson.MAX_CONNECTIONS)
//                        break;
                }
            } catch (Exception e) {
                
            }
        }

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
        
        for (LinkedInPerson p : _profilesToBeVisited) {
            System.out.println(p.firstname + ", " + p.profileLink + ", " + p.occupation + ", " + p.message);
        }
    }

    private void _getPeopleYouMayKnow() {
        WebElement pymkContainer = tryToFindElement(By.className("pv-profile-pymk__container"));
        List<WebElement> pymkList = pymkContainer.findElements(By.className("pv-pymk-section__member-container"));

        for (WebElement people : pymkList) {
            String profileLink = people.findElement(By.tagName("a")).getAttribute("href");
            String name = people.findElement(By.className("name")).getText();
            String occupation = people.findElement(By.className("pv-pymk-section__member-headline")).getText();
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

    private boolean _containsKeywords(String description) {

        String[] splittedKeywords = _linkedInPerson.keywords.toLowerCase().split("\\s*,\\s*");

        for (String x : splittedKeywords) System.out.println(x);
        
        for (int i = 0; i < splittedKeywords.length; i++) {
            if (description.contains(splittedKeywords[i])) {
                return true;
            }
        }
        return false;
    }
    
    private void _easyConnectRequest(String message) {
        getWebDriver().findElement(By.className("pv-s-profile-actions--connect")).click(); // click on Connect
        getWebDriver().findElement(By.className("artdeco-button--secondary")).click(); // Click on "Add a note"
        WebElement textarea = tryToFindElement(By.id("custom-message"));
        textarea.sendKeys(message);
    }
    
    private void _hardConnectRequest(String message) {
        WebElement more = getWebDriver().findElement(By.className("pv-s-profile-actions__overflow"));
        more.click();
        more.findElement(By.className("pv-s-profile-actions--connect")).click();
        waitOnElementAndClick(By.className("artdeco-button--secondary")); // Click on "Add a note"
        WebElement textarea = tryToFindElement(By.id("custom-message"));
        textarea.sendKeys(message);
    }
    
    
    

}
