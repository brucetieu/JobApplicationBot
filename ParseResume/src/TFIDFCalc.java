
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Class which calculates the tf-idf of a document.
 * 
 * @author bruce
 *
 */
public class TFIDFCalc {

    private TextDocument _vectorA;
    private TextDocument _vectorB;
    private TextDocument _words;

    /**
     * Default constructor. Initialize TextDocument object.
     */
    public TFIDFCalc() {
        _words = new TextDocument();
    }

    /**
     * This is a parameterized constructor which takes two TextDocuments
     * representing tf-idf vectors. Also create new TextDocument vectors.
     * 
     * @param tfidfDoc1 The tf-idf TextDocument "vector" of the first document; is a
     *                  hash table with words as keys, tf-idf as values.
     * @param tfidfDoc2 The tf-idf TextDocument "vector" of the second document; is
     *                  a hash table with words as keys, tf-idf as values.
     */
    public TFIDFCalc(TextDocument tfidfDoc1, TextDocument tfidfDoc2) {
        _vectorA = new TextDocument();
        _vectorB = new TextDocument();
        _vectorA.hashTable = tfidfDoc1.hashTable;
        _vectorB.hashTable = tfidfDoc2.hashTable;
    }

    /**
     * Get the first vector.
     * 
     * @return a TextDocument object which represents a vector.
     */
    public TextDocument getVectorA() {
        return _vectorA;
    }

    /**
     * Get the second vector.
     * 
     * @return A TextDocument object which represents a vector.
     */
    public TextDocument getVectorB() {
        return _vectorB;
    }

    /**
     * This gets the union of the two documents.
     * 
     * @param resumeWords  The first list of words from the resume.
     * @param jobDescWords The second list of words from the job description.
     * @return an array list with unique words from both lists.
     */
    public ArrayList<String> getUnionOfLists(List<String> resumeWords, List<String> jobDescWords) {
        Set<String> set = new HashSet<>();
        set.addAll(resumeWords);
        set.addAll(jobDescWords);

        return new ArrayList<>(set);
    }

    /**
     * This keeps track of how many times the unique word shows up in each document.
     * 
     * @param set         The array list of unique words.
     * @param cleanedList The list of words generated from the resume or job
     *                    description.
     * @return a hash table represented as a TextDocument which counts the frequency
     *         of each unique word. Need one for the resume and another for the job
     *         description.
     */
    public TextDocument countUniqueWords(List<String> set, List<String> cleanedList) {

        Hashtable<String, Double> freqUniqueWords = _words.toHashTable();

        // Initialize the value of each key to be 0.
        for (int i = 0; i < set.size(); i++) {
            freqUniqueWords.put(set.get(i), 0.0);
        }

        // Count how many times the word appears in the cleanedList, populate those
        // counts as values in the Hashtable.
        for (String word : cleanedList) {
            freqUniqueWords.put(word, freqUniqueWords.get(word) + 1);
        }

        return new TextDocument(freqUniqueWords);

    }

    /**
     * This computes the term frequency of each word.
     * 
     * @param freqUniqueWords The TextDocument in the form of a hash table with the
     *                        frequency of unique words in each document.
     * @param listOfWords     The list of words for respective documents (w/o
     *                        union).
     * @return a new word frequency table as a TextDocument.
     */
    public TextDocument computeTF(TextDocument freqUniqueWords, List<String> listOfWords) {

        Hashtable<String, Double> tfHash = _words.toHashTable();

        int termsInDoc = listOfWords.size();

        // Compute the term frequency of each word.
        // TF(t) = (Number of times term t appears in a document) / (Total number of
        // terms in the document).
        for (String word : freqUniqueWords.hashTable.keySet()) {
            tfHash.put(word, ((double) freqUniqueWords.hashTable.get(word) / (double) termsInDoc));

        }
        return new TextDocument(tfHash);
    }

    /**
     * This computes the inverse document frequency of a word.
     * 
     * @param freqUniqueWordsA the frequency table as a TextDocument for document 1.
     * @param freqUniqueWordsB the frequency table as a TextDocument for document 2.
     * @return The idf table as a TextDocument.
     */
    public TextDocument computeIDF(TextDocument freqUniqueWordsA, TextDocument freqUniqueWordsB) {

        Hashtable<String, Double> idfHash = _words.toHashTable();
        int numOfDocuments = 2;
        int numDocWTermT = 0;

        // IDF(t) = log_e(Total number of documents / Number of documents with term t in
        // it).
        for (String word : freqUniqueWordsA.hashTable.keySet()) {
            if (freqUniqueWordsA.hashTable.get(word) > 0 || freqUniqueWordsB.hashTable.get(word) > 0) {
                numDocWTermT += 1;
                idfHash.put(word, (double) Math.log((double) numOfDocuments / (double) numDocWTermT));
                numDocWTermT = 0;
            }
        }

        return new TextDocument(idfHash);
    }

    /**
     * Calculate the tf-idf of a document.
     * 
     * @param tf  The term frequency table as a TextDocument.
     * @param idf The inverse document frequency table as a TextDocument.
     * @return A table with the tf-idf for each word in a document as a
     *         TextDocument.
     */
    public TextDocument computeTFIDF(TextDocument tf, TextDocument idf) {
        Hashtable<String, Double> tfidfHash = _words.toHashTable();

        // tfidf(t, d, D) = tf(t,d) * idf(t, D).
        for (String word : tf.hashTable.keySet()) {
            tfidfHash.put(word, tf.hashTable.get(word) * idf.hashTable.get(word));
        }
        return new TextDocument(tfidfHash);
    }

    /**
     * This method executes all previous methods in the class.
     * 
     * @param doc1 The raw string contents of the first document.
     * @param doc2 The raw string contents of the second document.
     * @return A TFIDFCalc object which passes two TextDocuments.
     * @throws IOException Checks if there are any file reading errors.
     */
    public TFIDFCalc runTFIDFCalc(String doc1, String doc2) throws IOException {

        // Get the union of words in each document.
        List<String> union = getUnionOfLists(ExtractPDFText.parseDocument(doc1), ExtractPDFText.parseDocument(doc2));

        // Count the unique words in each document.
        TextDocument doc1Words = countUniqueWords(union, ExtractPDFText.parseDocument(doc1));
        TextDocument doc2Words = countUniqueWords(union, ExtractPDFText.parseDocument(doc2));

        // Compute the term frequency of each document.
        TextDocument tfDoc1 = computeTF(doc1Words, ExtractPDFText.parseDocument(doc1));
        TextDocument tfDoc2 = computeTF(doc2Words, ExtractPDFText.parseDocument(doc2));

        // Compute the inverse document frequency.
        TextDocument idf = computeIDF(tfDoc1, tfDoc2);

        // Create tf-idf vectors of each document.
        TextDocument tfidfDoc1 = computeTFIDF(tfDoc1, idf);
        TextDocument tfidfDoc2 = computeTFIDF(tfDoc2, idf);

        return new TFIDFCalc(tfidfDoc1, tfidfDoc2);

    }

}