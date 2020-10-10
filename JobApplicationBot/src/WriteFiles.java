import java.io.FileWriter;
import java.io.IOException;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


/**
 * Class which writes information to files.
 * @author bruce
 *
 */
public class WriteFiles {

//    private JobPostingData _jobPostingData = new JobPostingData();

    /**
     * This method writes the job posting information to a CSV so the applicant can
     * keep track of easy apply jobs.
     * 
     * @throws Exception
     */
    public void writeJobPostToCSV() {
        ICsvBeanWriter beanWriter = null;
        final String[] header = { "jobTitle", "companyName", "companyLoc", "remote", "dateApplied",
                "appType", "jobLink", "submitted", "jobStatus" };
        
        try {
        beanWriter = new CsvBeanWriter(new FileWriter("jobTitleDescOutput.csv"), CsvPreference.STANDARD_PREFERENCE);
        final CellProcessor[] processors = getProcessors();
        beanWriter.writeHeader(header);
        
        for (JobPostingData jobPost : JobPostingData.jobPostingContainer) {
            beanWriter.write(jobPost, header, processors);
        }
        
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException e) {
                    System.err.println("Error closing the writer: " + e);
                }
            }
            
        }


    }

    /**
     * Sets up the processors. There are 10 columns in the CSV, so 10 processors are
     * defined.
     * 
     * @return The cell processors.
     */
    private static CellProcessor[] getProcessors() {
        final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // jobTitle
                new NotNull(), // companyName
                new NotNull(), // companyLocation
                new NotNull(), // remote
                new NotNull(), // dateApplied
                new NotNull(), // jobType
                new NotNull(), // jobLink
                new NotNull(), // submitted
                new NotNull() // jobStatus
        };

        return processors;
    }

}
