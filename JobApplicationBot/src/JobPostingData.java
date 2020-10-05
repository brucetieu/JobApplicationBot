import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class holds job posting data.
 * @author bruce
 *
 */
public class JobPostingData {

    public int pageNum;
    public String job_title, remote, submitted, companyName, companyLoc;
    public ArrayList<HashMap<String, String>> jobPostingContainer = new ArrayList<HashMap<String, String>>();
    public HashMap<String, String> jobDesc = new HashMap<String, String>();

}
