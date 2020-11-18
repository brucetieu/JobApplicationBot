package net.codejava.swing;

import javax.swing.JTabbedPane;

/**
 * Singleton class - Only create one tab in the GUI, not multiple.
 * 
 * @author Bruce Tieu
 *
 */
public class SingletonTab {

    private static SingletonTab _singleInstance = null;
    private JTabbedPane _tabbedPane;

    /**
     * Instantiate the JTabbedPane object.
     */
    private SingletonTab() {
        _tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    }

    /**
     * Ensure only one instance of this class is created.
     * 
     * @return The single instance.
     */
    public static SingletonTab getInstance() {
        if (_singleInstance == null) {
            _singleInstance = new SingletonTab();
        }
        return _singleInstance;
    }

    /**
     * Get the tabbed pane.
     * 
     * @return The JTabbedPane object.
     */
    public JTabbedPane getTabbedPane() {
        return _tabbedPane;
    }

}
