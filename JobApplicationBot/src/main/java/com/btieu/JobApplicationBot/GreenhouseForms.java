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
        _botAction.tryToFindElementAndSendKeys(By.id("first_name"), "Bruce");
        _botAction.tryToFindElementAndSendKeys(By.id("last_name"), "Tieu");
        _botAction.tryToFindElementAndSendKeys(By.id("email"), "ucdbruce");
        _botAction.tryToFindElementAndSendKeys(By.id("phone"), "phone");
        _botAction.tryToFindElementAndSendKeys(By.id("job_application_answers_attributes_0_text_value"), "linkin"); // linkedin

        _botAction.tryToFindElementAndSendKeys(By.id("job_application_location"), "Denver, Colorado"); // city
        List<WebElement> cities = _botAction.tryToFindElements(By.className("ui-menu-item"));

        try {
            for (WebElement city : cities) {
                if (city.getText().contains("Denver, Colorado")) {
                    city.click();
                }
            }
        } catch (Exception e) {
            System.out.print("Could not click on City");
        }

    }

    public void fillEducation(JobApplicationData jobAppData) {
        _botAction.waitOnElementAndClick(By.id("s2id_education_school_name_0"));
        _botAction.tryToFindElementAndSendKeys(By.className("select2-input"), "University of Colorado Denver");

        List<WebElement> schools = _botAction.tryToFindElements(By.className("select2-results-dept-0"));

        try {
            for (WebElement school : schools) {
                if (school.getText().contains("University of Colorado, Denver")) {
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
                "Acknowledge/Confirm"); // custom form
        _botAction.tryToSelectFromDpn(By.id("job_application_answers_attributes_4_boolean_value"), "Yes"); // custom
                                                                                                           // form
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

    public void uploadResume(JobApplicationData jobAppData) throws IOException {
        _botAction.waitOnElementAndClick(By.cssSelector("a[data-source='paste']"));
        _botAction.tryToFindElement(By.id("resume_text")).sendKeys(ExtractPDFText.extractPDFTextToString(
                new File("/Users/bruce/Documents/Resumes/WithObj3_Bruce_Tieu_2020_Resume.pdf")));
    }

    public void submitApplication() {
        _botAction.waitOnElementAndClick(By.id("submit-app"));
    }
}
