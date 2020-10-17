package junittesting.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordListTestCases {

    public static final String[] RESUME = { "results", "oriented", "finance", "professional",
            "years", "experience", "publicly", "traded", "privately", "held", "enterprises" };

    public static final String[] JOB_DESCRIPTION = { "financial", "analyst", "will", "assist", "corporate",
            "investments", "capital", "appropriation", "request", "processes", "support", "corporate", "finance",
            "team", "key", "presentations", "board", "directors", "senior", "management" };
    
    public static final List<String> RESUME_WORDS = Arrays.asList(RESUME);
    public static final List<String> JOB_DESCRIPTION_WORDS = Arrays.asList(JOB_DESCRIPTION);
    
    public static final String[] UNIQUE_WORDS = {"results", "oriented", "finance", "professional", 
            "years", "experience", "publicly", "traded", "privately", "held", "enterprises", "financial", "analyst", "will", "assist", "corporate",
            "investments", "capital", "appropriation", "request", "processes", "support", "corporate",
            "team", "key", "presentations", "board", "directors", "senior", "management"};
    
    public static final List<String> EXPECTED_OUTPUT = Arrays.asList(UNIQUE_WORDS);
    
    
}
