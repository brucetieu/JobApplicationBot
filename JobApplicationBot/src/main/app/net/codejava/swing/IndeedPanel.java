package net.codejava.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;


/**
 * This class creates the Indeed panel.
 * 
 * @author bruce
 *
 */
public class IndeedPanel extends CreateGUIComponents {

    private static final long serialVersionUID = 1L;
    private JTextField _firstName;
    private JTextField _lastName;
    private JTextField _fullName;
    private JTextField _email;
    private JTextField _phoneNumber;
    private JPasswordField _password;
    private JTextField _whatJob;
    private JTextField _jobLoc;
    private JTextField _pageNum;
    private JTextField _csvOutputName;
    private JComboBox<ApplicationType> _appBox;
    private JComboBox<Integer> _pageNumBox;
    private JTabbedPane _tabbedPane;

    /**
     * Default constructor.
     */
    public IndeedPanel() {
    }

    /**
     * Create the Indeed panel with labels and application fields.
     * 
     * @param _contentPane A JPanel object.
     */
    public void createIndeedPanel(JPanel _contentPane) {

        createTab("Indeed", _contentPane, _tabbedPane, 0, 0, 0, 0);
        _addApplicantFields(); // Applicant info fields.
        _addJobPreferenceFields(); // Job preferences fields.
        addUploadResume(285, 275, 200, 29);
    }

    /**
     * This method launches the browser.
     */
    public void launchApp() {
        JButton launchButton = addButton("Launch", 250, 437, 117, 29);
        launchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             // TODO: Get the values of the text fields and open browser.
                // E.g for resume file: 
                // JobApplicationData.resumePath = getFile.toString();
             
            }
        });

    }
    
    /**
     * Add applicant information fields.
     */
    private void _addApplicantFields() {
        createGoodiesTitle("Indeed Login Info", 20, 32, 231, 16);
        addLabels("First name", 20, 65, 100, 16);
        addLabels("Last name", 20, 97, 100, 16);
        addLabels("Full name", 20, 128, 100, 16);
        addLabels("Email", 20, 166, 61, 16);
        addLabels("Password", 20, 206, 61, 16);
        addLabels("Phone number", 20, 244, 91, 16);

        _firstName = addTextField(125, 60, 130, 26, 10);
        _lastName = addTextField(125, 92, 130, 26, 10);
        _fullName = addTextField(125, 123, 130, 26, 10);
        _email = addTextField(125, 161, 130, 26, 10);
        _password = addPasswordField(125, 201, 130, 26, 10);
        _phoneNumber = addTextField(125, 239, 130, 26, 10);

    }
    
    /**
     * Add Job preferences fields.
     */
    private void _addJobPreferenceFields() {
        createGoodiesTitle("Job Preferences", 391, 32, 122, 16);
        addLabels("What job", 285, 65, 61, 16);
        addLabels("Location of job", 285, 97, 100, 16);
        addLabels("Application type", 285, 128, 150, 16);
        addLabels("Pages to scrape", 285, 156, 100, 16);
        addLabels("CSV output name", 285, 194, 150, 16);

        _whatJob = addTextField(401, 60, 130, 26, 10);
        _jobLoc = addTextField(401, 92, 130, 26, 10);
        _appBox = addAppTypeDropdown(401, 124, 150, 27);
        _pageNumBox = addDropdown(GUIComponentsHelper.generatePageNumbers(), 401, 156, 150, 27);
        _csvOutputName = addTextField(401, 192, 180, 26, 10);
    }


}
