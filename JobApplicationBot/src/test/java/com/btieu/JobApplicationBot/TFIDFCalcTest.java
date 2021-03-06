package com.btieu.JobApplicationBot;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;

import org.junit.jupiter.api.Test;

/**
 * Test all methods in TFIDFCalc.
 * 
 * @author bruce
 *
 */
public class TFIDFCalcTest {

    private TFIDFCalcTestCases _wordListTestCases;
    private TextDocument _textDocument1, _textDocument2;
    private Hashtable<String, Double> _actualJobDescriptionTF, _actualResumeTF;
    private TFIDFCalc _tfidfCalc;

    /**
     * Constructor which initializes the necessary variables for testing.
     * 
     * @throws IOException        Catch any file reading errors.
     * @throws URISyntaxException
     */
    public TFIDFCalcTest() throws IOException, URISyntaxException {
        _wordListTestCases = new TFIDFCalcTestCases();
        _textDocument1 = new TextDocument(TFIDFCalcTestCases.RESUME_STRING);
        _textDocument2 = new TextDocument(TFIDFCalcTestCases.JOB_DESCRIPTION_STRING);
        _tfidfCalc = new TFIDFCalc(_textDocument1, _textDocument2);
        _actualJobDescriptionTF = _tfidfCalc.computeTF(_textDocument1.getWordsFromDocument());
        _actualResumeTF = _tfidfCalc.computeTF(_textDocument2.getWordsFromDocument());
    }

    @Test
    /**
     * Test the computeTF() method in TFIDFCalc.
     * 
     * @throws IOException
     */
    void computeTFTest() throws IOException {

        assertEquals(TFIDFCalcTestCases.fakeJobDescriptionTF(), _actualJobDescriptionTF);
        assertEquals(TFIDFCalcTestCases.fakeResumeTF(), _actualResumeTF);

    }

    @Test
    /**
     * Test the computeIDF() method in TFIDFCalc.
     */
    void computeIDFTest() {
        assertEquals(TFIDFCalcTestCases.fakeIDF(), _tfidfCalc.computeIDF());
    }

    @Test
    /**
     * Test the computeTFIDF() method in TFIDFCalc.
     */
    void computeTFIDFTest() {
        assertEquals(_wordListTestCases.fakeJobDescriptionTFIDF(), _tfidfCalc.computeTFIDF(_actualJobDescriptionTF));
        assertEquals(_wordListTestCases.fakeResumeTFIDF(), _tfidfCalc.computeTFIDF(_actualResumeTF));
    }

    @Test
    /**
     * Test the runTFIDFTest() method in TFIDFCalc.
     * 
     * @throws IOException
     */
    void runTFIDFTest() throws IOException {
        assertEquals(_wordListTestCases.fakeResumeTFIDF(), _tfidfCalc.runTFIDFCalc().getTFIDFHashDocB());
        assertEquals(_wordListTestCases.fakeJobDescriptionTFIDF(), _tfidfCalc.runTFIDFCalc().getTFIDFHashDocA());
    }

}
