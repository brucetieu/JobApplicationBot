package com.btieu.JobApplicationBot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Linkedin connections bot. Connect with the people based off the "People You
 * May Know" or "People Who Also Viewed" sections in LinkedIn. Send a custom
 * message to each person to connect. Can connect on specific keywords.
 * 
 * @author Bruce Tieu
 *
 */
public class LinkedInBot extends Bot {

    private JobApplicationData _jobAppData;
    private LinkedInPerson _linkedInPerson;

    private List<String> _visitedProfiles;
    private Queue<LinkedInPerson> _profilesToBeVisited;
    
    private static final String _CLASS_PYMK_SECTION = "pv-profile-pymk__container";
    private static final String _CLASS_PV_SECTION = "pv-browsemap-section";
    private static final String _CLASS_PYMK_MEMBERS = "pv-pymk-section__member-container";
    private static final String _CLASS_PV_MEMBERS = "pv-browsemap-section__member-container";
    private static final String _CLASS_PYMK_HEADLINE = "pv-pymk-section__member-headline";
    private static final String _CLASS_PV_HEADLINE = "pv-browsemap-section__member-headline";
    private static final String _CLASS_CONNECT_BTN = "pv-s-profile-actions--connect";
    private static final String _CLASS_ADD_NOTE_BTN = "artdeco-button--secondary";
    private static final String _ID_CUSTOM_MSG = "custom-message";
    private static final String _CLASS_MORE_BTN = "pv-s-profile-actions__overflow";

    /**
     * Parameterized constructor to initialize the job application data and
     * LinkedInPerson objects.
     * 
     * @param jobAppData     The job application data object.
     * @param linkedInPerson The LinkedInPerson object.
     */
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
    public void login() {

        // Make sure the Email and Password fields are cleared out of any text.
        getWebDriver().findElement(By.id("username")).clear();
        getWebDriver().findElement(By.id("password")).clear();

        // Populate the fields with an email and a password
        tryToFindElementAndSendKeys(By.id("username"), _jobAppData.email);
        tryToFindElementAndSendKeys(By.id("password"), _jobAppData.password);

        waitOnElementAndClick(By.className("btn__primary--large"));
    }

    /**
     * Go to your Linkedin profile, need to access the "People Also Viewed" and
     * "People You May Know" sections.
     */
    public void goToProfile() {
        getWebDriver().get(_jobAppData.linkedin);
    }

    /**
     * Get all the profiles links from the "People Also Viewed" and "People You May
     * Know" sections.
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

                // Connect with people with the keywords you're looking for.
                if (_containsKeywords(queuedProfile.occupation.toLowerCase())) {

                    // Add the profile to the visited list.
                    getWebDriver().get(queuedProfile.profileLink);
                    _visitedProfiles.add(queuedProfile.profileLink);

                    // Keep updating the list of profiles to visit.
                    if (elementExists(By.className(_CLASS_PYMK_SECTION))
                            && elementExists(By.className(_CLASS_PV_SECTION))) {
                        aggregatePeopleProfiles();
                    }

                    // Write connection message.
                    try {
                        _easyConnectRequest(queuedProfile.message);
                    } catch (Exception e) {
                        _hardConnectRequest(queuedProfile.message);
                    }

                    // Send message.
                    if (isClicked(By.className("ml1"))) {
                        connections += 1;
                        System.out.println("Sent invitation!");
                    }

                    // Stop after connecting with x number of people.
                    if (connections == LinkedInPerson.MAX_CONNECTIONS)
                        break;
                }

            } catch (Exception e) {
                System.out.println("Some error");
            }

        }

    }

    /**
     * Get the list of people under "People Also Viewed Section"
     */
    private void _getPeopleViewed() {

        WebElement pvContainer = tryToFindElement(By.className(_CLASS_PV_SECTION));
        List<WebElement> pvList = pvContainer.findElements(By.className(_CLASS_PV_MEMBERS));

        _addToProfilesToBeVisited(By.className(_CLASS_PV_HEADLINE), pvList);

        for (LinkedInPerson p : _profilesToBeVisited) {
            System.out.println(p.firstname + ", " + p.profileLink + ", " + p.occupation + ", " + p.message);
        }
    }

    /**
     * Get list of people under "People Who You May Know".
     */
    private void _getPeopleYouMayKnow() {
        WebElement pymkContainer = tryToFindElement(By.className(_CLASS_PYMK_SECTION));
        List<WebElement> pymkList = pymkContainer.findElements(By.className(_CLASS_PYMK_MEMBERS));

        _addToProfilesToBeVisited(By.className(_CLASS_PYMK_HEADLINE), pymkList);

    }

    /**
     * Fill the profiles to be visited.
     * 
     * @param by         The element which contains the people occupations.
     * @param peopleList The list of people from a specific section.
     */
    private void _addToProfilesToBeVisited(By by, List<WebElement> peopleList) {

        for (WebElement people : peopleList) {
            String profileLink = people.findElement(By.tagName("a")).getAttribute("href");
            String name = people.findElement(By.className("name")).getText();
            String occupation = people.findElement(by).getText();

            // Only add to profilesToBeVisited if it the profile isn't already in the list
            // and it hasn't been visited.
            if (!_profilesToBeVisited.stream().anyMatch(l -> l.profileLink.equals(profileLink))
                    && !_visitedProfiles.contains(profileLink)) {
                _profilesToBeVisited.add(new LinkedInPerson(_splitFullname(name), profileLink, occupation,
                        _assembleMessage(_splitFullname(name))));
            }
        }
    }

    /**
     * Get first name and capitalize it.
     * 
     * @param name A person's full name.
     * @return The first name.
     */
    private String _splitFullname(String name) {
        String[] splitted = name.toLowerCase().split("\\s+");
        String firstName = Character.toUpperCase(splitted[0].charAt(0)) + splitted[0].substring(1);
        return firstName;

    }

    /**
     * Assemble personalized message.
     * 
     * @param name The first name of the person.
     * @return The message.
     */
    private String _assembleMessage(String name) {
        String message = "Hi " + name + ", " + _linkedInPerson.message + _jobAppData.fullname + ".";
        return message;
    }


    /**
     * Check if their occupation contains the keywords.
     * 
     * @param description Their header description.
     * @return True, if there's a match, false otherwise.
     */
    private boolean _containsKeywords(String description) {

        String[] splittedKeywords = _linkedInPerson.keywords.toLowerCase().split("\\s*,\\s*");

        for (int i = 0; i < splittedKeywords.length; i++) {
            if (description.contains(splittedKeywords[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handle cases where there's a "Connect" button.
     * 
     * @param message The connect message.
     */
    private void _easyConnectRequest(String message) {
        getWebDriver().findElement(By.className(_CLASS_CONNECT_BTN)).click(); // click on Connect
        getWebDriver().findElement(By.className(_CLASS_ADD_NOTE_BTN)).click(); // Click on "Add a note"
        WebElement textarea = tryToFindElement(By.id(_ID_CUSTOM_MSG));
        textarea.sendKeys(message);
    }

    /**
     * Handle cases where you have to do more work to connect.
     * 
     * @param message The connect message.
     */
    private void _hardConnectRequest(String message) {
        WebElement more = getWebDriver().findElement(By.className(_CLASS_MORE_BTN));
        more.click();
        more.findElement(By.className(_CLASS_CONNECT_BTN)).click();
        waitOnElementAndClick(By.className(_CLASS_ADD_NOTE_BTN)); // Click on "Add a note"
        WebElement textarea = tryToFindElement(By.id(_ID_CUSTOM_MSG));
        textarea.sendKeys(message);
    }

}
