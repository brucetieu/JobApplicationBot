package com.btieu.ParseResume;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * A text document class to store the text of a pdf file or web page.
 * 
 * @author bruce
 *
 */
public class TextDocument {

    private List<String> _wordsFromDocument; // The list of words for document A and document B.
    private static Set<String> _union = new HashSet<>(); // Store the union of the two lists of words.

    /**
     * Constructor which stores PDF file as a TextDocument object.
     * 
     * @param resumeFile The path to the resume.
     * @throws IOException Catch any file errors.
     */
    public TextDocument(File resumeFile) throws IOException {
        _wordsFromDocument = ExtractPDFText.extractPDFText(resumeFile);
        _union.addAll(_wordsFromDocument); // Add list of words to set.
    }

    /**
     * Constructor which stores text from the web-page as a TextDocument object.
     * 
     * @param text The text from the web page.
     */
    public TextDocument(String text) {
        _wordsFromDocument = ExtractPDFText.parseText(text);
        _union.addAll(_wordsFromDocument); // Add list of words to set.
    }

    /**
     * Get the list of words from a document.
     * 
     * @return a list of words.
     */
    public List<String> getWordsFromDocument() {
        return _wordsFromDocument;
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
