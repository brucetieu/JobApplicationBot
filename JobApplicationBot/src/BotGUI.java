
public class BotGUI {

	public static void main(String[] args) throws InterruptedException {
		JobApplicationData JAD = new JobApplicationData();
		JAD.setName("John Doe");
		JAD.setEmail("email@email.com");
		JAD.setPhone("7202610380");
		JAD.setResume("/Users/bruce/Documents/WithObj2_Bruce_Tieu_2020_Resume.pdf");
		JAD.setURL("https://www.indeed.com/?from=gnav-util-homepage");
		JAD.setPassword("password");
		JAD.setWhat("Software engineer");
		JAD.setWhere("remote");
		JAD.setAppType("easily apply");
		
		IndeedBot IB = new IndeedBot(JAD);
		IB.navigateToUrl();
		IB.searchJobs();
		IB.JobScrape();
		

	}

}
