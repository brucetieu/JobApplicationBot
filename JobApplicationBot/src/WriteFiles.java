import java.io.FileWriter;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * Class which writes information to files.
 * @author bruce
 *
 */
public class WriteFiles {

    private JobPostingData _jobPostingData = new JobPostingData();

    /**
     * This method writes the job posting information to a CSV so the applicant can
     * keep track of easy apply jobs.
     * 
     * @throws Exception
     */
    public void writeJobPostToCSV(JobPostingData jobPostingData) throws Exception {
        final String[] header = new String[] { "jobTitle", "companyName", "companyLocation", "remote", "dateApplied",
                "jobType", "jobLink", "submitted", "jobStatus" };
        ICsvMapWriter mapWriter = null;

        try {
            mapWriter = new CsvMapWriter(new FileWriter("jobPostOutput.csv"), CsvPreference.STANDARD_PREFERENCE);
            final CellProcessor[] processors = getProcessors();

            // Write the header.
            mapWriter.writeHeader(header);

            // Write each HashMap in the ArrayList
            for (int i = 0; i < jobPostingData.jobPostingContainer.size(); i++) {
                System.out.println(jobPostingData.jobPostingContainer.get(i));
                mapWriter.write(jobPostingData.jobPostingContainer.get(i), header, processors);
            }
        } finally {
            if (mapWriter != null) {
                mapWriter.close();
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
