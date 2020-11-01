package com.btieu.JobApplicationBot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This class holds job posting data.
 * 
 * @author bruce
 *
 */
public class JobPostingData {
    
    public double jobMatch;
    public String jobTitle, companyName, companyLoc, remote, dateApplied, appType, jobLink, submitted, jobStatus;

    public JobPostingData(double jobMatch, String jobTitle, String companyName, String companyLoc, String remote, String dateApplied,
            String appType, String jobLink, String submitted, String jobStatus) {
        this.jobMatch = jobMatch;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.companyLoc = companyLoc;
        this.remote = remote;
        this.dateApplied = dateApplied;
        this.appType = appType;
        this.jobLink = jobLink;
        this.submitted = submitted;
        this.jobStatus = jobStatus;
    }

    // Static variables.
    public static int pageNum;
    public static Set<JobPostingData> jobPostingContainer = new HashSet<JobPostingData>();

    public JobPostingData() {
        
    }

    @Override
    /**
     * Print out object member variables.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JobPostingData [jobMatch=");
        builder.append(this.jobMatch);
        builder.append(", jobTitle=");
        builder.append(this.jobTitle);
        builder.append(", companyName=");
        builder.append(this.companyName);
        builder.append(", companyLoc=");
        builder.append(this.companyLoc);
        builder.append(", remote=");
        builder.append(this.remote);
        builder.append(", dateApplied=");
        builder.append(this.dateApplied);
        builder.append(", appType=");
        builder.append(this.appType);
        builder.append(", jobLink=");
        builder.append(this.jobLink);
        builder.append(", submitted=");
        builder.append(this.submitted);
        builder.append(", jobStatus=");
        builder.append(this.jobStatus);
        builder.append("]");
        return builder.toString();
    }

    // Needed these getters so that the JobPostingData object correct writes to CSV via BeanWriter.
    
    /**
     * Get the cosine similarity value ie. how well the resume matches the job description.
     * @return The cosine similarity. 
     */
    public double getjobMatch() {
        return this.jobMatch;
    }
    
    /**
     * Get the title of the job e.g Software Engineer.
     * @return a job title of type string.
     */
    public String getJobTitle() {
        return this.jobTitle;
    }

    /**
     * Get the company name of the job.
     * @return The company name of type string.
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Get the location of the job.
     * @return The company name of type string.
     */
    public String getCompanyLoc() {
        return this.companyLoc;
    }

    /**
     * Get information about the job being remote or not.
     * @return A string that is either "yes" or "no".
     */
    public String getRemote() {
        return this.remote;
    }

    /**
     * Get the date the job was applied to.
     * @return The date formatted as a string.
     */
    public String getDateApplied() {
        return this.dateApplied;
    }
    
    /**
     * Get the application type e.g easily apply
     * @return The application type as a string.
     */
    public String getAppType() {
        return this.appType;
    }

    /**
     * Get the link to the job application.
     * @return A job link which is a string.
     */
    public String getJobLink() {
        return this.jobLink;
    }
    
    /**
     * Get information about whether or not the application had already been submitted.
     * @return A string of either "yes" or "no".
     */
    public String getSubmitted() {
        return this.submitted;
    }

    /**
     * Get the status of the application.
     * @return An empty string.
     */
    public String getJobStatus() {
        return this.jobStatus;
    }

}
