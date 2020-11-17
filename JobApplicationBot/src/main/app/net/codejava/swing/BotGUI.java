package net.codejava.swing;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Create the GUI.
 * 
 * @author Bruce Tieu
 *
 */
public class BotGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel _contentPane;
    private IndeedPanel _indeedPanel;
    private GlassdoorPanel _glassdoorPanel;

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
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the Desktop app.
     * @param indeedPanel object which creates the indeed panel.
     */
    public BotGUI(IndeedPanel indeedPanel, GlassdoorPanel glassdoorPanel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 720, 720);
        this._contentPane = new JPanel();
        this._contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
