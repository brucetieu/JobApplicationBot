import java.util.ArrayList;
import java.util.HashMap;

public class JobPostingData {

    public int pageNum;
    public String job_title, remote, submitted, companyName, companyLoc;
    public ArrayList<HashMap<String, String>> jobPostingContainer = new ArrayList<HashMap<String, String>>();
    public HashMap<String, String> jobDesc = new HashMap<String, String>();

}
