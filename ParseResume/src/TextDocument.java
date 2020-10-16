import java.util.Hashtable;

/**
 * A text document class which represents hash tables as a TextDocument.
 * @author bruce
 *
 */
public class TextDocument {
    
    public Hashtable<String, Double> hashTable = new Hashtable<String, Double>();
    
    
    /**
     * This is the default constructor.
     */
    public TextDocument() {

    }
    
    /**
     * Parameterized constructor which initializes a hash table. 
     * @param hashTable the hashtable which can represent a document, vector, frequency table.
     */
    public TextDocument(Hashtable<String, Double> hashTable) {
        this.hashTable = hashTable;
    }

    /**
     * This method creates hash tables.
     * @return a hash table with strings as keys and doubles as values. 
     */
    public Hashtable<String, Double> toHashTable() {
        return new Hashtable<String, Double>();
    }
    
}
