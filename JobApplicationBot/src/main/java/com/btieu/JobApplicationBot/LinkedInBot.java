package com.btieu.JobApplicationBot;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class LinkedInBot extends Bot {

    private JobApplicationData _jobAppData;
    private LinkedInPerson _linkedInPerson;
    private List<LinkedInPerson> _peopleContainer;
    public static final int MAX_SEARCH_PAGES = 10;

    public LinkedInBot(JobApplicationData jobAppData, LinkedInPerson linkedInPerson) {
        _jobAppData = jobAppData;
        _peopleContainer = new ArrayList<LinkedInPerson>();
        _linkedInPerson = linkedInPerson;
    }

    /**
     * Navigate to the Indeed site.
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

    public void search(String connectWith) throws InterruptedException {
        String peoplePage = "https://www.linkedin.com/search/results/people/?keywords=" + connectWith;
        getWebDriver().get(peoplePage);
    }

    public void aggregatePeopleProfiles(String occupation) throws InterruptedException {
        // Click on "People" button.
//        waitOnElementAndClick(By.className("search-vertical-filter__filter-item"));

        int i = 1;

        while (i <= MAX_SEARCH_PAGES) {

            i++;
            System.out.println(i);

            
            WebElement html = tryToFindElement(By.tagName("html"));
            html.sendKeys(Keys.END);

            WebElement ul = tryToFindElement(By.className("search-results__list"));
            List<WebElement> peopleList = ul.findElements(By.tagName("li"));

            for (WebElement people : peopleList) {
                String profileID = people.findElement(By.tagName("a")).getAttribute("href");
                String name = people.findElement(By.className("actor-name")).getText();
                _peopleContainer.add(new LinkedInPerson(_splitFullname(name), profileID, _assembleMessage(_splitFullname(name))));
                System.out.println(name + ": " + profileID);
            }

            String peoplePage = "https://www.linkedin.com/search/results/people/?keywords=" + occupation
                    + "&page=" + Integer.toString(i);
            getWebDriver().get(peoplePage);
        }

    }

    public void connect(LinkedInPerson linkedInPerson) {
        int connections = 0;
        for (LinkedInPerson person : _peopleContainer) {
            try {
                getWebDriver().get(person.profilelink);
                waitOnElementAndClick(By.className("pv-s-profile-actions--connect")); // click on Connect
                waitOnElementAndClick(By.className("artdeco-button--secondary")); // Click on "Add a note"
                WebElement textarea = tryToFindElement(By.id("custom-message"));
                
                textarea.sendKeys(person.message);
//                
                if (isClicked(By.className("ml1"))) {
                    connections += 1;
                    System.out.println("Sent invitation!");
                }
                
                if (connections == LinkedInPerson.MAX_CONNECTIONS) break;

            } catch (Exception e) {
                continue;
            }

        }
    }
    
    private String _splitFullname(String name) {
        int index = name.lastIndexOf(' ');
        if (index == -1)
            throw new IllegalArgumentException("Only a single name: " + name);
        String firstName = name.substring(0, index);
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
