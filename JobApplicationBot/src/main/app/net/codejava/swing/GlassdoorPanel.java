package net.codejava.swing;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

/**
 * Create a Glassdoor tab with fields to let users apply to Glassdoor jobs.
 * 
 * @author Bruce Tieu
 *
 */
public class GlassdoorPanel extends CreateGUIComponents {

    private static final long serialVersionUID = 1L;
    
    private JTextField _firstName;
    private JTextField _lastName;
    private JTextField _fullName;
    private JTextField _email;
    private JTextField _phoneNumber;
    private JPasswordField _password;
    private JTextField _school;
    private JTextField _location;
    private JTextField _company;
    private JTextField _linkedIn;
    private JTextField _github;
    private JTextField _portfolio;
    private JTextField _whatJob;
    private JTextField _jobLoc;
    private JTextField _pageNum;
    private JTextField _csvOutputName;
    private JComboBox<ApplicationType> _appBox;
    private JComboBox<Integer> _pageNumBox;
    private JTabbedPane _tabbedPane;


    /**
     * Create the Glassdoor panel.
     * @param _contentPane The panel for storing content.
     */
    public void createGlassdoorPanel(JPanel _contentPane) {
        
        createTab("Glassdoor", _contentPane, _tabbedPane, 0, 0, 650, 650);
        _addApplicantFields();
        addUploadResume(285, 275, 200, 29);
    }
    
    
    
    /**
     * Add applicant information fields.
     */
    private void _addApplicantFields() {
        
        createGoodiesTitle("Fill below for LEVER_GREENHOUSE", 10, 33, 250, 16);
        addLabels("First name", 20, 65, 100, 16);
        addLabels("Last name", 20, 97, 100, 16);
        addLabels("Full name", 20, 128, 100, 16);
        addLabels("Phone number", 20, 161, 100, 16);
        addLabels("School", 20, 193, 100, 16);
        addLabels("Location", 20, 225, 91, 16);
        addLabels("Company", 20, 257, 91, 16);
        addLabels("LinkedIn", 20, 289, 91, 16);
        addLabels("GitHub", 20, 321, 91, 16);
        addLabels("Portfolio", 20, 353, 91, 16);
        
        createGoodiesTitle("Glassdoor Login Info", 10, 405, 175, 16);
        addLabels("Email", 20, 437, 61, 16);
        addLabels("Password", 20, 469, 61, 16);

        _firstName = addTextField(125, 60, 130, 26, 10);
        _lastName = addTextField(125, 92, 130, 26, 10);
        _fullName = addTextField(125, 123, 130, 26, 10);
        _phoneNumber = addTextField(125, 156, 130, 26, 10);
        _school = addTextField(125, 188, 130, 26, 10);
        _location = addTextField(125, 220, 130, 26, 10);
        _company = addTextField(125, 252, 130, 26, 10);
        _linkedIn = addTextField(125, 284, 130, 26, 10);
        _github = addTextField(125, 316, 130, 26, 10);
        _portfolio = addTextField(125, 348, 130, 26, 10);
        
        _email = addTextField(125, 432, 130, 26, 10);
        _password = addPasswordField(125, 464, 130, 26, 10);

    }


}
