package com.btieu.JobApplicationBot;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 * Fill out as many fields of Greenhouse forms as possible.
 * 
 * @author bruce
 *
 */
public class GreenhouseForms {

    private Bot _botAction;
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;

    public GreenhouseForms() {
        _botAction = new Bot(); // Use the methods in Bot.
    }

    /**
     * Fill basic applicant info such as first name, last name, email, phone, etc.
     * 
     * @param jobAppData
     */
    public void fillAllBasicInfo(JobApplicationData jobAppData) {
        _botAction.tryToFindElementAndSendKeys(By.id("first_name"), jobAppData.firstname);
        _botAction.tryToFindElementAndSendKeys(By.id("last_name"), jobAppData.lastname);
        _botAction.tryToFindElementAndSendKeys(By.id("email"), jobAppData.email);
        _botAction.tryToFindElementAndSendKeys(By.id("phone"), jobAppData.phone);
        _botAction.tryToFindElementAndSendKeys(By.id("job_application_answers_attributes_0_text_value"),
                jobAppData.linkedin);

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


    /**
     * Handle all cases of work authentication and visa questions.
     * 
     * @param jobAppData The job application object.
     */
    public void fillAllWorkAuth() {

        _botAction.tryToSelectFromDpn(By.id("job_application_answers_attributes_1_boolean_value"), "Yes"); // work auth
        _botAction.tryToSelectFromDpn(By.id("job_application_answers_attributes_2_boolean_value"), "No"); // Visa
        _botAction.tryToSelectFromDpn(By.id("job_application_answers_attributes_3_boolean_value"), "No");
        _botAction.tryToSelectFromDpn(
                By.id("job_application_answers_attributes_3_answer_selected_options_attributes_3_question_option_id"),
                "Acknowledge/Confirm"); // Acknowledge/Confirm
        _botAction.tryToSelectFromDpn(By.id("job_application_answers_attributes_4_boolean_value"), "Yes"); // custom
        _botAction.tryToFindElementAndSendKeys(By.xpath("//input[contains(@autocomplete, 'visa')]"), "No"); // Visa
    }

    /**
     * Fill out questions regarding where you found the application. In this case,
     * it's glassdoor.
     */
    public void fillAllHowDidYouFindUs() {
        _botAction.tryToSelectFromDpn(
                By.id("job_application_answers_attributes_2_answer_selected_options_attributes_2_question_option_id"),
                "Glassdoor"); // How did you hear about us?
        _botAction.tryToFindElementAndSendKeys(By.id("job_application_answers_attributes_2_text_value"), "Glassdoor");
        _botAction.tryToSelectFromDpn(By.tagName("select"), "Glassdoor");

    }

    /**
     * Custom forms - click the approve consent checkbox.
     */
    public void approveConsent() {
        _botAction.waitOnElementAndClick(By.id("job_application_data_compliance_gdpr_consent_given"));
    }

    /**
     * Upload resume.
     * 
     * @throws IOException Catch any file errors.
     */
    public void uploadResume() {
        _botAction.waitOnElementAndClick(By.cssSelector("a[data-source='paste']"));

        try {
            _botAction.tryToFindElement(By.id("resume_text"))
                    .sendKeys(ExtractPDFText.extractPDFTextToString(new File(JobApplicationData.resumePath)));
        } catch (IOException e) {
            System.out.println("Error uploading resume");
        }
    }

    /**
     * Submit the application.
     */
    public void submitApplication() {
        _botAction.waitOnElementAndClick(By.id("submit-app"));
    }
}
