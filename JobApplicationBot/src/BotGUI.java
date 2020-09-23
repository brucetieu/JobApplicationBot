
/**
 * This is the main class which allows the user to fill in their information before applying. 
 * TODO: Implement a GUI which allows the user to type in their information.
 * 
 * @author Bruce Tieu
 *
 */
public class BotGUI {
	
	/**
	 * 
	 * @param args    not used
	 * @throws InterruptedException    if the thread executing the method is interrupted, stop the method and return early
	 */
	public static void main(String[] args) throws InterruptedException {
		// Create a new object which is a job application data. 
		JobApplicationData JAD = new JobApplicationData();
		JAD.setName("John Doe");
		JAD.setEmail("email@email.com");
		JAD.setPhone("5555555555");
		JAD.setResume("/Users/bruce/Documents/WithObj2_Bruce_Tieu_2020_Resume.pdf");
		JAD.setURL("https://www.indeed.com/?from=gnav-util-homepage");
		JAD.setPassword("password");
		JAD.setWhat("Software engineer");
		JAD.setWhere("remote");
		JAD.setAppType("easily apply");
		
		// Create an IndeedBot to apply for jobs.
		IndeedBot IB = new IndeedBot(JAD);
		IB.navigateToUrl();
		IB.login();
		IB.searchJobs();
		IB.JobScrape();
	}

}
