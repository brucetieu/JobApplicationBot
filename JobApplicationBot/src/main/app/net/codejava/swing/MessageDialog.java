package net.codejava.swing;

import javax.swing.JOptionPane;

/**
 * Generate a message dialog as the user interacts with the GUI.
 * @author bruce
 *
 */
public class MessageDialog {
    
    public static final String INDEED_EASY_APPLY_MSG = "Saving Indeed Easily Apply jobs...";
    public static final String INDEED_NOT_EASY_APPLY_MSG = "Saving Indeed Not Easy Apply jobs...";
    public static final String INDEED_ALL_MSG = "Saving all Indeed jobs...";
    public static final String SUCCESS_JOB_SAVE_MSG = "Job successfully saved. You may search again with a different config or close the app.";
    public static final String SUCCESS_MSG = "Success";
    public static final String SUCCESSFUL_LAUNCH_MSG = "Successful launch";
    public static final String INVALID_LEVER_GREENHOUSE_MSG = "Error: LEVER_GREENHOUSE is invalid. Select from EASILY_APPLY, NOT_EASY_APPLY, ALL";
    public static final String INVALID_OPTION_MSG = "Invalid Option";
    

    /**
     * Generate messages for the message dialog box.
     * @param infoMessage The message.
     * @param titleBar The title of the message.
     */
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
