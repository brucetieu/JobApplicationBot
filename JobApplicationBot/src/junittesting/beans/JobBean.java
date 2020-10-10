package junittesting.beans;

/**
 * This class holds job posting data.
 * 
 * @author bruce
 *
 */
public class JobBean {

    private String _jobTitle, _companyName, _companyLoc, _remote, _dateApplied, _appType, _jobLink, _submitted,
            _jobStatus;

    public JobBean(String _jobTitle, String _companyName, String _companyLoc, String _remote, String _dateApplied,
            String _appType, String _jobLink, String _submitted, String _jobStatus) {
        this._jobTitle = _jobTitle;
        this._companyName = _companyName;
        this._companyLoc = _companyLoc;
        this._remote = _remote;
        this._dateApplied = _dateApplied;
        this._appType = _appType;
        this._jobLink = _jobLink;
        this._submitted = _submitted;
        this._jobStatus = _jobStatus;
    }

    public JobBean() {

    }
    // Static variables.
//    public static int pageNum;
//    public static ArrayList<JobPostingData> jobPostingContainer = new ArrayList<JobPostingData>();

    @Override
    /**
     * Print out object member variables.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JobPostingData [jobTitle=");
        builder.append(this._jobTitle);
        builder.append(", companyName=");
        builder.append(this._companyName);
        builder.append(", companyLoc=");
        builder.append(this._companyLoc);
        builder.append(", remote=");
        builder.append(this._remote);
        builder.append(", dateApplied=");
        builder.append(this._dateApplied);
        builder.append(", appType=");
        builder.append(this._appType);
        builder.append(", jobLink=");
        builder.append(this._jobLink);
        builder.append(", submitted=");
        builder.append(this._submitted);
        builder.append(", jobStatus=");
        builder.append(this._jobStatus);
        builder.append("]");
        return builder.toString();
    }

    // Needed these getters so that the JobPostingData object correct writes to CSV
    // via BeanWriter.

    /**
     * Get the title of the job e.g Software Engineer.
     * 
     * @return a job title of type string.
     */
    public String getJobTitle() {
        return this._jobTitle;
    }

    /**
     * Get the company name of the job.
     * 
     * @return The company name of type string.
     */
    public String getCompanyName() {
        return this._companyName;
    }

    /**
     * Get the location of the job.
     * 
     * @return The company name of type string.
     */
    public String getCompanyLoc() {
        return this._companyLoc;
    }

    /**
     * Get information about the job being remote or not.
     * 
     * @return A string that is either "yes" or "no".
     */
    public String getRemote() {
        return this._remote;
    }

    /**
     * Get the date the job was applied to.
     * 
     * @return The date formatted as a string.
     */
    public String getDateApplied() {
        return this._dateApplied;
    }

    /**
     * Get the application type e.g easily apply
     * 
     * @return The application type as a string.
     */
    public String getAppType() {
        return this._appType;
    }

    /**
     * Get the link to the job application.
     * 
     * @return A job link which is a string.
     */
    public String getJobLink() {
        return this._jobLink;
    }

    /**
     * Get information about whether or not the application had already been
     * submitted.
     * 
     * @return A string of either "yes" or "no".
     */
    public String getSubmitted() {
        return this._submitted;
    }

    /**
     * Get the status of the application.
     * 
     * @return An empty string.
     */
    public String getJobStatus() {
        return this._jobStatus;
    }

}
