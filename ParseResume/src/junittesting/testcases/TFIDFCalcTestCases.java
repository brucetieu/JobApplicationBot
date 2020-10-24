package junittesting.testcases;

import java.util.Hashtable;

/**
 * Class which generates and holds dummy data for tf-idf testing.
 * 
 * @author bruce
 *
 */
public class TFIDFCalcTestCases {

    // Generate fake text.
    public static final String JOB_DESCRIPTION_STRING = "A requirement for this job is JavaScript. Python is a wonderful language.";
    public static final String RESUME_STRING = "I am learning JavaScript. Java is a cool language.";

    public static Hashtable<String, Double> JOB_DESCRIPTION_TF;
    public static Hashtable<String, Double> RESUME_TF;
    public static Hashtable<String, Double> IDF;

    public Hashtable<String, Double> JOB_DESCRIPTION_TFIDF;
    public Hashtable<String, Double> RESUME_TFIDF;

    /**
     * Initialize tf-idf hash tables.
     * 
     * @param tfidfResume The tf-idf hash table for the resume.
     * @param tfidfJobDescription The tf-idf hash table for the job description.
     */
    public TFIDFCalcTestCases(Hashtable<String, Double> tfidfResume, Hashtable<String, Double> tfidfJobDescription) {
        RESUME_TFIDF = tfidfResume;
        JOB_DESCRIPTION_TFIDF = tfidfJobDescription;
    }

    /**
     * Default constructor.
     */
    public TFIDFCalcTestCases() {
    }

    /**
     * Generate term frequency for words in job description.
     * 
     * @return a hash table.
     */
    public static Hashtable<String, Double> fakeJobDescriptionTF() {
        JOB_DESCRIPTION_TF = new Hashtable<String, Double>();
        JOB_DESCRIPTION_TF.put("am", (double) 1 / 7);
        JOB_DESCRIPTION_TF.put("learning", (double) 1 / 7);
        JOB_DESCRIPTION_TF.put("javascript", (double) 1 / 7);
        JOB_DESCRIPTION_TF.put("java", (double) 1 / 7);
        JOB_DESCRIPTION_TF.put("is", (double) 1 / 7);
        JOB_DESCRIPTION_TF.put("cool", (double) 1 / 7);
        JOB_DESCRIPTION_TF.put("language", (double) 1 / 7);
        JOB_DESCRIPTION_TF.put("requirement", (double) 0);
        JOB_DESCRIPTION_TF.put("for", (double) 0);
        JOB_DESCRIPTION_TF.put("this", (double) 0);
        JOB_DESCRIPTION_TF.put("job", (double) 0);
        JOB_DESCRIPTION_TF.put("python", (double) 0);
        JOB_DESCRIPTION_TF.put("wonderful", (double) 0);
        return JOB_DESCRIPTION_TF;
    }

    /**
     * Generate term frequency for words in resume.
     * 
     * @return a hash table.
     */
    public static Hashtable<String, Double> fakeResumeTF() {
        RESUME_TF = new Hashtable<String, Double>();
        RESUME_TF.put("am", (double) 0);
        RESUME_TF.put("learning", (double) 0);
        RESUME_TF.put("javascript", (double) 1 / 10);
        RESUME_TF.put("java", (double) 0);
        RESUME_TF.put("is", (double) 2 / 10);
        RESUME_TF.put("cool", (double) 0);
        RESUME_TF.put("language", (double) 1 / 10);
        RESUME_TF.put("requirement", (double) 1 / 10);
        RESUME_TF.put("for", (double) 1 / 10);
        RESUME_TF.put("this", (double) 1 / 10);
        RESUME_TF.put("job", (double) 1 / 10);
        RESUME_TF.put("python", (double) 1 / 10);
        RESUME_TF.put("wonderful", (double) 1 / 10);
        return RESUME_TF;
    }

    /**
     * Generate IDF.
     * 
     * @return a hash table.
     */
    public static Hashtable<String, Double> fakeIDF() {
        IDF = new Hashtable<String, Double>();
        IDF.put("am", (double) Math.log(2));
        IDF.put("learning", (double) Math.log(2));
        IDF.put("javascript", (double) 0);
        IDF.put("java", (double) Math.log(2));
        IDF.put("is", (double) 0);
        IDF.put("cool", (double) Math.log(2));
        IDF.put("language", (double) 0);
        IDF.put("requirement", (double) Math.log(2));
        IDF.put("for", (double) Math.log(2));
        IDF.put("this", (double) Math.log(2));
        IDF.put("job", (double) Math.log(2));
        IDF.put("python", (double) Math.log(2));
        IDF.put("wonderful", (double) Math.log(2));
        return IDF;
    }

    /**
     * Generate the tf-idf table for the fake job description text.
     * 
     * @return A hash table.
     */
    public Hashtable<String, Double> fakeJobDescriptionTFIDF() {
        JOB_DESCRIPTION_TFIDF = new Hashtable<String, Double>();
        JOB_DESCRIPTION_TFIDF.put("am", (double) 1 / 7 + (double) 1 / 7 * Math.log(2));
        JOB_DESCRIPTION_TFIDF.put("learning", (double) 1 / 7 + (double) 1 / 7 * Math.log(2));
        JOB_DESCRIPTION_TFIDF.put("javascript", (double) 1 / 7);
        JOB_DESCRIPTION_TFIDF.put("java", (double) 1 / 7 + (double) 1 / 7 * Math.log(2));
        JOB_DESCRIPTION_TFIDF.put("is", (double) 1 / 7);
        JOB_DESCRIPTION_TFIDF.put("cool", (double) 1 / 7 + (double) 1 / 7 * Math.log(2));
        JOB_DESCRIPTION_TFIDF.put("language", (double) 1 / 7);
        JOB_DESCRIPTION_TFIDF.put("requirement", (double) 0);
        JOB_DESCRIPTION_TFIDF.put("for", (double) 0);
        JOB_DESCRIPTION_TFIDF.put("this", (double) 0);
        JOB_DESCRIPTION_TFIDF.put("job", (double) 0);
        JOB_DESCRIPTION_TFIDF.put("python", (double) 0);
        JOB_DESCRIPTION_TFIDF.put("wonderful", (double) 0);
        return JOB_DESCRIPTION_TFIDF;
    }

    /**
     * Generate the tf-idf table for the fake resume text.
     * 
     * @return a hash table.
     */
    public Hashtable<String, Double> fakeResumeTFIDF() {
        RESUME_TFIDF = new Hashtable<String, Double>();
        RESUME_TFIDF.put("am", (double) 0);
        RESUME_TFIDF.put("learning", (double) 0);
        RESUME_TFIDF.put("javascript", (double) 1 / 10);
        RESUME_TFIDF.put("java", (double) 0);
        RESUME_TFIDF.put("is", (double) 2 / 10);
        RESUME_TFIDF.put("cool", (double) 0);
        RESUME_TFIDF.put("language", (double) 1 / 10);
        RESUME_TFIDF.put("requirement", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        RESUME_TFIDF.put("for", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        RESUME_TFIDF.put("this", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        RESUME_TFIDF.put("job", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        RESUME_TFIDF.put("python", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        RESUME_TFIDF.put("wonderful", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        return RESUME_TFIDF;
    }

}
