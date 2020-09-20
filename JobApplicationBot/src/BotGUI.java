
public class BotGUI {

	public static void main(String[] args) throws InterruptedException {
		IndeedBot IB = new IndeedBot("https://www.indeed.com/?from=gnav-util-homepage",
				"ucdbrucetieu@gmail.com", "password", "Software Developer", "remote");
		IB.navigateToUrl();
		IB.login();
		IB.searchJobs();
	
	}

}
