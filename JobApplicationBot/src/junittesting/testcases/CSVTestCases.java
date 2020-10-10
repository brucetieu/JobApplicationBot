package junittesting.testcases;

import java.util.Arrays;
import java.util.List;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import junittesting.beans.JobBean;

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
    public static final String JOB1_CSV = "Full Stack Intern,Redko,Remote,yes,10/9/20 9:21,EASILY_APPLY,https://www.indeed.com/company/Redko/jobs/Full-Stack-Intern-737deae7d5296876?fccid=fef428aa4152bc0d&vjs=3,no,";
    public static final JobBean JOB1 = new JobBean("Full Stack Intern", "Redko", "Remote", "yes", "10/9/20 9:21",
            "EASILY_APPLY",
            "https://www.indeed.com/company/Redko/jobs/Full-Stack-Intern-737deae7d5296876?fccid=fef428aa4152bc0d&vjs=3",
            "no", "");

    public static final String JOB2_CSV = "Full Stack Engineering Intern,U.S. Xpress Enterprises, Inc.,Scottsdale, AZ,yes,10/9/20 9:21,EASILY_APPLY,https://www.indeed.com/company/U.S.-Xpress/jobs/Full-Stack-Engineering-Intern-546d726323baa0a8?fccid=d921f5450b899369&vjs=3,no,";
    public static final JobBean JOB2 = new JobBean("Full Stack Engineering Intern", "U.S. Xpress Enterprises Inc.","Scottsdale, AZ", "yes", "10/9/20 9:21", "EASILY_APPLY", "https://www.indeed.com/company/U.S.-Xpress/jobs/Full-Stack-Engineering-Intern-546d726323baa0a8?fccid=d921f5450b899369&vjs=3",
            "no", "");

    public static final String CSV_FILE = new StringBuilder(HEADER_CSV).append("\r\n").append(JOB1_CSV).append("\r\n")
            .append(JOB2_CSV).append("\r\n").toString();

    public static final List<JobBean> JOBBEAN = Arrays.asList(JOB1, JOB2);

}
