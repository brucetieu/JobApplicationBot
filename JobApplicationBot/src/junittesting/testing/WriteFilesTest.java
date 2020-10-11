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

package junittesting.testing;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import junittesting.jobappbot.JobPostingData;
import junittesting.testcases.CSVTestCases;

import org.junit.jupiter.api.Test;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import static org.junit.jupiter.api.Assertions.*;

/*
 * This is the class to test the writing of beans to a CSV file.
 */
class WriteFilesTest {
    private static final CsvPreference _PREFS = CsvPreference.STANDARD_PREFERENCE;
    private Writer _writer;
    private CsvBeanWriter _beanWriter;
    private JobPostingData _jobBean;

    /*
     * This constructor initializes the writer, bean writer, and job bean.
     */
    public WriteFilesTest() {
        _writer = new StringWriter();
        _beanWriter = new CsvBeanWriter(_writer, _PREFS);
        _jobBean = new JobPostingData();
    }

    @Test
    /**
     * Test the bean writing capability. 
     * @throws IOException Throw an IOException if there's an error.
     */
    void testWriteProcessors() throws IOException {

        _beanWriter.writeHeader(CSVTestCases.HEADER);
        for (JobPostingData jobBean : CSVTestCases.JOB_POSTING_BEAN) {
            _beanWriter.write(jobBean, CSVTestCases.HEADER, CSVTestCases.WRITE_PROCESSORS);
        }
        _beanWriter.flush();

        // Replace double quotes within the string.
        String actual = _writer.toString();
        actual = actual.replace("\"", "");

        // Compare expected (CSVTestCases.CSV_FILE) vs actual results (actual).
        assertEquals(CSVTestCases.CSV_FILE, actual);
    }


}
