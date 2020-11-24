package net.codejava.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;
import com.btieu.JobApplicationBot.JobIterator;
import com.btieu.JobApplicationBot.JobPostingData;
import com.btieu.JobApplicationBot.Pagination;
import com.btieu.JobApplicationBot.WriteFiles;

/**
 * This class creates the Indeed panel.
 * 
 * @author bruce
 *
 */
public class IndeedPanel extends CreateGUIComponents {

    private static final long serialVersionUID = 1L;
    private static final int _STARTING_PAGE = 0;
    private static final String _INDEED_URL = "https://www.indeed.com/?from=gnav-util-homepage";
    private JTextField _whatJob;
    private JTextField _jobLoc;
    private JTextField _csvOutputPath;
    private JComboBox<ApplicationType> _appBox;
    private JComboBox<Integer> _pageNumBox;
    private JTabbedPane _tabbedPane;
    private List<JTextField> _listOfTextFields = new ArrayList<>();

    /**
     * Create the Indeed panel with labels and application fields.
     * 
     * @param _contentPane A JPanel object.
     */
    public void createIndeedPanel(JPanel _contentPane) {

        createTab("Indeed", _contentPane, _tabbedPane, 0, 0, 0, 0);
        _addJobPreferenceFields(); // Job preferences fields.
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

                JobApplicationData jobAppData = new JobApplicationData();
                WriteFiles writeFiles = null;

                try {
                    writeFiles = new WriteFiles(_csvOutputPath.getText());
                } catch (IOException e2) {
                    System.out.println(e2.toString());
                }

                jobAppData.platformUrl = _INDEED_URL;
                jobAppData.whatJob = _whatJob.getText();
                jobAppData.locationOfJob = _jobLoc.getText();
                JobApplicationData.resumePath = getResumeFile().toString();

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
        _listOfTextFields.add(_whatJob);
        _listOfTextFields.add(_jobLoc);
        _listOfTextFields.add(_csvOutputPath);

        // Disable launch button if any text fields are blank.
        for (JTextField tf : _listOfTextFields) {
            tf.getDocument().addDocumentListener(new TextfieldListener(_listOfTextFields, launchButton));
        }
    }

}
