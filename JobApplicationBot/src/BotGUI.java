
public class BotGUI {

	public static void main(String[] args) throws InterruptedException {
		IndeedBot IB = new IndeedBot("https://www.indeed.com/",
				"email", "password", "Software Developer", "remote");
		IB.navigateToUrl();
		IB.login();
		IB.searchJobs();

	}

}
