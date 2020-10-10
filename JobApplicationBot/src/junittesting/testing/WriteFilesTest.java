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

class WriteFilesTest {
    private static final CsvPreference _PREFS = CsvPreference.STANDARD_PREFERENCE;
    private Writer _writer;
    private CsvBeanWriter _beanWriter;
    private JobBean _bean;

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
        assertEquals(CSVTestCases.CSV_FILE, _writer.toString());
    }

}
