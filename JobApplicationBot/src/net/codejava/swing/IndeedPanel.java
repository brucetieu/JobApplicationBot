package net.codejava.swing;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class IndeedPanel extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final JFileChooser _openFileChooser;
    private JPanel _panel;
    private JTextField _firstNameField;
    private JTextField _lastNameLabel;
    private JTextField _fullNameField;
    private JTextField _emailField;
    private JTextField _phoneNumberField;
    private JPasswordField _passwordField;
    private JTextField _whatJobField;
    private JTextField _locationOfJobField;
    private File _file;
    private JTabbedPane _tabbedPane;

    public IndeedPanel() {
        this._openFileChooser = new JFileChooser();

    }

    public void createIndeedPanel(JPanel _contentPane) {
//      this.openFileChooser = new JFileChooser();
        this._openFileChooser.setCurrentDirectory(new File("./"));

        // Create a tabbed frame.
        _tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        _tabbedPane.setBounds(0, 0, 650, 650);
        _contentPane.add(_tabbedPane);

        // One for Indeed
        _panel = new JPanel();
        _tabbedPane.addTab("Indeed", null, _panel, null);
        _panel.setLayout(null);

        JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Indeed Login Info");
        lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewJgoodiesTitle.setBounds(20, 32, 231, 16);
        _panel.add(lblNewJgoodiesTitle);

        // Applicant information fields.
        JLabel firstName = new JLabel("First name");
        firstName.setBounds(20, 65, 100, 16);
        _panel.add(firstName);

        JLabel lastName = new JLabel("Last name");
        lastName.setBounds(20, 97, 100, 16);
        _panel.add(lastName);

        JLabel fullName = new JLabel("Full name");
        fullName.setBounds(20, 128, 91, 16);
        _panel.add(fullName);

        JLabel email = new JLabel("Email");
        email.setBounds(20, 166, 61, 16);
        _panel.add(email);

        JLabel lblNewLabel_5 = new JLabel("Password");
        lblNewLabel_5.setBounds(20, 206, 61, 16);
        _panel.add(lblNewLabel_5);

        JLabel phoneNumber = new JLabel("Phone number");
        phoneNumber.setBounds(20, 244, 91, 16);
        _panel.add(phoneNumber);

        _firstNameField = new JTextField();
        _firstNameField.setBounds(125, 60, 130, 26);
        _panel.add(_firstNameField);
        _firstNameField.setColumns(10);

        _lastNameLabel = new JTextField();
        _lastNameLabel.setBounds(125, 92, 130, 26);
        _panel.add(_lastNameLabel);
        _lastNameLabel.setColumns(10);

        _fullNameField = new JTextField();
        _fullNameField.setBounds(125, 123, 130, 26);
        _panel.add(_fullNameField);
        _fullNameField.setColumns(10);

        _emailField = new JTextField();
        _emailField.setBounds(125, 161, 130, 26);
        _panel.add(_emailField);
        _emailField.setColumns(10);

        _phoneNumberField = new JTextField();
        _phoneNumberField.setBounds(125, 239, 130, 26);
        _panel.add(_phoneNumberField);
        _phoneNumberField.setColumns(10);

        _passwordField = new JPasswordField();
        _passwordField.setBounds(125, 201, 130, 26);
        _panel.add(_passwordField);

        // Job preferences column.
        JLabel lblNewJgoodiesTitle_1 = DefaultComponentFactory.getInstance().createTitle("Job Preferences");
        lblNewJgoodiesTitle_1.setBounds(391, 32, 122, 16);
        _panel.add(lblNewJgoodiesTitle_1);

        JLabel whatJob = new JLabel("What job");
        whatJob.setBounds(285, 65, 61, 16);
        _panel.add(whatJob);

        JLabel locationOfJob = new JLabel("Location of Job");
        locationOfJob.setBounds(285, 97, 100, 16);
        _panel.add(locationOfJob);

        JLabel appTypeDropdown = new JLabel("Application type");
        appTypeDropdown.setBounds(285, 128, 108, 16);
        _panel.add(appTypeDropdown);

        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setBounds(391, 124, 122, 27);
        comboBox.addItem("Easily Apply");
        _panel.add(comboBox);

        _whatJobField = new JTextField();
        _whatJobField.setBounds(401, 60, 130, 26);
        _panel.add(_whatJobField);
        _whatJobField.setColumns(10);

        _locationOfJobField = new JTextField();
        _locationOfJobField.setBounds(397, 92, 130, 26);
        _panel.add(_locationOfJobField);
        _locationOfJobField.setColumns(10);

        JLabel msgLabel = new JLabel("");
        msgLabel.setBounds(191, 295, 340, 16);
        _panel.add(msgLabel);

        // Add functionality to upload resume.
        JButton openFileBtn = new JButton("Upload resume...");
        openFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = _openFileChooser.showOpenDialog(_contentPane);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = _openFileChooser.getSelectedFile();
                    msgLabel.setText("File successfully loaded");
                    _file = selectedFile;
                    System.out.println(selectedFile);
                } else {
                    msgLabel.setText("No file chosen");
                }
            }
        });
        openFileBtn.setBounds(20, 290, 159, 29);
        _panel.add(openFileBtn);
    }

    public void launchApp() {
        // Run the IndeedBot when "Launch" button is clicked.
        JButton launchBtn = new JButton("Launch");
        launchBtn.setBounds(250, 437, 117, 29);
        _panel.add(launchBtn);
    }

}
