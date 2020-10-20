import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * A text document class which represents hash tables as a TextDocument and
 * stores the actual text of the document.
 * 
 * @author bruce
 *
 */
public class TextDocument {

    private List<String> _document1, _document2; // The list of words for document 1 and document 2.
    private Set<String> _union; // The union of the two lists of words.

    /**
     * This constructor passes two document strings to be parsed in ExtractPDFText
     * and then takes the union of each list to create a set of unique words.
     * 
     * @param _document1 The first document.
     * @param _document2 The second document.
     * @throws IOException Throw an exception if there's an error.
     */
    public TextDocument(String _document1, String _document2) throws IOException {
        this._document1 = ExtractPDFText.parseDocument(_document1);
        this._document2 = ExtractPDFText.parseDocument(_document2);
        _union = new HashSet<>(); // Create a set.
        _union.addAll(this._document1); // Add all words from first document to union.
        _union.addAll(this._document2); // Add all words from second document to union.
    }

    /**
     * Get the first document.
     * 
     * @return a list of words.
     */
    public List<String> getDocument1() {
        return _document1;
    }

    /**
     * Get the second document.
     * 
     * @return a list of words.
     */
    public List<String> getDocument2() {
        return _document2;
    }

    /**
     * Get the union of two word lists.
     * 
     * @return a set of words with no duplicates.
     */
    public Set<String> getUnion() {
        return _union;
    }

    /**
     * This method computes the frequency of words in a document.
     * 
     * @param cleanedList The list of words to be counted.
     * @return a hash table with the key being the word and the value being the
     *         frequency of that word.
     */
    public Hashtable<String, Double> getFrequencyByWord(List<String> cleanedList) {

        Hashtable<String, Double> freqUniqueWords = new Hashtable<String, Double>();

        // Set the frequency of all words to be 0.
        for (String unionVal : _union) {
            freqUniqueWords.put(unionVal, 0.0);
        }

        // Count how many times the word appears in the cleanedList, populate those
        // counts as values in the hash table.
        for (String word : cleanedList) {
            freqUniqueWords.put(word, freqUniqueWords.get(word) + 1);
        }

        return freqUniqueWords;

    }

}
