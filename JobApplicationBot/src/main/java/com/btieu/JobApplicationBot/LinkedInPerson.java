package com.btieu.JobApplicationBot;

public class LinkedInPerson {
    private String _firstname, _profilelink;
    
    public LinkedInPerson(String firstname, String profilelink) {
        _firstname = firstname;
        _profilelink = profilelink;
    }
    
    public String getFirstname() {
        return _firstname;
    }
    
    public String getProfilelink() {
        return _profilelink;
    }
}

