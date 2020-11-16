package com.btieu.JobApplicationBot;

/**
 * Assemble a Linkedin Profile / person.
 * @author Bruce Tieu
 *
 */
public class LinkedInPerson {
    public String firstname, profileLink, occupation, message, keywords;
    public static int MAX_CONNECTIONS;
    
    /**
     * Initialize name, profile link, occupation, and the message.
     * @param firstname The person's first name.
     * @param profileLink Their profile link.
     * @param occupation Their occupation.
     * @param message The message to be sent.
     */
    public LinkedInPerson(String firstname, String profileLink, String occupation, String message) {
        this.firstname = firstname;
        this.profileLink = profileLink;
        this.occupation = occupation;
        this.message = message;
    }

    /**
     * Default constructor.
     */
    public LinkedInPerson() {

    }
}

