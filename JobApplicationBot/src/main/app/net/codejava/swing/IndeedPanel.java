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
import javax.swing.JTextField;


/**
 * This class creates the Indeed panel.
 * 
 * @author bruce
 *
 */
public class IndeedPanel extends CreateGUIComponents {

    private static final long serialVersionUID = 1L;
    private JTextField _email;
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
     * This method launches the browser and grabs all information from filled out
     * fields.
     */
    public void launchApp() {
        JButton launchButton = addButton("Launch", 280, 525, 117, 29);
        launchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JobApplicationData jobAppData = new JobApplicationData();
                WriteFiles writeFiles = null;
                ;
                try {
                    writeFiles = new WriteFiles(_csvOutputName.getText());
                } catch (IOException e2) {
                    System.out.println(e2.toString());
                }
                
                jobAppData.email = _email.getText();
                jobAppData.password = String.valueOf(_password.getPassword());
                
                JobApplicationData.resumePath = getResumeFile().toString();
                jobAppData.platformUrl = "https://www.indeed.com/?from=gnav-util-homepage";
                jobAppData.whatJob = _whatJob.getText();
                jobAppData.locationOfJob = _jobLoc.getText();
                JobPostingData.pagesToScrape = Integer.parseInt(_pageNumBox.getSelectedItem().toString());
                ApplicationType appType = (ApplicationType) _appBox.getSelectedItem();
                JobIterator jobIterator = new JobIterator(writeFiles, appType);
                Pagination page = new Pagination(jobAppData);
                
                // Run the IndeedBot.
                new RunIndeedBot(appType, jobAppData, jobIterator, page);

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

        _email = addTextField(125, 60, 130, 26, 10);
        _password = addPasswordField(125, 92, 130, 26, 10);

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
        _csvOutputName = addTextField(401, 192, 180, 26, 10);
    }
}
