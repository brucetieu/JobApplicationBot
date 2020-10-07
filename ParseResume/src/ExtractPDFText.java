import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Adapted from:
 * https://svn.apache.org/viewvc/pdfbox/trunk/examples/src/main/java/org/apache/pdfbox/examples/util/ExtractTextSimple.java?view=markup
 * 
 * This is a simple text extraction example to get started.
 *
 * @author Tilman Hausherr
 */
public class ExtractPDFText {
    private String _pdfFile;

    public ExtractPDFText(String pdfFile) {
        _pdfFile = pdfFile;
    }

    /**
     * This will read the PDF and store each word in a string array.
     *
     * @throws IOException If there is an error parsing or extracting the document.
     * @return a String array of words.
     */
    public String[] readPDF() throws IOException {

        try (PDDocument document = PDDocument.load(new File(_pdfFile))) {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new IOException("You do not have permission to extract text");
            }

            PDFTextStripper stripper = new PDFTextStripper();

            // This example uses sorting, but in some cases it is more useful to switch it
            // off,
            // e.g. in some files with columns where the PDF content stream respects the
            // column order.
            stripper.setSortByPosition(true);

            // Get the text of the document, keep as much words as possible, and replace
            // everything else with an empty string.
            String parsedText = stripper.getText(document).replaceAll("[^-A-Za-z./\n\r\t\\+\\' ]+", "");

            // Split up the parsedText by spaces, periods, '/', '-' and make sure it's all
            // lowercase.
            String[] words = parsedText.toLowerCase().split("[\\s\\.\\/\\-]+");

            return words;
        }
    }

    /**
     * This method removes words of length 1 or less and removes all the stopwords.
     * 
     * @param wordsAsArray The words that remain from the initial resume parsing.
     * @return the final word list with stop words removed.
     */
    public List<String> removeStopWords(String[] wordsAsArray) {
        // First convert the array of Strings to a List of Strings.
        List<String> finalWordList = new ArrayList<String>(Arrays.asList(wordsAsArray));

        // Then, iterate through the list and remove any words with length <= 1.
        for (Iterator<String> iter = finalWordList.iterator(); iter.hasNext();) {
            String word = iter.next();
            if (word.length() <= 1) {
                iter.remove();
            }
        }

        // Finally, remove all the stopwords in the list.
        finalWordList.removeAll(StopWords.stopWords);

        return finalWordList;
    }
}