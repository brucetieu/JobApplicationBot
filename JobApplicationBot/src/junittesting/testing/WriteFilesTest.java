package junittesting.testing;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import junittesting.beans.JobBean;
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
    private JobBean _bean;

    /*
     * This constructor initializes the writer, bean writer, and bean JobBean object.
     */
    public WriteFilesTest() {
        _writer = new StringWriter();
        _beanWriter = new CsvBeanWriter(_writer, _PREFS);
        _bean = new JobBean();
    }

    @Test
    void testWriteProcessors() throws IOException {

        _beanWriter.writeHeader(CSVTestCases.HEADER);
        for (JobBean bean : CSVTestCases.JOBBEAN) {
            _beanWriter.write(bean, CSVTestCases.HEADER, CSVTestCases.WRITE_PROCESSORS);
        }
        _beanWriter.flush();

        // Replace double quotes within the string.
        String actual = _writer.toString();
        actual = actual.replace("\"", "");

        // Compare expected (CSVTestCases.CSV_FILE) vs actual results (temp).
        assertEquals(CSVTestCases.CSV_FILE, actual);
    }


}
