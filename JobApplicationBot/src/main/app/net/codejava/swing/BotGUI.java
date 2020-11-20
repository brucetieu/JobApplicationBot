package net.codejava.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Create the GUI.
 * 
 * @author bruce
 *
 */
public class BotGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int _GUI_X_AXIS = 0;
    private static final int _GUI_Y_AXIS = 0;
    private static final int _GUI_WIDTH = 650;
    private static final int _GUI_HEIGHT = 650;
    private JPanel _contentPane;
    private IndeedPanel _indeedPanel;
    private GlassdoorPanel _glassdoorPanel;
    private static final String _GUI_TITLE = "Job Application Bot";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IndeedPanel indeedPanel = new IndeedPanel();
                    GlassdoorPanel glassdoorPanel = new GlassdoorPanel();
                    BotGUI frame = new BotGUI(indeedPanel, glassdoorPanel);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the Desktop app.
     * 
     * @param indeedPanel    Object which creates the indeed panel.
     * @param glassdoorPanel Object which creates the Glassdoor panel.
     */
    public BotGUI(IndeedPanel indeedPanel, GlassdoorPanel glassdoorPanel) {
        super(_GUI_TITLE);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(_GUI_X_AXIS, _GUI_Y_AXIS, _GUI_WIDTH, _GUI_HEIGHT);
        setLocationRelativeTo(null);
       
        this._contentPane = new JPanel();
        setContentPane(this._contentPane);
        this._contentPane.setLayout(null);

        this._indeedPanel = indeedPanel;
        this._indeedPanel.createIndeedPanel(this._contentPane);
        this._indeedPanel.launchApp();

        this._glassdoorPanel = glassdoorPanel;
        this._glassdoorPanel.createGlassdoorPanel(this._contentPane);
        this._glassdoorPanel.launchApp();

    }

}
