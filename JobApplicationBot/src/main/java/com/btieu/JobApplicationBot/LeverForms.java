package com.btieu.JobApplicationBot;

import org.openqa.selenium.By;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

public class LeverForms {

    private Bot _botAction;
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

    public LeverForms() {

        _botAction = new Bot();
    }

    public void fillAllBasicInfo(JobApplicationData jobAppData) {
        _botAction.tryToFindElementAndSendKeys(By.name("name"), "Bruce Tieu");
        _botAction.tryToFindElementAndSendKeys(By.name("email"), "ucdbrucetieu");
        _botAction.tryToFindElementAndSendKeys(By.name("phone"), "5");
        _botAction.tryToFindElementAndSendKeys(By.name("org"), "x"); // current company
        _botAction.tryToFindElementAndSendKeys(By.name("urls[LinkedIn]"), "x");
        _botAction.tryToFindElementAndSendKeys(By.name("urls[GitHub]"), "x");
        _botAction.tryToFindElementAndSendKeys(By.name("urls[Portfolio]"), "x"); // portfolio
    }

    public void fillAllWorkAuth(JobApplicationData jobAppData) {
        _botAction.tryToFindElementAndSendKeys(By.xpath("//textarea[@class='card-field-input']"), "No"); // visa
        _botAction.waitOnElementAndClick(By.xpath("//input[@type='radio' and @value='Yes']")); // Work auth

    }

    public void fillAllHowDidYouFindUs(JobApplicationData jobAppData) {

    }

    public void uploadResume(JobApplicationData jobAppData) {
        _botAction.getWebDriver().findElement(By.name("resume"))
                .sendKeys("/Users/bruce/Documents/Resumes/WithObj3_Bruce_Tieu_2020_Resume.pdf");
    }
    
    public void submitApplication() {
      _botAction.waitOnElementAndClick(By.className("template-btn-submit"));
       
    }

}
