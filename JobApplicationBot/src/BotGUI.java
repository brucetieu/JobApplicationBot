import javax.swing.Jframe;
import javax.swing.jpanel;

/**
 * This is the main class which allows the user to fill in their information before applying. 
 * TODO: Implement a GUI which allows the user to type in their information.
 * @author Bruce Tieu
 */
public class BotGUI {
	/**
	 * @param args Not used.
	 * @throws InterruptedException if the thread executing the method is interrupted, stop the method and return early
	 */
	public static void main(String[] args) throws InterruptedException {
		// Create a new object which is a job application data. 
//		JobApplicationData jobAppData = new JobApplicationData();
//		jobAppData.firstname = "John";
//		jobAppData.lastname = "Doe";
//		jobAppData.fullname = "John Doe";
//		jobAppData.email = "ucdbrucetieu@gmail.com";
//		jobAppData.phone = "7202610380";
//		jobAppData.resumePath = "/Users/bruce/Documents/WithObj2_Bruce_Tieu_2020_Resume.pdf";
//		jobAppData.url = "https://www.indeed.com/?from=gnav-util-homepage";
//		jobAppData.password = "password";
//		jobAppData.whatJob = "Software engineer";
//		jobAppData.locationOfJob = "remote";
//		
//		// Create an IndeedBot to apply for jobs.
//		IndeedBot IB = new IndeedBot(jobAppData, JobApplicationData.ApplicationType.EASILY_APPLY);
//		IB.navigateToUrl();
//		IB.searchJobs();
//		IB.jobScrape();
		
		JFrame panel = new JPanel();
		JFrame frame = new JFrame();
		frame.setSize(100,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
	}
