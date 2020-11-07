package com.btieu.JobApplicationBot;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

public class GreenhouseForms {

    private Bot _botAction;
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

    public GreenhouseForms() {

        _botAction = new Bot();
    }

    public void fillAllBasicInfo(JobApplicationData jobAppData) {
        _botAction.tryToFindElementAndSendKeys(By.id("first_name"), jobAppData.firstname);
        _botAction.tryToFindElementAndSendKeys(By.id("last_name"), jobAppData.lastname);
        _botAction.tryToFindElementAndSendKeys(By.id("email"), jobAppData.email);
        _botAction.tryToFindElementAndSendKeys(By.id("phone"), jobAppData.phone);
        _botAction.tryToFindElementAndSendKeys(By.id("job_application_answers_attributes_0_text_value"), jobAppData.linkedin); // linkedin

        _botAction.tryToFindElementAndSendKeys(By.id("job_application_location"), jobAppData.location); // city
        List<WebElement> cities = _botAction.tryToFindElements(By.className("ui-menu-item"));

        try {
            for (WebElement city : cities) {
                if (city.getText().contains(jobAppData.location)) {
                    city.click();
                }
            }
        } catch (Exception e) {
            System.out.println("Could not click on City");
        }

    }

    public void fillEducation(JobApplicationData jobAppData) {
        _botAction.waitOnElementAndClick(By.id("s2id_education_school_name_0"));
        _botAction.tryToFindElement(By.id("select2-drop")).findElement(By.className("select2-search"));
        _botAction.tryToFindElementAndSendKeys(By.tagName("input"), jobAppData.school);
        
        WebElement ul =_botAction.tryToFindElement(By.id("select2List1"));
        System.out.println(ul.getText());
        List<WebElement> schools = ul.findElements(By.className("select2-results-dept-0"));

        try {
            for (WebElement school : schools) {
                if (school.getText().contains(jobAppData.school)) {
                    school.click();
                }
            }
        } catch (Exception e) {
            System.out.print("Could not click on education");
        }

    }

    public void fillAllWorkAuth(JobApplicationData jobAppData) {

        _botAction.tryToSelectFromDpn(By.id("job_application_answers_attributes_1_boolean_value"), "Yes"); // work auth
        _botAction.tryToSelectFromDpn(By.id("job_application_answers_attributes_2_boolean_value"), "No"); // Visa
        _botAction.tryToSelectFromDpn(By.id("job_application_answers_attributes_3_boolean_value"), "No");
        _botAction.tryToSelectFromDpn(
                By.id("job_application_answers_attributes_3_answer_selected_options_attributes_3_question_option_id"),
                "Acknowledge/Confirm"); // Acknowledge/Confirm
        _botAction.tryToSelectFromDpn(By.id("job_application_answers_attributes_4_boolean_value"), "Yes"); // custom
        _botAction.tryToFindElementAndSendKeys(By.xpath("//input[contains(@autocomplete, 'visa')]"), "No"); //Visa                                                                                                 
    }

    public void fillAllHowDidYouFindUs(JobApplicationData jobAppData) {
        _botAction.tryToSelectFromDpn(
                By.id("job_application_answers_attributes_2_answer_selected_options_attributes_2_question_option_id"),
                "Glassdoor"); // How did you hear about us?
        _botAction.tryToFindElementAndSendKeys(By.id("job_application_answers_attributes_2_text_value"), "Glassdoor"); // Same
                                                                                                                       // as
                                                                                                                       // above.
        _botAction.tryToSelectFromDpn(By.tagName("select"), "Glassdoor");

    }
    
    public void approveConsent() {
        _botAction.waitOnElementAndClick(By.id("job_application_data_compliance_gdpr_consent_given"));
    }

    public void uploadResume() throws IOException {
        _botAction.waitOnElementAndClick(By.cssSelector("a[data-source='paste']"));
        
        try {
        _botAction.tryToFindElement(By.id("resume_text")).sendKeys(ExtractPDFText.extractPDFTextToString(
                new File(JobApplicationData.resumePath)));
        } catch (Exception e) {
            System.out.println("Error uploading resume");
        }
    }

    public void submitApplication() {
        _botAction.waitOnElementAndClick(By.id("submit-app"));
    }
}
