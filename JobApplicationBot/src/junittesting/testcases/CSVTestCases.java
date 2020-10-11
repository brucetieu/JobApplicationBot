package junittesting.testcases;

import java.util.Arrays;
import java.util.List;

import junittesting.beans.JobBean;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;


/**
 * This class builds the test data to test writeJobPostToCSV() method.
 * @author bruce
 *
 */
public class CSVTestCases {
    public static final String[] HEADER = new String[] { "jobTitle", "companyName", "companyLoc", "remote",
            "dateApplied", "appType", "jobLink", "submitted", "jobStatus" };
    public static final String HEADER_CSV = "jobTitle,companyName,companyLoc,remote,dateApplied,appType,jobLink,submitted,jobStatus";

    public static final CellProcessor[] WRITE_PROCESSORS = new CellProcessor[] { new NotNull(), // jobTitle
            new NotNull(), // companyName
            new NotNull(), // companyLocation
            new NotNull(), // remote
            new NotNull(), // dateApplied
            new NotNull(), // jobType
            new NotNull(), // jobLink
            new NotNull(), // submitted
            new NotNull() // jobStatus
    };
    
    // Create muliple test data. One is a string, the other is bean.
    public static final String JOB1_CSV = "Full Stack Intern,Redko,Remote,yes,10/9/20 9:21,EASILY_APPLY,https://www.indeed.com/company/Redko/jobs/Full-Stack-Intern-737deae7d5296876?fccid=fef428aa4152bc0d&vjs=3,no,";
    public static final JobBean JOB1 = new JobBean("Full Stack Intern", "Redko", "Remote", "yes", "10/9/20 9:21",
            "EASILY_APPLY",
            "https://www.indeed.com/company/Redko/jobs/Full-Stack-Intern-737deae7d5296876?fccid=fef428aa4152bc0d&vjs=3",
            "no", "");

    public static final String JOB2_CSV = "Full Stack Engineering Intern,U.S. Xpress Enterprises, Inc.,"
            + "Scottsdale, AZ"
            + ",yes,10/9/20 9:21,EASILY_APPLY,https://www.indeed.com/company/U.S.-Xpress/jobs/Full-Stack-Engineering-Intern-546d726323baa0a8?fccid=d921f5450b899369&vjs=3,no,";
    public static final JobBean JOB2 = new JobBean("Full Stack Engineering Intern", "U.S. Xpress Enterprises, Inc.",
            "Scottsdale, AZ", "yes", "10/9/20 9:21", "EASILY_APPLY",
            "https://www.indeed.com/company/U.S.-Xpress/jobs/Full-Stack-Engineering-Intern-546d726323baa0a8?fccid=d921f5450b899369&vjs=3",
            "no", "");

    public static final String JOB3_CSV = "Intern Web Developer,Atlink Education,Remote,yes,10/9/20 9:22,EASILY_APPLY,https://www.indeed.com/company/Atlink-Education/jobs/Intern-Web-Developer-207f0c248bd8fb96?fccid=4837d661d2a76be2&vjs=3,no,";
    public static final JobBean JOB3 = new JobBean("Intern Web Developer", "Atlink Education", "Remote", "yes",
            "10/9/20 9:22", "EASILY_APPLY",
            "https://www.indeed.com/company/Atlink-Education/jobs/Intern-Web-Developer-207f0c248bd8fb96?fccid=4837d661d2a76be2&vjs=3",
            "no", "");

    public static final String JOB4_CSV = "Software Engineer Intern,EcoCart,San Francisco, CA,yes,10/9/20 9:22,EASILY_APPLY,https://www.indeed.com/company/EcoCart/jobs/Software-Engineer-Intern-6c27e2ac38c37639?fccid=fadf1499cb147e15&vjs=3,no,";
    public static final JobBean JOB4 = new JobBean("Software Engineer Intern", "EcoCart", "San Francisco, CA", "yes",
            "10/9/20 9:22", "EASILY_APPLY",
            "https://www.indeed.com/company/EcoCart/jobs/Software-Engineer-Intern-6c27e2ac38c37639?fccid=fadf1499cb147e15&vjs=3",
            "no", "");

    public static final String JOB5_CSV = "Software Engineer,Copilot,Denver, CO,yes,10/8/20 11:14,EASILY_APPLY,https://www.indeed.com/company/Copilot/jobs/Software-Engineer-f139d9dcdff1f88e?fccid=f4de6c53d8e539bd&vjs=3,no,";
    public static final JobBean JOB5 = new JobBean("Software Engineer", "Copilot", "Denver, CO", "yes", "10/8/20 11:14",
            "EASILY_APPLY",
            "https://www.indeed.com/company/Copilot/jobs/Software-Engineer-f139d9dcdff1f88e?fccid=f4de6c53d8e539bd&vjs=3",
            "no", "");

    // CSV_FILE is what we should expect the method to produce.
    public static final String CSV_FILE = new StringBuilder(HEADER_CSV).append("\r\n").append(JOB1_CSV).append("\r\n")
            .append(JOB2_CSV).append("\r\n").append(JOB3_CSV).append("\r\n").append(JOB4_CSV).append("\r\n")
            .append(JOB5_CSV).append("\r\n").toString();

    // JOBEAN will be what is actually produced by the method.
    public static final List<JobBean> JOBBEAN = Arrays.asList(JOB1, JOB2, JOB3, JOB4, JOB5);

}
