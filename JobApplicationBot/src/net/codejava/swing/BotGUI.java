package net.codejava.swing;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import org.apache.commons.io.FileUtils;

public class BotGUI extends JFrame {

    private JPanel contentPane;
    private JTextField firstNameField;
    private JTextField lastNameLabel;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JPasswordField passwordField;
    private JTextField whatJobField;
    private JTextField locationOfJobField;
    private final JFileChooser openFileChooser;
    private File file;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BotGUI frame = new BotGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public BotGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 100, 650, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        openFileChooser = new JFileChooser();
        openFileChooser.setCurrentDirectory(new File("./"));

        // Create a tabbed frame.
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 650, 650);
        contentPane.add(tabbedPane);

        // One for Indeed
        JPanel panel = new JPanel();
        tabbedPane.addTab("Indeed", null, panel, null);
        panel.setLayout(null);

        JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Indeed Login Info");
        lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewJgoodiesTitle.setBounds(20, 32, 231, 16);
        panel.add(lblNewJgoodiesTitle);

        // Applicant information fields.
        JLabel firstName = new JLabel("First name");
        firstName.setBounds(20, 65, 100, 16);
        panel.add(firstName);

        JLabel lastName = new JLabel("Last name");
        lastName.setBounds(20, 97, 100, 16);
        panel.add(lastName);

        JLabel fullName = new JLabel("Full name");
        fullName.setBounds(20, 128, 91, 16);
        panel.add(fullName);

        JLabel email = new JLabel("Email");
        email.setBounds(20, 166, 61, 16);
        panel.add(email);

        JLabel lblNewLabel_5 = new JLabel("Password");
        lblNewLabel_5.setBounds(20, 206, 61, 16);
        panel.add(lblNewLabel_5);

        JLabel phoneNumber = new JLabel("Phone number");
        phoneNumber.setBounds(20, 244, 91, 16);
        panel.add(phoneNumber);

        firstNameField = new JTextField();
        firstNameField.setBounds(125, 60, 130, 26);
        panel.add(firstNameField);
        firstNameField.setColumns(10);

        lastNameLabel = new JTextField();
        lastNameLabel.setBounds(125, 92, 130, 26);
        panel.add(lastNameLabel);
        lastNameLabel.setColumns(10);

        fullNameField = new JTextField();
        fullNameField.setBounds(125, 123, 130, 26);
        panel.add(fullNameField);
        fullNameField.setColumns(10);

        emailField = new JTextField();
        emailField.setBounds(125, 161, 130, 26);
        panel.add(emailField);
        emailField.setColumns(10);

        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(125, 239, 130, 26);
        panel.add(phoneNumberField);
        phoneNumberField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(125, 201, 130, 26);
        panel.add(passwordField);

        // Job preferences column.
        JLabel lblNewJgoodiesTitle_1 = DefaultComponentFactory.getInstance().createTitle("Job Preferences");
        lblNewJgoodiesTitle_1.setBounds(391, 32, 122, 16);
        panel.add(lblNewJgoodiesTitle_1);

        JLabel whatJob = new JLabel("What job");
        whatJob.setBounds(285, 65, 61, 16);
        panel.add(whatJob);

        JLabel locationOfJob = new JLabel("Location of Job");
        locationOfJob.setBounds(285, 97, 100, 16);
        panel.add(locationOfJob);

        JLabel appTypeDropdown = new JLabel("Application type");
        appTypeDropdown.setBounds(285, 128, 108, 16);
        panel.add(appTypeDropdown);

        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setBounds(391, 124, 122, 27);
        comboBox.addItem("Easily Apply");
        panel.add(comboBox);

        whatJobField = new JTextField();
        whatJobField.setBounds(401, 60, 130, 26);
        panel.add(whatJobField);
        whatJobField.setColumns(10);

        locationOfJobField = new JTextField();
        locationOfJobField.setBounds(397, 92, 130, 26);
        panel.add(locationOfJobField);
        locationOfJobField.setColumns(10);

        JLabel msgLabel = new JLabel("");
        msgLabel.setBounds(191, 295, 340, 16);
        panel.add(msgLabel);

        // Add functionality to upload resume.
        JButton openFileBtn = new JButton("Upload resume...");
        openFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = openFileChooser.showOpenDialog(contentPane);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = openFileChooser.getSelectedFile();
                    msgLabel.setText("File successfully loaded");
                    file = selectedFile;
                    System.out.println(selectedFile);
                } else {
                    msgLabel.setText("No file chosen");
                }
            }
        });
        openFileBtn.setBounds(20, 290, 159, 29);
        panel.add(openFileBtn);

        // LinkedIn tab
        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("LinkedIn", null, panel_1, null);

        // Glassdoor tab
        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Glassdoor", null, panel_2, null);
        panel_2.setLayout(null);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(18, 17, 61, 16);
        panel_2.add(lblNewLabel);

        // Run the IndeedBot when "Launch" button is clicked.
        JButton launchBtn = new JButton("Launch");
        launchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JobApplicationData jobAppData = new JobApplicationData();
                jobAppData.firstname = firstNameField.getText();
                jobAppData.lastname = lastNameLabel.getText();
                jobAppData.fullname = fullNameField.getText();
                jobAppData.email = emailField.getText();
                jobAppData.phone = phoneNumberField.getText();
                jobAppData.resumePath = file.toString();
                jobAppData.url = "https://www.indeed.com/?from=gnav-util-homepage";
                jobAppData.password = String.valueOf(passwordField.getPassword());
                jobAppData.whatJob = whatJobField.getText();
                jobAppData.locationOfJob = locationOfJobField.getText();

                System.out.println(jobAppData.resumePath);
                // Create an IndeedBot to apply for jobs.
                IndeedBot IB = new IndeedBot(jobAppData, JobApplicationData.ApplicationType.EASILY_APPLY);
                IB.navigateToUrl();
                try {
                    IB.searchJobs();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    IB.jobScrape();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        launchBtn.setBounds(250, 437, 117, 29);
        panel.add(launchBtn);
    }
}
