package net.codejava.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextfieldListener implements DocumentListener {

    private JTextField _textfield;
    private JButton _launchButton;
    
    public TextfieldListener(JTextField textfield, JButton launchButton) {
        _textfield = textfield;
        _launchButton = launchButton;
    }
//    public static String validate(String textInField) {
//        if (textInField.length() == 0) {
//            failedMessage("This field must be filled out");
//            return "";
//        }
//        return textInField;
//    }
//    private JTextField _textField;
//    private String _text;
//    
//    public TextfieldListener(JTextField textField, String text) {
//        _textField = textField;
//        _text = text;
//    }
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (validateFields()) {
//            _text = _textField.getText();
//        }
//        
//    }
//    
//    public boolean validateFields() {
//        if (!validateField(_textField, "This field is required.")) {
//            return false;
//        }
//        return true;
//    }
//    
//    public boolean validateField( JTextField f, String errormsg )
//    {
//      if ( f.getText().equals("") )
//        return failedMessage( f, errormsg );
//      else
//        return true; // validation successful
//    }
//    
//    public static void failedMessage(String errormsg)
//    {
//      JOptionPane.showMessageDialog(null, errormsg); // give user feedback
////      f.requestFocus(); // set focus on field, so user can change
////      return false; // return false, as validation has failed
//    }
// 

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
//        for (JTextField textfield : _listOfTextFields) {
            if (_textfield.getText().isEmpty()) {
                isEnabled = false;
            }
//        }
        _launchButton.setEnabled(isEnabled);
        
    }
}
