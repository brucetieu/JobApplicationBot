package com.btieu.JobApplicationBot;

import org.openqa.selenium.By;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * Fill out Lever forms.
 * 
 * @author bruce
 *
 */
public class LeverForms {

    private Bot _botAction;
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

    /**
     * Instantiate Bot to access actions.
     */
    public LeverForms() {
        _botAction = new Bot();
    }

    /**
     * Fill out basic applicant info.
     * 
     * @param jobAppData The job application object.
     */
    public void fillAllBasicInfo(JobApplicationData jobAppData) {
        _botAction.tryToFindElementAndSendKeys(By.name("name"), jobAppData.fullname);
        _botAction.tryToFindElementAndSendKeys(By.name("email"), jobAppData.email);
        _botAction.tryToFindElementAndSendKeys(By.name("phone"), jobAppData.phone);
        _botAction.tryToFindElementAndSendKeys(By.name("org"), jobAppData.currentCompany); // current company
        _botAction.tryToFindElementAndSendKeys(By.name("urls[LinkedIn]"), jobAppData.linkedin);
        _botAction.tryToFindElementAndSendKeys(By.name("urls[GitHub]"), jobAppData.github);
        _botAction.tryToFindElementAndSendKeys(By.name("urls[Portfolio]"), jobAppData.portfolio); // portfolio
    }

    /**
     * Fill out questions regarding work authorization and visa.
     * 
     * @param jobAppData The job application object.
     */
    public void fillAllWorkAuth(JobApplicationData jobAppData) {
        _botAction.tryToFindElementAndSendKeys(By.xpath("//textarea[@class='card-field-input']"), "No"); // visa
        _botAction.waitOnElementAndClick(By.xpath("//input[@type='radio' and @value='Yes']")); // Work auth
        _botAction.waitOnElementAndClick(By.xpath("//input[@type='radio' and @value='No']")); // Visa
        _botAction.tryToSelectFromDpn(By.tagName("select"), "Yes"); // Work auth
        _botAction.tryToSelectFromDpn(By.tagName("select"), "No"); // Visa

    }

    /**
     * Fill out How did you find us?
     */
    public void fillAllHowDidYouFindUs() {
        _botAction.tryToSelectFromDpn(By.tagName("select"), "Glassdoor");
    }

    /**
     * Upload resume.
     */
    public void uploadResume() {
        try {
            _botAction.getWebDriver().findElement(By.name("resume")).sendKeys(JobApplicationData.resumePath);
        } catch (Exception e) {
            System.out.println("Error uploading resume");
        }
    }

    /**
     * Submit Application.
     */
    public void submitApplication() {
        _botAction.waitOnElementAndClick(By.className("template-btn-submit"));

    }

}
