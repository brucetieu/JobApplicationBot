package junittesting.testing;

import junittesting.testcases.CSVTestCases;
import junittesting.testcases.WordListTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class TFIDFCalcTest {

    @Test
    void getUnionOfListsTest() {
        TFIDFCalc tfidfCalc = new TFIDCalc();
        
        List<String> actualOutput = tfidfCalc.getUnionOfLists(WordListTestCases.RESUME_WORDS, WordListTestCases.JOB_DESCRIPTION_WORDS);
        
        assertEquals(WordListTestCases.EXPECTED_OUTPUT, actualOutput);
    }

}
