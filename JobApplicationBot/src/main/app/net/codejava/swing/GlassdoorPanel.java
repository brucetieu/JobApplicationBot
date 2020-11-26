package net.codejava.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private static final int _STARTING_PAGE = 0;

    private JTextField _email;
    private JPasswordField _password;
    private JTextField _whatJob;
    private JTextField _jobLoc;
    private JTextField _csvOutputPath;
    private JComboBox<ApplicationType> _appBox;
    private JComboBox<Integer> _pageNumBox;
    private JTabbedPane _tabbedPane;
    private List<JTextField> _listOfTextFields = new ArrayList<>();
    private EmailValidator _emailValidator = new EmailValidator();
    private JobApplicationData _jobAppData;
    private WriteFiles _writeFiles;
    private JobIterator _jobIterator;
    private Pagination _page;
    private ApplicationType _appType;

    /**
     * Initialize a JobApplicationData.
     */
    public GlassdoorPanel() {
        _jobAppData = new JobApplicationData();
    }
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

        // Disable button by default.
        launchButton.setEnabled(false);

        // Enable launch button if all TextFields are filled.
        _validateTextFields(launchButton);

        launchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                           
                _getCompleteFields();
                
                // Run the GlassdoorBot
                new RunGlassdoorBot(_appType, _jobAppData, _jobIterator, _page, _writeFiles);

            }
        });

    }

    /**
     * Add applicant information fields.
     */
    private void _addApplicantFields() {

        createGoodiesTitle("Glassdoor Login Info", 230, 232, 175, 16);
        addLabels("Email", 150, 270, 61, 16);
        addLabels("Password", 150, 308, 61, 16);

        _email = addTextField(280, 265, 130, 26, 10);
        _password = addPasswordField(280, 303, 130, 26, 10);

    }

    /**
     * Add Job preferences fields.
     */
    private void _addJobPreferenceFields() {
        createGoodiesTitle("Job Preferences", 230, 32, 122, 16);
        addLabels("What job", 150, 65, 61, 16);
        addLabels("Location of job", 150, 97, 100, 16);
        addLabels("Application type", 150, 128, 150, 16);
        addLabels("Pages to scrape", 150, 156, 100, 16);
        addLabels("CSV output path", 150, 194, 150, 16);

        _whatJob = addTextField(280, 60, 130, 26, 10);
        _jobLoc = addTextField(280, 92, 130, 26, 10);
        _appBox = addAppTypeDropdown(280, 124, 150, 27);
        _pageNumBox = addDropdown(GUIComponentsHelper.generatePageNumbers(_STARTING_PAGE), 280, 156, 150, 27);
        _csvOutputPath = addTextField(280, 192, 180, 26, 10);
    }

    /**
     * Check if the text fields are completed by listening to each one.
     */
    private void _validateTextFields(JButton launchButton) {

        // Add text field to the list of text fields.
        _listOfTextFields.add(_email);
        _listOfTextFields.add(_password);
        _listOfTextFields.add(_whatJob);
        _listOfTextFields.add(_jobLoc);
        _listOfTextFields.add(_csvOutputPath);

        // Disable launch button if any text fields are blank.
        for (JTextField tf : _listOfTextFields) {
            tf.getDocument().addDocumentListener(new TextFieldListener(_listOfTextFields, launchButton));
        }
    }
    
    private void _getCompleteFields() {
        _writeFiles = null;
        
        // Validate the csv output is actually a csv.
        try {
            if (_csvOutputPath.getText().endsWith(".csv") && _csvOutputPath.getText().length() > 4) {
                _writeFiles = new WriteFiles(_csvOutputPath.getText());
            }
            else {
                MessageDialog.infoBox(MessageDialog.INVALID_CSV_MSG, MessageDialog.INVALID_CSV_TITLE);
                return;
            }
           
        } catch (IOException e2) {
            System.out.println(e2.toString());
        }
        
        _jobAppData.platformUrl = _GLASSDOOR_URL;
        _jobAppData.email = _email.getText();
        _jobAppData.password = String.valueOf(_password.getPassword());
        _jobAppData.whatJob = _whatJob.getText();
        _jobAppData.locationOfJob = _jobLoc.getText();

        // Validate the email.
        if (!_emailValidator.validate(_jobAppData.email.trim())) {
            MessageDialog.infoBox(MessageDialog.INVALID_EMAIL_MSG, MessageDialog.INVALID_EMAIL_TITLE);
            return;
        }

        // Verify a resume has been uploaded.
        try {
            JobApplicationData.resumePath = getResumeFile().toString();
        } catch (Exception e1) {
            MessageDialog.infoBox(MessageDialog.NO_RESUME_MSG, MessageDialog.NO_RESUME_TITLE);
            return;
        }

        JobPostingData.pagesToScrape = Integer.parseInt(_pageNumBox.getSelectedItem().toString());
        _appType = (ApplicationType) _appBox.getSelectedItem();
        _jobIterator = new JobIterator(_writeFiles, _appType);
        _page = new Pagination(_jobAppData);
        
    }

}
