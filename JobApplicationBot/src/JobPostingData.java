import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class holds job title description data. Eg job title, location,
 * 
 * @author bruce
 *
 */
public class JobPostingData {

    public int pageNum;
    public String job_title, remote, submitted, companyName, companyLoc;
    public static ArrayList<HashMap<String, String>> jobTitleDescContainer = new ArrayList<HashMap<String, String>>();
    // The keys represent basic job title information e.g. the job title and
    // location, and the values represent what the specific job and location are e.g
    // 'Software Engineer' and 'Denver, CO'.
    public HashMap<String, String> jobTitleDescByJobURI = new HashMap<String, String>();

}
