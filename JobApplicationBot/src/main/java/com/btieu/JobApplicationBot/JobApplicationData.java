package com.btieu.JobApplicationBot;

/**
 * This class holds all the job application data.
 * 
 * @author Bruce Tieu
 */
public class JobApplicationData {
    public String firstname, lastname, fullname, email, phone, school, location, currentCompany, linkedin, github, portfolio, password, platformUrl, whatJob,
            locationOfJob;
    public static String resumePath;

    public static enum ApplicationType {
        EASILY_APPLY, NONE, URGENTLY_HIRING, ALL
    }

}
