package net.codejava.swing;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Create a Glassdoor tab with fields to let users apply to Glassdoor jobs.
 * 
 * @author Bruce Tieu
 *
 */
public class GlassdoorPanel extends CreateGUIComponents {

    private static final long serialVersionUID = 1L;
    private JTabbedPane _tabbedPane;

    /**
     * Create the Glassdoor panel.
     * @param _contentPane The panel for storing content.
     */
    public void createGlassdoorPanel(JPanel _contentPane) {

        createTab("Glassdoor", _contentPane, _tabbedPane, 0, 0, 650, 650);
        addUploadResume(285, 275, 200, 29);
    }

}
