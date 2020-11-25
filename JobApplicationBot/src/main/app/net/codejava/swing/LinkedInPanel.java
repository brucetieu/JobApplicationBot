package net.codejava.swing;

import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.LinkedInPerson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class creates the LinkedIn panel.
 * 
 * @author bruce
 *
 */
public class LinkedInPanel extends CreateGUIComponents {

    private static final long serialVersionUID = 1L;
    private static final String _LINKEDIN_URL = "https://www.linkedin.com/login?fromSignIn=true&trk=guest_homepage-basic_nav-header-signin";
    private JTextField _email;
    private JPasswordField _password;
    private JTextField _firstname;
    private JTextField _fullname;
    private JTextField _linkedin;
    private JTextField _keywords;
    private JComboBox<Integer> _maxConnects;
    private JTabbedPane _tabbedPane;
    private JTextArea _messageText;
    private List<JTextField> _listOfTextFields = new ArrayList<>();

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
        _addKeywordsField();
        _addDropdownForMaxConnects();
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
                LinkedInPerson linkedInPerson = new LinkedInPerson();

                jobAppData.email = _email.getText();
                jobAppData.password = String.valueOf(_password.getPassword());
                jobAppData.firstname = _firstname.getText();
                jobAppData.fullname = _fullname.getText();
                jobAppData.platformUrl = _LINKEDIN_URL;
                jobAppData.linkedin = _linkedin.getText();

                linkedInPerson.message = _messageText.getText();
                linkedInPerson.keywords = _keywords.getText();
                LinkedInPerson.MAX_CONNECTIONS = Integer.parseInt(_maxConnects.getSelectedItem().toString());

                // Run the LinkedInBot.
                new RunLinkedInBot(jobAppData, linkedInPerson);

            }
        });

    }

    /**
     * Add applicant information fields.
     */
    private void _addApplicantFields() {
        createGoodiesTitle("LinkedIn Login Info", 20, 32, 231, 16);
        addLabels("Email", 20, 65, 100, 16);
        addLabels("Password", 20, 97, 100, 16);
        addFixedLabel(20, 175, 500, 200);
        addLabels("First Name", 20, 129, 100, 16);
        addLabels("Full Name", 20, 161, 100, 16);
        addLabels("LinkedIn profile", 285, 141, 200, 16);

        _email = addTextField(125, 60, 130, 26, 10);
        _password = addPasswordField(125, 92, 130, 26, 10);
        _messageText = addTextArea(20, 325, 500, 100);
        _firstname = addTextField(125, 124, 130, 26, 10);
        _fullname = addTextField(125, 156, 130, 26, 10);
        _linkedin = addTextField(401, 136, 130, 26, 10);

    }

    /**
     * Add keyword textfield.
     */
    private void _addKeywordsField() {
        createGoodiesTitle("Separate keywords with a comma and space", 250, 32, 300, 16);
        addLabels("Keywords", 285, 65, 61, 16);
        _keywords = addTextField(401, 60, 130, 26, 10);
    }

    private void _addDropdownForMaxConnects() {
        addLabels("Connect requests", 285, 103, 200, 16);
        _maxConnects = addDropdown(GUIComponentsHelper.generateMaxConnectRequests(), 401, 98, 150, 27);
    }
    
    /**
     * Check if the text fields are completed by listening to each one.
     */
    private void _validateTextFields(JButton launchButton) {
        
        // Add text field to the list of text fields.
        _listOfTextFields.add(_email);
        _listOfTextFields.add(_password);
        _listOfTextFields.add(_firstname);
        _listOfTextFields.add(_fullname);
        _listOfTextFields.add(_linkedin);

        // Disable launch button if any text fields are blank.
        for (JTextField tf : _listOfTextFields) {
            tf.getDocument().addDocumentListener(new TextFieldListener(_listOfTextFields, launchButton));
        }
    }


}
