package com.btieu.JobApplicationBot;

public class LinkedInPerson {
    public String firstname, profileLink, occupation, message, keywords;
    public static int MAX_CONNECTIONS;
    
    public LinkedInPerson(String firstname, String profileLink, String occupation, String message) {
        this.firstname = firstname;
        this.profileLink = profileLink;
        this.occupation = occupation;
        this.message = message;
    }
    
    public LinkedInPerson() {}
    
  
}

