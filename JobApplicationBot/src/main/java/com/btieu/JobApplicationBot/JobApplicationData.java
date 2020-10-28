package com.btieu.JobApplicationBot;

/**
 * This class holds all the job application data.
 * 
 * @author Bruce Tieu
 */
public class JobApplicationData {
    public String firstname, lastname, fullname, email, phone, resumePath, platformUrl, password, whatJob, locationOfJob;

    public static enum ApplicationType {
        EASILY_APPLY, NONE, URGENTLY_HIRING
    }

}
