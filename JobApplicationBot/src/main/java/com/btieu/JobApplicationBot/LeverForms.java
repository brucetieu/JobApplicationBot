package com.btieu.JobApplicationBot;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

/**
 * Class to handle questions on lever forms.
 * @author Bruce Tieu
 *
 */
public class LeverForms {
    private Bot _bot;
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

    /**
     * Instantiate Bot to access actions.
     */
    public LeverForms() {
        _bot = new Bot();
    }

    /**
     * Fill out basic applicant info.
     * 
     * @param jobAppData The job application object.
     */
    public void fillAllBasicInfo(JobApplicationData jobAppData) {
        _bot.tryToFindElementAndSendKeys(By.name("name"), jobAppData.fullname);
        _bot.tryToFindElementAndSendKeys(By.name("email"), jobAppData.email);
        _bot.tryToFindElementAndSendKeys(By.name("phone"), jobAppData.phone);
        _bot.tryToFindElementAndSendKeys(By.name("org"), jobAppData.currentCompany); // current company
        _bot.tryToFindElementAndSendKeys(By.name("urls[LinkedIn]"), jobAppData.linkedin);
        _bot.tryToFindElementAndSendKeys(By.name("urls[GitHub]"), jobAppData.github);
        _bot.tryToFindElementAndSendKeys(By.name("urls[Portfolio]"), jobAppData.portfolio); // portfolio
    }

    /**
     * Fill out questions regarding work authorization and visa.
     * 
     * @param jobAppData The job application object.
     */
    public void fillAllWorkAuth(JobApplicationData jobAppData) {
        _bot.tryToFindElementAndSendKeys(By.xpath("//textarea[@class='card-field-input']"), "Yes"); // work auth
        _bot.tryToFindElementAndSendKeys(By.xpath("//textarea[@class='card-field-input']"), "No"); // visa
        _bot.waitOnElementAndClick(By.xpath("//input[@type='radio' and @value='Yes']")); // Work auth
        _bot.waitOnElementAndClick(By.xpath("//input[@type='radio' and @value='No']")); // Visa
        _bot.tryToSelectFromDpn(By.tagName("select"), "Yes"); // Work auth
        _bot.tryToSelectFromDpn(By.tagName("select"), "No"); // Visa

    }

    /**
     * Fill out How did you find us?
     */
    public void fillAllHowDidYouFindUs() {
        _bot.tryToSelectFromDpn(By.tagName("select"), "Glassdoor");
    }

    /**
     * Upload resume.
     */
    public void uploadResume() {
        try {
            _bot.getWebDriver().findElement(By.name("resume")).sendKeys(JobApplicationData.resumePath);
        } catch (NoSuchElementException e) {
            System.out.println("Error uploading resume");
        }
    }

    /**
     * Submit Application.
     */
    public void submitApplication() {
        _bot.waitOnElementAndClick(By.className("template-btn-submit"));

    }

}
