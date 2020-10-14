/*
 * Copyright 2020 Bruce Tieu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.btieu.JobApplicationBot;

import java.util.Arrays;
import java.util.List;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * This class builds the test data to test writeJobPostToCSV() method.
 * 
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

    // Create multiple test data. One is a string, the other is bean.
    public static final String JOB1_CSV = "Full Stack Intern,Redko,Remote,yes,10/9/20 9:21,EASILY_APPLY,https://www.indeed.com/company/Redko/jobs/Full-Stack-Intern-737deae7d5296876?fccid=fef428aa4152bc0d&vjs=3,no,";
    public static final JobPostingData JOB1 = new JobPostingData("Full Stack Intern", "Redko", "Remote", "yes",
            "10/9/20 9:21", "EASILY_APPLY",
            "https://www.indeed.com/company/Redko/jobs/Full-Stack-Intern-737deae7d5296876?fccid=fef428aa4152bc0d&vjs=3",
            "no", "");

    public static final String JOB2_CSV = "Full Stack Engineering Intern,U.S. Xpress Enterprises, Inc.,"
            + "Scottsdale, AZ"
            + ",yes,10/9/20 9:21,EASILY_APPLY,https://www.indeed.com/company/U.S.-Xpress/jobs/Full-Stack-Engineering-Intern-546d726323baa0a8?fccid=d921f5450b899369&vjs=3,no,";
    public static final JobPostingData JOB2 = new JobPostingData("Full Stack Engineering Intern",
            "U.S. Xpress Enterprises, Inc.", "Scottsdale, AZ", "yes", "10/9/20 9:21", "EASILY_APPLY",
            "https://www.indeed.com/company/U.S.-Xpress/jobs/Full-Stack-Engineering-Intern-546d726323baa0a8?fccid=d921f5450b899369&vjs=3",
            "no", "");

    public static final String JOB3_CSV = "Intern Web Developer,Atlink Education,Remote,yes,10/9/20 9:22,EASILY_APPLY,https://www.indeed.com/company/Atlink-Education/jobs/Intern-Web-Developer-207f0c248bd8fb96?fccid=4837d661d2a76be2&vjs=3,no,";
    public static final JobPostingData JOB3 = new JobPostingData("Intern Web Developer", "Atlink Education", "Remote",
            "yes", "10/9/20 9:22", "EASILY_APPLY",
            "https://www.indeed.com/company/Atlink-Education/jobs/Intern-Web-Developer-207f0c248bd8fb96?fccid=4837d661d2a76be2&vjs=3",
            "no", "");

    public static final String JOB4_CSV = "Software Engineer Intern,EcoCart,San Francisco, CA,yes,10/9/20 9:22,EASILY_APPLY,https://www.indeed.com/company/EcoCart/jobs/Software-Engineer-Intern-6c27e2ac38c37639?fccid=fadf1499cb147e15&vjs=3,no,";
    public static final JobPostingData JOB4 = new JobPostingData("Software Engineer Intern", "EcoCart",
            "San Francisco, CA", "yes", "10/9/20 9:22", "EASILY_APPLY",
            "https://www.indeed.com/company/EcoCart/jobs/Software-Engineer-Intern-6c27e2ac38c37639?fccid=fadf1499cb147e15&vjs=3",
            "no", "");

    public static final String JOB5_CSV = "Software Engineer,Copilot,Denver, CO,yes,10/8/20 11:14,EASILY_APPLY,https://www.indeed.com/company/Copilot/jobs/Software-Engineer-f139d9dcdff1f88e?fccid=f4de6c53d8e539bd&vjs=3,no,";
    public static final JobPostingData JOB5 = new JobPostingData("Software Engineer", "Copilot", "Denver, CO", "yes",
            "10/8/20 11:14", "EASILY_APPLY",
            "https://www.indeed.com/company/Copilot/jobs/Software-Engineer-f139d9dcdff1f88e?fccid=f4de6c53d8e539bd&vjs=3",
            "no", "");

    // CSV_FILE is what we should expect the method to produce.
    public static final String CSV_FILE = new StringBuilder(HEADER_CSV).append("\r\n").append(JOB1_CSV).append("\r\n")
            .append(JOB2_CSV).append("\r\n").append(JOB3_CSV).append("\r\n").append(JOB4_CSV).append("\r\n")
            .append(JOB5_CSV).append("\r\n").toString();

    // JOB_POSTING_BEAN will be what is actually produced by the method.
    public static final List<JobPostingData> JOB_POSTING_BEAN = Arrays.asList(JOB1, JOB2, JOB3, JOB4, JOB5);

}
