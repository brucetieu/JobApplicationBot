
/**
 * This class holds all the job application data
 * 
 * @author Bruce Tieu
 *
 */
public class JobApplicationData {
	private String firstname, lastname, fullname, email, phone, city, jobTitle, company, resumePath, url, password, 
			what, where, appType;
	
	// Setters which allow the user to set their information
	public void setFirstName(String firstname) {this.firstname = firstname;}
	public void setLastName(String lastname) {this.lastname = lastname;}
	public void setFullName(String fullname) {this.fullname = fullname;}
	public void setEmail(String email) {this.email = email;}
	public void setPhone(String phone) {this.phone = phone;}
	public void setCity(String city) {this.city = city;}
	public void setJobTitle(String jobTitle) {this.jobTitle = jobTitle;}
	public void setCompanyName(String company) {this.company = company;}
	public void setResume(String resumePath) {this.resumePath = resumePath;}
	public void setURL(String url) {this.url = url;}
	public void setPassword(String password) {this.password = password;}
	public void setWhat(String what) {this.what = what;}
	public void setWhere(String where) {this.where = where;}
	public void setAppType(String appType) {this.appType = appType;}
	
	// Getters which gets the job application information to be used when applying.
	public String getFirstName() {return firstname;}
	public String getLastName() {return lastname;}
	public String getFullName() {return fullname;}
	public String getEmail() {return email;}
	public String getPhone() {return phone;}
	public String getCity() {return city;}
	public String getJobTitle() {return jobTitle;}
	public String getCompanyName() {return company;}
	public String getResume() {return resumePath;}
	public String getURL() {return url;}
	public String getPassword() {return password;}
	public String getWhat() {return what;}
	public String getWhere() {return where;}
	public String getAppType() {return appType;}
}
