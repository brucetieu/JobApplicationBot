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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * This is the class to test the writing of beans to a CSV file.
 */
class WriteFilesTest {
    private Writer _writer;
    private JobPostingData _jobBean;

    /*
     * This constructor initializes the writer, bean writer, and job bean.
     */
    public WriteFilesTest() {
        _writer = new StringWriter();
        _jobBean = new JobPostingData();
    }

    @Test
    /**
     * Test if writeJobPostToCSV() in WriteFiles works. 
     * @throws IOException Throw an IOException if there's an error.
     */
    void testWriteJobPostToCSV() throws IOException {

        // Create a WriteFiles object, specify the filename to output the test results.
        WriteFiles writeFiles = new WriteFiles("testOutput.csv");
        
        // Store the string of what was written to the file.
        String actualOutput = writeFiles.writeJobPostToCSV(CSVTestCases.JOB_POSTING_BEAN);
        
        // Replace all double quotes with an empty string.
        actualOutput = actualOutput.replace("\"", "");
        
        // Check if the expected output is equal to what was actually written to the file.
        assertEquals(CSVTestCases.CSV_FILE, actualOutput);
    }


}
