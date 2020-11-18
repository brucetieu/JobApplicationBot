package net.codejava.swing;

import com.btieu.JobApplicationBot.JobApplicationData;
import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;
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

/**
 * This class contains the methods used to create swing panels, labels, buttons,
 * etc.
 * 
 * @author bruce
 *
 */
public class CreateGUIComponents extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel _panel;
    private JPanel _contentPane;
    private JLabel _lblNewJgoodiesTitle;
    private final JFileChooser _openFileChooser;
    private File _file;
    private JButton _button;
    private JTextField _field;
    private JPasswordField _password;
    private SingletonTab _singletonTab;

    /**
     * Initialize a new JPanel and file chooser object.
     */
    public CreateGUIComponents() {
        _panel = new JPanel();
        _singletonTab = SingletonTab.getInstance();
        _openFileChooser = new JFileChooser();
        this._openFileChooser.setCurrentDirectory(new File("./"));

    }
    
    /**
     * Get the resume file path.
     * @return The resume file.
     */
    public File getResumeFile() {
        return _file;
    }

    /**
     * This method adds JLabels.
     * 
     * @param name   The name of the label to be added.
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     */
    public void addLabels(String name, int x, int y, int width, int height) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, width, height);
        _panel.add(label);
    }

    /**
     * This method adds JTextFields.
     * 
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     * @param column The number of columns of the textfield.
     * @return A JTextField object.
     */
    public JTextField addTextField(int x, int y, int width, int height, int column) {
        _field = new JTextField();
        _field.setBounds(x, y, width, height);
        _panel.add(_field);
        _field.setColumns(column);
        return _field;
    }

    /**
     * This method adds a JPasswordField.
     * 
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     * @param column The number of columns of the textfield.
     * @return A JPasswordField object.
     */
    public JPasswordField addPasswordField(int x, int y, int width, int height, int column) {
        _password = new JPasswordField();
        _password.setBounds(x, y, width, height);
        _panel.add(_password);
        _password.setColumns(column);
        return _password;
    }

    /**
     * This method creates a tab.
     * 
     * @param name        The name of the tab.
     * @param contentPane The content object.
     * @param tabbedPane  The tab object.
     * @param x           The new x-coordinate of the component.
     * @param y           The new y-coordinate of the component.
     * @param width       The new width of the component.
     * @param height      The new height of the component.
     */
    public void createTab(String name, JPanel contentPane, JTabbedPane tabbedPane, int x, int y, int width,
            int height) {
        tabbedPane = _singletonTab.getTabbedPane();
        tabbedPane.setBounds(x, y, width, height);
        contentPane.add(tabbedPane);
        tabbedPane.addTab(name, null, _panel, null);
        _panel.setLayout(null);
    }

    /**
     * This method creates titles.
     * 
     * @param title  The name of the title.
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     */
    public void createGoodiesTitle(String title, int x, int y, int width, int height) {
        _lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle(title);
        _lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
        _lblNewJgoodiesTitle.setBounds(x, y, width, height);
        _panel.add(_lblNewJgoodiesTitle);
    }
    
    /**
     * This method creates a drop down menu of application types.
     * 
     * @param applicationTypes  An array of names which represents options in the dropdown.
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     * @return The ApplicationType combo box. 
     */
    public JComboBox<ApplicationType> addAppTypeDropdown(int x, int y, int width, int height) {
        JComboBox<ApplicationType> comboBox = new JComboBox<ApplicationType>(JobApplicationData.ApplicationType.values());
        comboBox.setBounds(x, y, width, height);
        _panel.add(comboBox);
        return comboBox;
    }


    /**
     * This method creates a drop down menu of numbers.
     * 
     * @param names  An array of names which represents options in the dropdown.
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     * @return The Integer combo box.
     */
    public JComboBox<Integer> addDropdown(Integer[] nums, int x, int y, int width, int height) {
        JComboBox<Integer> comboBox = new JComboBox<Integer>(nums);
        comboBox.setBounds(x, y, width, height);
        _panel.add(comboBox);
        return comboBox;
    }


    /**
     * This method adds a JButton.
     * 
     * @param name   The label displayed on the button.
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     * @return A button object.
     */
    public JButton addButton(String name, int x, int y, int width, int height) {
        _button = new JButton(name);
        _button.setBounds(x, y, width, height);
        _panel.add(_button);
        return _button;
    }

    /**
     * This method adds functionality to upload a resume.
     * 
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     */
    public void addUploadResume(int x, int y, int width, int height) {

        JButton openFileBtn = addButton("Upload resume (PDF only)", 280, 250, 200, 29);
        openFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = _openFileChooser.showOpenDialog(_contentPane);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = _openFileChooser.getSelectedFile();
                    addLabels("File Successfully Loaded!", x, y, width, height);
                    _file = selectedFile;
                } else {
                    addLabels("No file chosen!", x, y, width, height);
                }
            }
        });

    }

}
