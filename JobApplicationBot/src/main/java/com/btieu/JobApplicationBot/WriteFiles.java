package com.btieu.JobApplicationBot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.List;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.nio.file.Files;


/**
 * Class which writes information to files.
 * 
 * @author Bruce Tieu
 *
 */
public class WriteFiles {
    
    private Writer _writer;
    private File _file;
    private String _filename = "";


    /**
     * Initialize file name, file, and writer objects.
     * @param filename The name of csv file.
     * @throws IOException Catch any file IO errors.
     */
    public WriteFiles(String filename) throws IOException {
        _filename = filename;
        _file = new File(_filename);
        _writer = new FileWriter(_file);
    }
 
    /**
     * This method writes a job post to a csv file.
     * 
     * @param jobPosts A set of JobPostingData.
     * @return A string of the file contents.
     * @throws IOException Catch any file writing errors.
     */
    public String writeJobPostToCSV(List<JobPostingData> jobPosts) throws IOException {
        ICsvBeanWriter beanWriter = null;
        final String[] header = {"jobMatchScore", "jobTitle", "companyName", "companyLoc", "remote", "dateApplied", "appType",
                "jobLink", "submitted", "jobStatus" };

        try {
            beanWriter = new CsvBeanWriter(_writer, CsvPreference.STANDARD_PREFERENCE);
            final CellProcessor[] processors = getProcessors();
            beanWriter.writeHeader(header);

            for (JobPostingData jobPost : jobPosts) {
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
        
        // Return the contents of what was written to the file in a string.
        return new String(Files.readAllBytes(Paths.get(_filename)));

    }

    /**
     * Sets up the processors. There are 10 columns in the CSV, so 10 processors are
     * defined.
     * 
     * @return The cell processors.
     */
    private static CellProcessor[] getProcessors() {
        final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // jobMatchScore
                new NotNull(), // jobTitle
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
