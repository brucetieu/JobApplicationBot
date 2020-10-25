package com.btieu.JobApplicationBot;


import java.util.Hashtable;

/**
 * This class stores the result of the tf-idf computations in TFIDFCalc as an
 * object.
 * 
 * @author Bruce Tieu
 *
 */
public class TFIDFResultContainer {

    private Hashtable<String, Double> _tfidfDocA, _tfidfDocB;

    /**
     * This constructor initializes two tf-idf hash tables.
     * 
     * @param _tfidfDocA The first document's tf-idf hash table.
     * @param _tfidfDocB The second document's tf-idf hash table.
     */
    public TFIDFResultContainer(Hashtable<String, Double> tfidfDocA, Hashtable<String, Double> tfidfDocB) {
        _tfidfDocA = tfidfDocA;
        _tfidfDocB = tfidfDocB;
    }

    /**
     * This gets the tf-idf hash table for the first document.
     * 
     * @return a hash table.
     */
    public Hashtable<String, Double> getTFIDFHashDocA() {
        return _tfidfDocA;
    }

    /**
     * This gets the tf-idf hash table for the second document.
     * 
     * @return a hash table.
     */
    public Hashtable<String, Double> getTFIDFHashDocB() {
        return _tfidfDocB;
    }

}
