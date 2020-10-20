
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * Class which calculates the tf-idf of a document.
 * 
 * @author bruce
 *
 */
public class TFIDFCalc {

    private TextDocument _textDoc; // Class which stores actual text of the document.
    private Hashtable<String, Double> _tfidfDoc1, _tfidfDoc2; // Two tf-idf hash tables with the keys being the word and
                                                              // the values being the tf-idf weighting.

    /**
     * This constructor initializes a TextDocument and passes in the raw string
     * content of two documents.
     * 
     * @param document1 The first document.
     * @param document2 The second document.
     * @throws IOException Catch any file errors.
     */
    public TFIDFCalc(String document1, String document2) throws IOException {
        _textDoc = new TextDocument(document1, document2);
    }

    /**
     * This constructor initializes two tf-idf hash tables to be whatever is
     * returned from the runTFIDFCalc() method.
     * 
     * @param _tfidfDoc1 The first document's tf-idf hash table.
     * @param _tfidfDoc2 The second document's tf-idf hash table.
     */
    public TFIDFCalc(Hashtable<String, Double> _tfidfDoc1, Hashtable<String, Double> _tfidfDoc2) {
        this._tfidfDoc1 = _tfidfDoc1;
        this._tfidfDoc2 = _tfidfDoc2;

    }

    /**
     * This gets the tf-idf hash table for document 1.
     * 
     * @return a hash table.
     */
    public Hashtable<String, Double> getTFIDFHashtable1() {
        return this._tfidfDoc1;
    }

    /**
     * This gets the tf-idf hash table for document 2.
     * 
     * @return a hash table.
     */
    public Hashtable<String, Double> getTFIDFHashtable2() {
        return this._tfidfDoc2;
    }

    /**
     * This computes the term frequency of each word.
     * 
     * @param listOfWords The list of words for respective documents (w/o union).
     * @return a new word frequency table as a TextDocument.
     */
    private Hashtable<String, Double> computeTF(List<String> listOfWords) {

        Hashtable<String, Double> tfHash = new Hashtable<String, Double>();

        int termsInDoc = listOfWords.size();

        // Compute the term frequency of each word.
        // TF(t) = (Number of times term t appears in a document) / (Total number of
        // terms in the document).
        for (String word : _textDoc.getFrequencyByWord(listOfWords).keySet()) {
            tfHash.put(word, ((double) _textDoc.getFrequencyByWord(listOfWords).get(word) / (double) termsInDoc));

        }

        return tfHash;
    }

    /**
     * This computes the inverse document frequency of a word.
     * 
     * @param tfDoc1 the frequency table as a TextDocument for document 1.
     * @param tfDoc2 the frequency table as a TextDocument for document 2.
     * @return The idf table as a TextDocument.
     */
    private Hashtable<String, Double> computeIDF(Hashtable<String, Double> tfDoc1, Hashtable<String, Double> tfDoc2) {

        Hashtable<String, Double> idfHash = new Hashtable<String, Double>();
        int numOfDocuments = 2;
        int numDocWTermT = 0;

        // IDF(t) = log_e(Total number of documents / Number of documents with term t in
        // it).
        for (String word : tfDoc1.keySet()) {
            if (tfDoc1.get(word) > 0 || tfDoc2.get(word) > 0) {
                numDocWTermT += 1;
                idfHash.put(word, (double) Math.log((double) numOfDocuments / (double) numDocWTermT));
                numDocWTermT = 0;
            }
        }

        return idfHash;
    }

    /**
     * Calculate the tf-idf of a document.
     * 
     * @param tf  The term frequency table as a TextDocument.
     * @param idf The inverse document frequency table as a TextDocument.
     * @return A table with the tf-idf for each word in a document as a
     *         TextDocument.
     */
    private Hashtable<String, Double> computeTFIDF(Hashtable<String, Double> tf, Hashtable<String, Double> idf) {
        Hashtable<String, Double> tfidfHash = new Hashtable<String, Double>();

        // tfidf(t, d, D) = tf(t,d) * idf(t, D).
        for (String word : tf.keySet()) {
            tfidfHash.put(word, tf.get(word) * idf.get(word));
        }
        return tfidfHash;
    }

    /**
     * This method executes all previous methods in the class.
     * 
     * @return A TFIDFCalc object which passes two TextDocuments.
     * @throws IOException Checks if there are any file reading errors.
     */
    public TFIDFCalc runTFIDFCalc() throws IOException {

        // Compute the term frequency of each document.
        Hashtable<String, Double> tfDoc1 = computeTF(_textDoc.getDocument1());
        Hashtable<String, Double> tfDoc2 = computeTF(_textDoc.getDocument2());

        // Compute the inverse document frequency.
        Hashtable<String, Double> idf = computeIDF(tfDoc1, tfDoc2);

        // Create tf-idf vectors of each document.
        Hashtable<String, Double> tfidfDoc1 = computeTFIDF(tfDoc1, idf);
        Hashtable<String, Double> tfidfDoc2 = computeTFIDF(tfDoc2, idf);

        return new TFIDFCalc(tfidfDoc1, tfidfDoc2);

    }

}