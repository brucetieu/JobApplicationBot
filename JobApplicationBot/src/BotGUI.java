
public class BotGUI {

	public static void main(String[] args) throws InterruptedException {
		IndeedBot IB = new IndeedBot("https://www.indeed.com/",
				"ucdbrucetieu@gmail.com", "\\x\"Y,27=", "Software Developer", "remote");
		IB.navigateToUrl();
		IB.login();
		IB.searchJobs();

	}

}
