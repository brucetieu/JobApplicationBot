package com.btieu.JobApplicationBot;

public class LinkedInPerson {
    public String firstname, profilelink, occupation, message;
    public static int MAX_CONNECTIONS;
    
    public LinkedInPerson(String firstname, String profilelink, String message) {
        this.firstname = firstname;
        this.profilelink = profilelink;
        this.message = message;
    }
    
    public LinkedInPerson() {}
    
  
}

