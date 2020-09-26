package net.codejava.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JToggleButton;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;


public class BotGUI extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_5;
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
        

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 650, 650);
        contentPane.add(tabbedPane);

        JPanel panel = new JPanel();
        tabbedPane.addTab("Indeed", null, panel, null);
        panel.setLayout(null);

        JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance()
                .createTitle("Indeed Login Info");
        lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewJgoodiesTitle.setBounds(20, 32, 231, 16);
        panel.add(lblNewJgoodiesTitle);

        JLabel lblNewLabel_1 = new JLabel("First name");
        lblNewLabel_1.setBounds(20, 65, 100, 16);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Last name");
        lblNewLabel_2.setBounds(20, 97, 100, 16);
        panel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Full name");
        lblNewLabel_3.setBounds(20, 128, 91, 16);
        panel.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Email");
        lblNewLabel_4.setBounds(20, 166, 61, 16);
        panel.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Password");
        lblNewLabel_5.setBounds(20, 206, 61, 16);
        panel.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("Phone number");
        lblNewLabel_6.setBounds(20, 244, 91, 16);
        panel.add(lblNewLabel_6);

        textField = new JTextField();
        textField.setBounds(125, 60, 130, 26);
        panel.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(125, 92, 130, 26);
        panel.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(125, 123, 130, 26);
        panel.add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(125, 161, 130, 26);
        panel.add(textField_3);
        textField_3.setColumns(10);

        textField_5 = new JTextField();
        textField_5.setBounds(125, 239, 130, 26);
        panel.add(textField_5);
        textField_5.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(125, 201, 130, 26);
        panel.add(passwordField);
        
        JLabel lblNewJgoodiesTitle_1 = DefaultComponentFactory.getInstance().createTitle("Job Preferences");
        lblNewJgoodiesTitle_1.setBounds(391, 32, 122, 16);
        panel.add(lblNewJgoodiesTitle_1);
        
        JLabel whatJob = new JLabel("What job");
        whatJob.setBounds(285, 65, 61, 16);
        panel.add(whatJob);
        
        JLabel locationOfJob = new JLabel("Location of Job");
        locationOfJob.setBounds(285, 97, 100, 16);
        panel.add(locationOfJob);
        
        JLabel appType = new JLabel("Application type");
        appType.setBounds(285, 128, 108, 16);
        panel.add(appType);
        
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
        
        JButton openFileBtn = new JButton("Upload resume...");
        openFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = openFileChooser.showOpenDialog(contentPane);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = openFileChooser.getSelectedFile();
                        msgLabel.setText("File successfully loaded");
                        file = selectedFile;
                        System.out.println(selectedFile);
                }
                else {
                    msgLabel.setText("No file chosen");
                }
            }
        });
        openFileBtn.setBounds(20, 290, 159, 29);
        panel.add(openFileBtn);
        

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("LinkedIn", null, panel_1, null);

        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Glassdoor", null, panel_2, null);
        panel_2.setLayout(null);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(18, 17, 61, 16);
        panel_2.add(lblNewLabel);
        
        JButton btnNewButton = new JButton("Launch");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JobApplicationData jobAppData = new JobApplicationData();
                jobAppData.firstname = textField.getText();
                jobAppData.lastname = textField_1.getText();
                jobAppData.fullname = textField_2.getText();
                jobAppData.email = textField_3.getText();
                jobAppData.phone = textField_5.getText();
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
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    IB.jobScrape();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnNewButton.setBounds(250, 437, 117, 29);
        panel.add(btnNewButton);
    }
}
