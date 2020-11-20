package net.codejava.swing;

import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.LinkedInBot;
import com.btieu.JobApplicationBot.LinkedInPerson;

/**
 * Class to execute the LinkedInBot.
 * 
 * @author bruce
 *
 */
public class RunLinkedInBot {
    /**
     * This constructor will execute the LinkedInBot when instantiated.
     * 
     * @param jobAppData     The JobApplicationData object will all applicant data.
     * @param linkedInPerson The LinkedInPerson object represent any given profile.
     */
    public RunLinkedInBot(JobApplicationData jobAppData, LinkedInPerson linkedInPerson) {
        LinkedInBot linkedinBot = new LinkedInBot(jobAppData, linkedInPerson);
        linkedinBot.navigateToJobPage();
        linkedinBot.login();
        linkedinBot.goToProfile();
        linkedinBot.aggregatePeopleProfiles();
        linkedinBot.connect();

    }
}
