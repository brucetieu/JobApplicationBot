package net.codejava.swing;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Listen for changes in the text field and respond accordingly. Make some fields a requirement.
 * 
 * @author Bruce Tieu
 *
 */
public class TextFieldListener implements DocumentListener {

    private List<JTextField> _listOfTextfields;
    private JButton _launchButton;

    /**
     * Initialize the textfield and launch button which are 'listened' to.
     * 
     * @param textfield    The textfield object.
     * @param launchButton The button which launches the app.
     */
    public TextFieldListener(List<JTextField> listOfTextfields, JButton launchButton) {
        _listOfTextfields = listOfTextfields;
        _launchButton = launchButton;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changedUpdate(e);

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changedUpdate(e);

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        boolean isEnabled = true;
 
        // Enable the launch button if and only if all textfields are filled out.
        for (JTextField tf : _listOfTextfields) {
            if (tf.getText().isEmpty()) {
                isEnabled = false;
            }
        }

        _launchButton.setEnabled(isEnabled);

    }
}
