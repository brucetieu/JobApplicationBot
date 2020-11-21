package net.codejava.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;
import com.btieu.JobApplicationBot.JobIterator;
import com.btieu.JobApplicationBot.JobPostingData;
import com.btieu.JobApplicationBot.Pagination;
import com.btieu.JobApplicationBot.WriteFiles;

/**
 * Create a Glassdoor tab with fields to let users apply to Glassdoor jobs.
 * 
 * @author Bruce Tieu
 *
 */
public class GlassdoorPanel extends CreateGUIComponents {

    private static final long serialVersionUID = 1L;
    private static final String _GLASSDOOR_URL = "https://www.glassdoor.com/index.htm";

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
    private JTextField _csvOutputPath;
    private JComboBox<ApplicationType> _appBox;
    private JComboBox<Integer> _pageNumBox;
    private JTabbedPane _tabbedPane;
    private List<JTextField> _listOfLGFields = new ArrayList<>();
    private List<JTextField> _listOfNonLGFields = new ArrayList<>();

    /**
     * Create the Glassdoor panel.
     * 
     * @param _contentPane The panel for storing content.
     */
    public void createGlassdoorPanel(JPanel _contentPane) {

        createTab("Glassdoor", _contentPane, _tabbedPane, 0, 0, 650, 650);
        _addApplicantFields();
        _addJobPreferenceFields();
        addUploadResume(210, 475, 200, 29);
    }

    /**
     * This method launches the browser and grabs all information from filled out
     * fields.
     */
    public void launchApp() {
        JButton launchButton = addButton("Launch", 245, 525, 117, 29);

        launchButton.setEnabled(false);
        
        
        ApplicationType appType = (ApplicationType) _appBox.getSelectedItem();
        _validateTextFields(launchButton, _appBox);

        launchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JobApplicationData jobAppData = new JobApplicationData();
                WriteFiles writeFiles = null;
                try {
                    writeFiles = new WriteFiles(_csvOutputPath.getText());
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                jobAppData.firstname = _firstName.getText();
                jobAppData.lastname = _lastName.getText();
                jobAppData.fullname = _fullName.getText();
                jobAppData.email = _email.getText();
                jobAppData.password = String.valueOf(_password.getPassword());
                jobAppData.school = _school.getText();
                jobAppData.location = _location.getText();
                jobAppData.currentCompany = _company.getText();
                jobAppData.linkedin = _linkedIn.getText();
                jobAppData.github = _github.getText();
                jobAppData.portfolio = _portfolio.getText();

                jobAppData.phone = null;
                try {
                    jobAppData.phone = GUIComponentsHelper.phoneNumFormatter(_phoneNumber.getText());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                JobApplicationData.resumePath = getResumeFile().toString();
                jobAppData.platformUrl = _GLASSDOOR_URL;

                jobAppData.whatJob = _whatJob.getText();
                jobAppData.locationOfJob = _jobLoc.getText();
                JobPostingData.pagesToScrape = Integer.parseInt(_pageNumBox.getSelectedItem().toString());
//                ApplicationType appType = (ApplicationType) _appBox.getSelectedItem();
                JobIterator jobIterator = new JobIterator(writeFiles, appType);
                Pagination page = new Pagination(jobAppData);

                // Run the GlassdoorBot
                new RunGlassdoorBot(appType, jobAppData, jobIterator, page, writeFiles);

            }
        });

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

        createGoodiesTitle("Glassdoor Login Info", 391, 232, 175, 16);
        addLabels("Email", 285, 270, 61, 16);
        addLabels("Password", 285, 308, 61, 16);

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

        _email = addTextField(401, 265, 130, 26, 10);
        _password = addPasswordField(401, 303, 130, 26, 10);

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
        addLabels("CSV output path", 285, 194, 150, 16);

        _whatJob = addTextField(401, 60, 130, 26, 10);
        _jobLoc = addTextField(401, 92, 130, 26, 10);
        _appBox = addAppTypeDropdown(401, 124, 150, 27);
        _pageNumBox = addDropdown(GUIComponentsHelper.generatePageNumbers(0), 401, 156, 150, 27);
        _csvOutputPath = addTextField(401, 192, 180, 26, 10);
    }

    private void _validateTextFields(JButton launchButton, JComboBox<ApplicationType> comboBox) {
        comboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                JComboBox<ApplicationType> comboBox = (JComboBox<ApplicationType>) e.getSource();
                Object selected = comboBox.getSelectedItem();
                launchButton.setEnabled(false);
                if (selected == ApplicationType.LEVER_GREENHOUSE) {
                   
                
                    _listOfLGFields.add(_firstName);
                    _listOfLGFields.add(_lastName);
                    _listOfLGFields.add(_fullName);
                    _listOfLGFields.add(_phoneNumber);
                    _listOfLGFields.add(_location);
                    
                    // Disable launch button if any text fields are blank.
                    for (JTextField tf : _listOfLGFields) {
                        tf.getDocument().addDocumentListener(new TextfieldListener(_listOfLGFields, launchButton));
                    }
                }
                else {
                   
                  
                    _listOfNonLGFields.add(_whatJob);
                    _listOfNonLGFields.add(_jobLoc);
                    _listOfNonLGFields.add(_csvOutputPath);
                    _listOfNonLGFields.add(_email);
                    _listOfNonLGFields.add(_password);
                    
                    // Disable launch button if any text fields are blank.
                    for (JTextField tf : _listOfNonLGFields) {
                        tf.getDocument().addDocumentListener(new TextfieldListener(_listOfNonLGFields, launchButton));
                    }
                }
            }
            
        });
        
//        if (appType == ApplicationType.LEVER_GREENHOUSE) {
//            _listOfLGFields.add(_firstName);
//            _listOfLGFields.add(_lastName);
//            _listOfLGFields.add(_fullName);
//            _listOfLGFields.add(_phoneNumber);
//            _listOfLGFields.add(_location);
//            
//            // Disable launch button if any text fields are blank.
//            for (JTextField tf : _listOfLGFields) {
//                tf.getDocument().addDocumentListener(new TextfieldListener(_listOfLGFields, launchButton));
//            }
//        }
//        else {
//            _listOfNonLGFields.add(_whatJob);
//            _listOfNonLGFields.add(_jobLoc);
//            _listOfNonLGFields.add(_csvOutputPath);
//            _listOfNonLGFields.add(_email);
//            _listOfNonLGFields.add(_password);
//            
//            // Disable launch button if any text fields are blank.
//            for (JTextField tf : _listOfNonLGFields) {
//                tf.getDocument().addDocumentListener(new TextfieldListener(_listOfNonLGFields, launchButton));
//            }
//        }
    }

}
