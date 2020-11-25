/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

/**
 * This class contains the methods used to create swing panels, labels, buttons,
 * etc.
 * 
 * @author bruce
 *
 */
public class CreateGUIComponents extends JFrame {


    private static final long serialVersionUID = 1L;
    private static final int MAX_CHARACTERS = 270;
    private JPanel _panel;
    private JPanel _contentPane;
    private JLabel _lblNewJgoodiesTitle;
    private final JFileChooser _openFileChooser;
    private File _file;
    private JButton _button;
    private JTextField _field;
    private JPasswordField _password;
    private SingletonTab _singletonTab;
    private JTextArea _textArea;
    private JTextArea _changeLog;


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
    public JLabel addLabels(String name, int x, int y, int width, int height) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, width, height);
        _panel.add(label);
        return label;
    }
    
    /**
     * Add a fixed label which will go specifically in the LinkedInPanel.
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     */
    public void addFixedLabel(int x, int y, int width, int height) {
        JLabel label = new JLabel();
        label.setText("<html><body>Write your message like the format below, making sure that there's always a \"Sincerely, \" at the end, "
                + "or an ending greeting of your choice. The beginning of the message will be substituted with \"Hi, 'person's name', \" followed by your message. </body></html>");
        label.setBounds(x, y, width, height);
        _panel.add(label);
    }
    
    /**
     * Add a text area so the user can assemble a message.
     * @param x      The new x-coordinate of the component.
     * @param y      The new y-coordinate of the component.
     * @param width  The new width of the component.
     * @param height The new height of the component.
     * @return The text area as an object.
     */
    public JTextArea addTextArea(int x, int y, int width, int height) {
        _textArea = new JTextArea(
                "Example: \"your profile appeared in my search of software engineers. I am currently pursuing a career in software engineering and it would be great to hear about your journey and experience in the field. Kindly, accept my invitation. You would be a big help! Sincerely, \"",
                100, 100);
        _textArea.setLineWrap(true);
        _textArea.setWrapStyleWord(true);
        _textArea.setBounds(x, y, width, height);

        AbstractDocument pDoc = (AbstractDocument) _textArea.getDocument();
        
        // Set max num of characters text area can have.
        pDoc.setDocumentFilter(new DocumentSizeFilter(MAX_CHARACTERS));

        // Create the text area for the status log and configure it.
        _changeLog = new JTextArea(5, 30);
        _changeLog.setEditable(false);
        JScrollPane scrollPaneForLog = new JScrollPane(_changeLog);
        createGoodiesTitle("Character count", 20, 440, 122, 16);
        scrollPaneForLog.setBounds(20, 460, 300, 50);

        pDoc.addDocumentListener(new CharLengthDocumentListener(_changeLog));

        _panel.add(scrollPaneForLog);
        _panel.add(_textArea);
        return _textArea;
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
        _field.setColumns(column);
        _panel.add(_field);
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
        comboBox.removeItemAt(comboBox.getItemCount() - 1);  // Remove LEVER_GREENHOUSE option.
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

        JLabel chosenFilelabel = addLabels("", x, y, width, height);
        
        JButton openFileBtn = addButton("Upload resume (PDF only)", 200, 450, 200, 29);
        openFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = _openFileChooser.showOpenDialog(_contentPane);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = _openFileChooser.getSelectedFile();
                    chosenFilelabel.setText("File successfully loaded!");
                    _file = selectedFile;
                } else {
                    chosenFilelabel.setText("");
                    chosenFilelabel.setText("No file chosen!");
                }
            }
        });

    }

}
