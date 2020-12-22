package hcmus.edu.project02;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIRegister extends JFrame implements ActionListener {
    // Window width, height
    public static int DEFAULT_WIDTH = 350;
    public static int DEFAULT_HEIGHT = 170;
    public static int DEFAULT_COMPONENT_HEIGHT = 25;
    public static int DEFAULT_BTN_WIDTH = 100;
    public static int DEFAULT_INPUT_COLUMN = 20;
    public static EmptyBorder DEFAULT_EMPTY_BORDER = new EmptyBorder(10, 10, 10, 10);
    public static Dimension INPUT_FIELD_MAX_SIZE = new Dimension(Integer.MAX_VALUE, DEFAULT_COMPONENT_HEIGHT);
    public static Dimension BUTTON_MAX_SIZE = new Dimension(DEFAULT_BTN_WIDTH, DEFAULT_COMPONENT_HEIGHT);

    // Large headerLabel
    JLabel headerLabel = new JLabel("Login");

    public GUIRegister() {
        // Set title for window
        setTitle("Login/Register");
        // Setting the width and height of frame
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set main layout
        setLayout(new BorderLayout());

        // Create panel contains Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        add(headerPanel, BorderLayout.NORTH);
        // Put headerLabel to center of headerPanel
        setupHeader(headerPanel);

        // Create panel contains login form
        JPanel labelPanel = new JPanel();
        add(labelPanel, BorderLayout.WEST);
        JPanel tfPanel = new JPanel();
        add(tfPanel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.SOUTH);
        // Put components into form
        initializeForm(labelPanel, tfPanel, btnPanel);

        // Setting the frame visibility to true
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void setupHeader(JPanel headerPanel) {
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerPanel.add(headerLabel);
    }


    public void initializeForm(JPanel labelPanel, JPanel tfPanel, JPanel btnPanel) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
