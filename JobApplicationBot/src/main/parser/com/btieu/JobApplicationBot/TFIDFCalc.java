package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Class which calculates the tf-idf of a document.
 * 
 * @author bruce
 *
 */
public class TFIDFCalc {

    private TextDocument _textDocumentA, _textDocumentB;

    /**
     * This initializes two TextDocument objects so that the tf-idf can be
     * calculated between them.
     * 
     * @param textDocumentA The first document.
     * @param textDocumentB The second document.
     * @throws IOException Catch any file errors.
     */
    public TFIDFCalc(TextDocument textDocumentA, TextDocument textDocumentB) throws IOException {
        _textDocumentA = textDocumentA;
        _textDocumentB = textDocumentB;
    }

    /**
     * This computes the term frequency of each word.
     * 
     * @param listOfWords The list of words for each document (w/o union).
     * @return A word frequency hash table.
     */
    public Hashtable<String, Double> computeTF(List<String> listOfWords) {

        Hashtable<String, Double> tfHash = new Hashtable<String, Double>();

        int termsInDoc = listOfWords.size();

        // Compute the term frequency of each word.
        // TF(t) = (Number of times term t appears in a document) / (Total number of
        // terms in the document).
        for (String word : _textDocumentA.getFrequencyByWord(listOfWords).keySet()) {
            tfHash.put(word, ((double) _textDocumentA.getFrequencyByWord(listOfWords).get(word) / (double) termsInDoc));

        }

        return tfHash;
    }

    /**
     * This computes the inverse document frequency of a word.
     * 
     * @return A idf hash table.
     */
    public Hashtable<String, Double> computeIDF() {

        Hashtable<String, Double> idfHash = new Hashtable<String, Double>();

        // Create a list of hash tables.
        List<Hashtable<String, Double>> listOfHashes = new ArrayList<Hashtable<String, Double>>();

        // Add the frequency tables of words of each document to the list.
        listOfHashes.add(_textDocumentA.getFrequencyByWord());
        listOfHashes.add(_textDocumentB.getFrequencyByWord());

        int numOfDocuments = 2;

        // Set all values in the idf hash table to be 0.
        for (String word : listOfHashes.get(0).keySet()) {
            idfHash.put(word, 0.0);
        }

        // Count the number of documents with a term t (word) in it.
        for (Hashtable<String, Double> hashtable : listOfHashes) {
            for (String word : hashtable.keySet()) {
                if (hashtable.get(word) > 0) {
                    idfHash.put(word, (double) idfHash.get(word) + 1);
                }
            }
        }

        // IDF(t) = ln(Total number of documents / Number of documents with term t in
        // it).
        for (String word : idfHash.keySet()) {
            double numDocWithTermT = idfHash.get(word);
            double idf = Math.log((double) numOfDocuments / numDocWithTermT);

            // Replace divide by 0 errors with a 0.
            if (numDocWithTermT == 0)
                idfHash.put(word, 0.0);
            else
                idfHash.put(word, idf);

        }

        return idfHash;
    }

    /**
     * Calculate the tf-idf of a document.
     * 
     * @param tf  The term frequency hash table.
     * @param idf The inverse document frequency hash table.
     * @return A tf-idf hash table.
     * 
     */
    public Hashtable<String, Double> computeTFIDF(Hashtable<String, Double> tf) {
        Hashtable<String, Double> tfidfHash = new Hashtable<String, Double>();

        // tfidf(t, d, D) = tf(t,d) + tf(t,d) * idf(t, D).
        for (String word : tf.keySet()) {
            tfidfHash.put(word, tf.get(word) + (tf.get(word) * computeIDF().get(word)));
        }

        return tfidfHash;
    }

    /**
     * This method executes all previous methods in the class.
     * 
     * @return A TFIDFResultContainer which passes two hash tables.
     * @throws IOException Checks if there are any file reading errors.
     * 
     */
    public TFIDFResultContainer runTFIDFCalc() throws IOException {

        // Compute the term frequency of each document.
        Hashtable<String, Double> tfDocA = computeTF(_textDocumentA.getWordsFromDocument());
        Hashtable<String, Double> tfDocB = computeTF(_textDocumentB.getWordsFromDocument());

        // Create tf-idf vectors of each document.
        Hashtable<String, Double> tfidfDocA = computeTFIDF(tfDocA);
        Hashtable<String, Double> tfidfDocB = computeTFIDF(tfDocB);

        return new TFIDFResultContainer(tfidfDocA, tfidfDocB);

    }

}
