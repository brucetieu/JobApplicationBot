
/**
 * Copyright 2020 Bruce Tieu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License. This is a simple text extraction example to get started.
 *
 * Adapted from:
 * https://svn.apache.org/viewvc/pdfbox/trunk/examples/src/main/java/org/apache/pdfbox/examples/util/ExtractTextSimple.java?view=markup
 * 
 * @author Tilman Hausherr
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

public class ExtractPDFText {

    /**
     * This will read the PDF and store each word in a string array.
     *
     * @throws IOException If there is an error parsing or extracting the document.
     * @return a String array of words.
     */
    public static List<String> parseDocument(String doc) throws IOException {

        // Create file object with path of the pdf.
        File file = new File(doc);

        // Check if the file is a file. If it is, then parse the pdf.
        if (file.isFile()) {
            try (PDDocument document = PDDocument.load(file)) {
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

                return removeStopWords(splitText(stripper.getText(document)));
            }

        }
        // Otherwise, doc is just a regular string which also needs to be reduced.
        else {
            return removeStopWords(splitText(doc));
        }
    }

    /**
     * This method splits a text by spaces.
     * 
     * @param text The document.
     * @return a string of splitted words.
     */
    public static String[] splitText(String text) {
        String parsedText = text.replaceAll("[^-A-Za-z./\n\r\t\\+\\' ]+", "");
        String[] words = parsedText.toLowerCase().split("[\\s\\.\\/\\-]+");
        return words;
    }

    /**
     * This method removes words of length 1 or less and removes all the stopwords.
     * 
     * @param wordsAsArray The words that remain from the initial resume parsing.
     * @return the final word list with stop words removed.
     */
    public static List<String> removeStopWords(String[] wordsAsArray) {
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
        finalWordList.removeAll(StopWords.STOP_WORDS);

        return finalWordList;
    }

}