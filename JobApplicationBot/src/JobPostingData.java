import java.util.ArrayList;

/**
 * This class holds job posting data.
 * 
 * @author bruce
 *
 */
public class JobPostingData {

    private String jobTitle, companyName, companyLoc, remote, dateApplied, appType, jobLink, submitted, jobStatus;

    public JobPostingData(String jobTitle, String companyName, String companyLoc, String remote, String dateApplied,
            String appType, String jobLink, String submitted, String jobStatus) {
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
    public static ArrayList<JobPostingData> jobPostingContainer = new ArrayList<JobPostingData>();


    @Override
    /**
     * Print out object member variables.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JobPostingData [jobTitle=");
        builder.append(jobTitle);
        builder.append(", companyName=");
        builder.append(companyName);
        builder.append(", companyLoc=");
        builder.append(companyLoc);
        builder.append(", remote=");
        builder.append(remote);
        builder.append(", dateApplied=");
        builder.append(dateApplied);
        builder.append(", appType=");
        builder.append(appType);
        builder.append(", jobLink=");
        builder.append(jobLink);
        builder.append(", submitted=");
        builder.append(submitted);
        builder.append(", jobStatus=");
        builder.append(jobStatus);
        builder.append("]");
        return builder.toString();
    }

    // Needed these getters so that the JobPostingData object correct writes to CSV via BeanWriter.
    
    /**
     * Get the title of the job e.g Software Engineer.
     * @return a job title of type string.
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Get the company name of the job.
     * @return The company name of type string.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Get the location of the job.
     * @return The company name of type string.
     */
    public String getCompanyLoc() {
        return companyLoc;
    }

    /**
     * Get information about the job being remote or not.
     * @return A string that is either "yes" or "no".
     */
    public String getRemote() {
        return remote;
    }

    /**
     * Get the date the job was applied to.
     * @return The date formatted as a string.
     */
    public String getDateApplied() {
        return dateApplied;
    }
    
    /**
     * Get the application type e.g easily apply
     * @return The application type as a string.
     */
    public String getAppType() {
        return appType;
    }

    /**
     * Get the link to the job application.
     * @return A job link which is a string.
     */
    public String getJobLink() {
        return jobLink;
    }
    
    /**
     * Get information about whether or not the application had already been submitted.
     * @return A string of either "yes" or "no".
     */
    public String getSubmitted() {
        return submitted;
    }

    /**
     * Get the status of the application.
     * @return An empty string.
     */
    public String getJobStatus() {
        return jobStatus;
    }

}
