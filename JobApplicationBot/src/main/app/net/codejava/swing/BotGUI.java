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

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IndeedPanel indeedPanel = new IndeedPanel();
                    BotGUI frame = new BotGUI(indeedPanel);
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
    public BotGUI(IndeedPanel indeedPanel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 100, 650, 650);
        this._contentPane = new JPanel();
        this._contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(this._contentPane);
        this._contentPane.setLayout(null);

        this._indeedPanel = indeedPanel;
        this._indeedPanel.createIndeedPanel(this._contentPane);
        this._indeedPanel.launchApp();

    }

}
