package net.codejava.swing;

import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;
import com.btieu.JobApplicationBot.JobIterator;
import com.btieu.JobApplicationBot.JobPostingData;
import com.btieu.JobApplicationBot.Pagination;
import com.btieu.JobApplicationBot.WriteFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;


/**
 * This class creates the Indeed panel.
 * 
 * @author bruce
 *
 */
public class LinkedInPanel extends CreateGUIComponents {

    private static final long serialVersionUID = 1L;
    private JTextField _email;
    private JPasswordField _password;
    private JTextField _keywords;
    private JTextPane _maxConnects; 
    private JTabbedPane _tabbedPane;
    private JTextArea _messageText;

    /**
     * Default constructor.
     */
    public LinkedInPanel() {
    }

    /**
     * Create the Indeed panel with labels and application fields.
     * 
     * @param _contentPane A JPanel object.
     */
    public void createLinkedInPanel(JPanel _contentPane) {

        createTab("LinkedIn", _contentPane, _tabbedPane, 0, 0, 650, 650);
        _addApplicantFields(); // Applicant info fields.
//        _addJobPreferenceFields(); // Job preferences fields.
//        addUploadResume(285, 275, 200, 29);
    }

    /**
     * This method launches the browser and grabs all information from filled out
     * fields.
     */
    public void launchApp() {
        JButton launchButton = addButton("Launch", 280, 525, 117, 29);
        launchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JobApplicationData jobAppData = new JobApplicationData();


                
                jobAppData.email = _email.getText();
                jobAppData.password = String.valueOf(_password.getPassword());
                
                jobAppData.platformUrl = "https://www.indeed.com/?from=gnav-util-homepage";
  
                
                // Run the IndeedBot.


            }
        });

    }

    /**
     * Add applicant information fields.
     */
    private void _addApplicantFields() {
        createGoodiesTitle("Indeed Login Info", 20, 32, 231, 16);
        addLabels("Email", 20, 65, 100, 16);
        addLabels("Password", 20, 97, 100, 16);
        addFixedLabel(20, 90, 500, 200);
    
        _email = addTextField(125, 60, 130, 26, 10);
        _password = addPasswordField(125, 92, 130, 26, 10);
        _messageText = addTextArea(20, 240, 500, 200);
        

    }

}
