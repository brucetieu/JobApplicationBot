package com.btieu.JobApplicationBot;

import java.util.Hashtable;

/**
 * Class which generates and holds dummy data for tf-idf testing.
 * 
 * @author bruce
 *
 */
public class TFIDFCalcTestCases {
    
    public static final String PATH = "/Users/2020 Senior Year/Fall 2020/SWE Apprenticeship/job-application-bot/JobApplicationBot/src/test/resources/com/btieu/JobApplicationBot/TestResume.pdf";

    // Generate fake text.
    public static final String JOB_DESCRIPTION_STRING = "A requirement for this job is JavaScript. Python is a wonderful language.";
    public static final String RESUME_STRING = "I am learning JavaScript. Java is a cool language.";

    public static Hashtable<String, Double> job_description_tf;
    public static Hashtable<String, Double> resume_tf;
    public static Hashtable<String, Double> idf;

    public Hashtable<String, Double> job_description_tfidf;
    public Hashtable<String, Double> resume_tfidf;

    /**
     * Initialize tf-idf hash tables.
     * 
     * @param tfidfResume         The tf-idf hash table for the resume.
     * @param tfidfJobDescription The tf-idf hash table for the job description.
     */
    public TFIDFCalcTestCases(Hashtable<String, Double> resume_tfidf, Hashtable<String, Double> job_description_tfidf) {
        this.resume_tfidf = resume_tfidf;
        this.job_description_tfidf = job_description_tfidf;
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
        job_description_tf = new Hashtable<String, Double>();
        job_description_tf.put("am", (double) 1 / 7);
        job_description_tf.put("learning", (double) 1 / 7);
        job_description_tf.put("javascript", (double) 1 / 7);
        job_description_tf.put("java", (double) 1 / 7);
        job_description_tf.put("is", (double) 1 / 7);
        job_description_tf.put("cool", (double) 1 / 7);
        job_description_tf.put("language", (double) 1 / 7);
        job_description_tf.put("requirement", (double) 0);
        job_description_tf.put("for", (double) 0);
        job_description_tf.put("this", (double) 0);
        job_description_tf.put("job", (double) 0);
        job_description_tf.put("python", (double) 0);
        job_description_tf.put("wonderful", (double) 0);
        return job_description_tf;
    }

    /**
     * Generate term frequency for words in resume.
     * 
     * @return a hash table.
     */
    public static Hashtable<String, Double> fakeResumeTF() {
        resume_tf = new Hashtable<String, Double>();
        resume_tf.put("am", (double) 0);
        resume_tf.put("learning", (double) 0);
        resume_tf.put("javascript", (double) 1 / 10);
        resume_tf.put("java", (double) 0);
        resume_tf.put("is", (double) 2 / 10);
        resume_tf.put("cool", (double) 0);
        resume_tf.put("language", (double) 1 / 10);
        resume_tf.put("requirement", (double) 1 / 10);
        resume_tf.put("for", (double) 1 / 10);
        resume_tf.put("this", (double) 1 / 10);
        resume_tf.put("job", (double) 1 / 10);
        resume_tf.put("python", (double) 1 / 10);
        resume_tf.put("wonderful", (double) 1 / 10);
        return resume_tf;
    }

    /**
     * Generate IDF.
     * 
     * @return a hash table.
     */
    public static Hashtable<String, Double> fakeIDF() {
        idf = new Hashtable<String, Double>();
        idf.put("am", (double) Math.log(2));
        idf.put("learning", (double) Math.log(2));
        idf.put("javascript", (double) 0);
        idf.put("java", (double) Math.log(2));
        idf.put("is", (double) 0);
        idf.put("cool", (double) Math.log(2));
        idf.put("language", (double) 0);
        idf.put("requirement", (double) Math.log(2));
        idf.put("for", (double) Math.log(2));
        idf.put("this", (double) Math.log(2));
        idf.put("job", (double) Math.log(2));
        idf.put("python", (double) Math.log(2));
        idf.put("wonderful", (double) Math.log(2));
        return idf;
    }

    /**
     * Generate the tf-idf table for the fake job description text.
     * 
     * @return A hash table.
     */
    public Hashtable<String, Double> fakeJobDescriptionTFIDF() {
        job_description_tfidf = new Hashtable<String, Double>();
        job_description_tfidf.put("am", (double) 1 / 7 + (double) 1 / 7 * Math.log(2));
        job_description_tfidf.put("learning", (double) 1 / 7 + (double) 1 / 7 * Math.log(2));
        job_description_tfidf.put("javascript", (double) 1 / 7);
        job_description_tfidf.put("java", (double) 1 / 7 + (double) 1 / 7 * Math.log(2));
        job_description_tfidf.put("is", (double) 1 / 7);
        job_description_tfidf.put("cool", (double) 1 / 7 + (double) 1 / 7 * Math.log(2));
        job_description_tfidf.put("language", (double) 1 / 7);
        job_description_tfidf.put("requirement", (double) 0);
        job_description_tfidf.put("for", (double) 0);
        job_description_tfidf.put("this", (double) 0);
        job_description_tfidf.put("job", (double) 0);
        job_description_tfidf.put("python", (double) 0);
        job_description_tfidf.put("wonderful", (double) 0);
        return job_description_tfidf;
    }

    /**
     * Generate the tf-idf table for the fake resume text.
     * 
     * @return a hash table.
     */
    public Hashtable<String, Double> fakeResumeTFIDF() {
        resume_tfidf = new Hashtable<String, Double>();
        resume_tfidf.put("am", (double) 0);
        resume_tfidf.put("learning", (double) 0);
        resume_tfidf.put("javascript", (double) 1 / 10);
        resume_tfidf.put("java", (double) 0);
        resume_tfidf.put("is", (double) 2 / 10);
        resume_tfidf.put("cool", (double) 0);
        resume_tfidf.put("language", (double) 1 / 10);
        resume_tfidf.put("requirement", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        resume_tfidf.put("for", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        resume_tfidf.put("this", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        resume_tfidf.put("job", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        resume_tfidf.put("python", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        resume_tfidf.put("wonderful", (double) 1 / 10 + (double) 1 / 10 * Math.log(2));
        return resume_tfidf;
    }

}
