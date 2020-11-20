package net.codejava.swing;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

/**
 * Helper class for creating some components in CreateGUIComponents.
 * @author Bruce Tieu
 */
public class GUIComponentsHelper {
    
    private static final int _MAX_PAGE_NUM = 50;
    private static final int _MAX_CONNECTION_REQUESTS = 500;
    private static MaskFormatter _maskFormatter;
    
    public static final String PHONE_MASK = "##########";
    
    /**
     * Generate page numbers to add as a dropdown.
     * @return An Integer array page numbers.
     */
    public static Integer[] generatePageNumbers(int start) {
        Integer pageNumContainer[] = new Integer[_MAX_PAGE_NUM];
        for (int i = start; i < _MAX_PAGE_NUM; i++) {
            pageNumContainer[i] = i;
        }
        return pageNumContainer;
    }
    
    /**
     * Generate a list of connection request numbers, the max being 500.
     * @return An Integer array of connection numbers.
     */
    public static Integer[] generateMaxConnectRequests() {
        Integer maxConnectContainer[] = new Integer[_MAX_CONNECTION_REQUESTS + 1];
        for (int i = 1; i <= _MAX_CONNECTION_REQUESTS; i++) {
            maxConnectContainer[i] = i;
        }
        return maxConnectContainer;
    }
    
    /**
     * Format phone number to be #########.
     * @param phoneNumber The phone number in a String format.
     * @return The formatted phone number.
     * @throws ParseException Catch any parsing errors. 
     */
    public static String phoneNumFormatter(String phoneNumber) throws ParseException {
        _maskFormatter = new MaskFormatter(PHONE_MASK);
        _maskFormatter.setValueContainsLiteralCharacters(false);
        return _maskFormatter.valueToString(phoneNumber);
        
    }
    
}
