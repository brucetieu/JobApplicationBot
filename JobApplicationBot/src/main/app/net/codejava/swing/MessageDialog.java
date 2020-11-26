package net.codejava.swing;

import javax.swing.JOptionPane;

/**
 * Generate a message dialog as the user interacts with the GUI.
 * @author bruce
 *
 */
public class MessageDialog {
    
    public static final String INDEED_EASY_APPLY_MSG = "Saving Indeed Easily Apply jobs... Click Ok to continue.";
    public static final String INDEED_NOT_EASY_APPLY_MSG = "Saving Indeed Not Easy Apply jobs... Cick Ok to continue.";
    public static final String INDEED_ALL_MSG = "Saving all Indeed jobs... Click Ok to continue.";
    
    public static final String GLASSDOOR_EASY_APPLY_MSG = "Saving Glassdoor Easily Apply jobs... Click Ok to continue.";
    public static final String GLASSDOOR_NOT_EASY_APPLY_MSG = "Saving Glassdoor Not Easily Apply jobs... Click Ok to continue.";
    public static final String GLASSDOOR_ALL_MSG = "Saving all Glasdoor jobs... Click Ok to continue.";
    
    public static final String SUCCESS_JOB_SAVE_MSG = "Job successfully saved. You may search again with a different config or close the app.";
    public static final String SUCCESS_TITLE = "Success";
    public static final String SUCCESSFUL_LAUNCH_TITLE = "Successful launch";
    
    public static final String INVALID_EMAIL_MSG = "Invalid email format! Try again.";
    public static final String INVALID_EMAIL_TITLE = "Invalid email";
    
    public static final String NO_RESUME_MSG = "Please upload a resume! Try again.";
    public static final String NO_RESUME_TITLE = "No resume file supplied.";
    
    public static final String INVALID_CSV_MSG = "Path specified does not end with .csv!";
    public static final String INVALID_CSV_TITLE = "Invalid upload file format";
    
    public static final String INVALID_PHONE_MSG = "Incorrect phone number format! Try again.";
    public static final String INVALID_PHONE_TITLE = "Invalid phone number format";
    

    /**
     * Generate messages for the message dialog box.
     * @param infoMessage The message.
     * @param titleBar The title of the message.
     */
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
