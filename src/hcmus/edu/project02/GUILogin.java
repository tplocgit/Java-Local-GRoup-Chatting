package hcmus.edu.project02;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GUILogin extends JFrame {
    // Window width, height
    public static int DEFAULT_WIDTH = 350;
    public static int DEFAULT_HEIGHT = 200;

    // Label + TextField height
    public static int DEFAULT_COMPONENT_HEIGHT = 25;

    // Label + Input text field + login button width
    public static int DEFAULT_LABEL_WIDTH = 80;
    public static int DEFAULT_BTN_WIDTH = 80;
    public static int DEFAULT_INPUT_WIDTH = 165;

    // DEFAULT INPUT COLUMN
    public static int DEFAULT_INPUT_COLUMN = 20;

    // Y position of userLabel userInput
    public static int DEFAULT_USER_Y = 20;
    // Y position of pwdLabel pwdInput
    public static int DEFAULT_PWD_Y = 50;
    // X position of userLabel pwdLabel
    public static int DEFAULT_LABEL_X = 10;
    // X position of userInput pwdInput
    public static int DEFAULT_INPUT_X = 100;

    // Button position
    // Y position of pwdLabel pwdInput
    public static int DEFAULT_BUTTON_Y = 80;
    // X position of userLabel pwdLabel
    public static int DEFAULT_BUTTON_X = 10;


    public GUILogin() {
        // Set title for window
        setTitle("Login");
        // Setting the width and height of frame
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panel contains login form
        JPanel login_form = new JPanel();
        getContentPane().add(login_form);
        // Put components into form
        setupComponents(login_form);
        // Add Panel + Setting the frame visibility to true
        setVisible(true);
    }

    public void setupComponents(JPanel formPanel) {
        formPanel.setLayout(null);

        // user label
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(createDefaultLabelShape(DEFAULT_LABEL_X, DEFAULT_USER_Y));
        formPanel.add(userLabel);

        // user input text field
        JTextField userInput = new JTextField(DEFAULT_INPUT_COLUMN);
        userInput.setBounds(createDefaultInputShape(DEFAULT_INPUT_X, DEFAULT_USER_Y));
        formPanel.add(userInput);

        // password label
        JLabel pwdLabel = new JLabel("Password");
        pwdLabel.setBounds(createDefaultLabelShape(DEFAULT_LABEL_X, DEFAULT_PWD_Y));
        formPanel.add(pwdLabel);

        // user input text field
        JPasswordField pwdInput = new JPasswordField(DEFAULT_INPUT_COLUMN);
        pwdInput.setBounds(createDefaultInputShape(DEFAULT_INPUT_X, DEFAULT_PWD_Y));
        formPanel.add(pwdInput);

        // login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(createDefaultButtonShape(DEFAULT_BUTTON_X, DEFAULT_BUTTON_Y));
        formPanel.add(loginBtn);
    }

    private Rectangle createDefaultLabelShape(int x, int y) { // x, y is position
        return new Rectangle(x, y, DEFAULT_LABEL_WIDTH, DEFAULT_COMPONENT_HEIGHT);
    }

    private Rectangle createDefaultInputShape(int x, int y) { // x, y is position
        return new Rectangle(x, y, DEFAULT_INPUT_WIDTH, DEFAULT_COMPONENT_HEIGHT);
    }

    private Rectangle createDefaultButtonShape(int x, int y) { // x, y is position
        return new Rectangle(x, y, DEFAULT_BTN_WIDTH, DEFAULT_COMPONENT_HEIGHT);
    }
}
