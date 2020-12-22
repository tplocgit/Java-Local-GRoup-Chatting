package hcmus.edu.project02;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.TreeMap;

public class GUIRegister extends JFrame{
    // Window width, height
    public static int DEFAULT_WIDTH = 350;
    public static int DEFAULT_HEIGHT = 230;
    public static int DEFAULT_COMPONENT_HEIGHT = 25;
    public static int DEFAULT_BTN_WIDTH = 100;
    public static EmptyBorder DEFAULT_EMPTY_BORDER = new EmptyBorder(10, 10, 10, 10);
    public static Dimension INPUT_FIELD_MAX_SIZE = new Dimension(Integer.MAX_VALUE, DEFAULT_COMPONENT_HEIGHT);
    public static Dimension BUTTON_MAX_SIZE = new Dimension(DEFAULT_BTN_WIDTH, DEFAULT_COMPONENT_HEIGHT);
    public static String PATH = "accounts.txt";

    // Large headerLabel
    JLabel headerLabel = new JLabel("<html><span style='font-size:16px;color:red''>Register</span></html>");
    JFrame mainFrame = this;
    TreeMap<String, Account> accountMap = AccountsFileControllers.read(PATH);

    public GUIRegister() throws IOException {
        // Set title for window
        setTitle("Register");
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
        // labelPanel setting
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBorder(DEFAULT_EMPTY_BORDER);
        // user label
        JLabel userLabel = new JLabel("User");
        labelPanel.add(userLabel);
        // password label
        JLabel pwdLabel = new JLabel("Password");
        marginLabelTop(pwdLabel, 12);
        labelPanel.add(pwdLabel);
        // confirm password label
        JLabel confirmLabel = new JLabel("Confirm PWD");
        marginLabelTop(confirmLabel, 12);
        labelPanel.add(confirmLabel);

        // tfPanel setting
        tfPanel.setLayout(new BoxLayout(tfPanel, BoxLayout.Y_AXIS));
        tfPanel.setBorder(DEFAULT_EMPTY_BORDER);
        // user input text field
        JTextField userInput = new JTextField();
        userInput.setMaximumSize(INPUT_FIELD_MAX_SIZE);
        tfPanel.add(userInput);
        // pwd input text field
        JPasswordField pwdInput = new JPasswordField();
        pwdInput.setMaximumSize(INPUT_FIELD_MAX_SIZE);
        tfPanel.add(pwdInput);
        // confirm password label
        JPasswordField confirmInput = new JPasswordField();
        confirmInput.setMaximumSize(INPUT_FIELD_MAX_SIZE);
        tfPanel.add(confirmInput);

        // Button Panel
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setBorder(DEFAULT_EMPTY_BORDER);

        // login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setMaximumSize(BUTTON_MAX_SIZE);
        btnPanel.add(loginBtn);
        // login btn event listener
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    GUILogin loginPage = new GUILogin();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        // Register Btn
        JButton regBtn = new JButton("Register");
        btnPanel.add(regBtn);
        // Reg btn event listener
        regBtn.setMaximumSize(BUTTON_MAX_SIZE);
        regBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userInput.getText();
                String pwd = new String(pwdInput.getPassword());
                String conf = new String(confirmInput.getPassword());
                StringBuilder errorMess = new StringBuilder("Your error:\n");
                boolean isUserExist = accountMap.containsKey(user);
                boolean isValidInput = true;

                if (user.isEmpty()) {
                    isValidInput = false;
                    errorMess.append("\tInvalid input of user field.\n");
                } else if (isUserExist) {
                    isValidInput = false;
                    errorMess.append("\tUser exist.\n");
                }

                if (pwd.isEmpty()) {
                    isValidInput = false;
                    errorMess.append("\tInvalid input of pwd field.\n");
                } else if (!pwd.equals(conf)) {
                    isValidInput = false;
                    errorMess.append("\tPassword and confirm password are different.\n");
                }

                if (!isValidInput) {
                    JOptionPane.showMessageDialog(mainFrame, errorMess.toString());
                } else  {
                    JOptionPane.showMessageDialog(mainFrame, "Register successfully!");
                    accountMap.put(user, new Account(user, pwd));
                    try {
                        System.out.println("write new user");
                        AccountsFileControllers.write(PATH, accountMap);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    private static void marginLabelTop(JLabel target, int size) {
        target.setBorder(new EmptyBorder(size, 0, 0 ,0));
    }
}
