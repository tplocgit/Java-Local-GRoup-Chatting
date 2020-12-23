package hcmus.edu.project02;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMessage extends JFrame {

    public static int DEFAULT_WIDTH = 650;
    public static int DEFAULT_HEIGHT = 500;
    public static int DEFAULT_COLUMN = 10;
    public static EmptyBorder DEFAULT_EMPTY_BORDER = new EmptyBorder(10, 10, 10, 10);
    public static int DEFAULT_TYPE_TA_ROWS = 3;
    public static EmptyBorder EMPTY_BORDER_TOP = new EmptyBorder(10, 0, 0, 0);

    JFrame mainFrame = this;
    public GUIMessage() {
        setTitle("Message");
        setLayout(new BorderLayout());
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        JPanel connectionPanel = new JPanel();
        add(connectionPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        add(contentPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        //add(infoPanel, BorderLayout.EAST);

        initializeGUI(connectionPanel, contentPanel, infoPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initializeGUI (JPanel conPanel, JPanel contentPanel, JPanel infoPanel) {

        // setting connection Panel
        conPanel.setLayout(new FlowLayout());

        // Enter IP panel
        JPanel ipPanel = new JPanel(new FlowLayout());

        JLabel ipLabel = new JLabel("IP");
        ipPanel.add(ipLabel);

        JTextField ipInput = new JTextField(DEFAULT_COLUMN);
        ipPanel.add(ipInput);
        conPanel.add(ipPanel);

        // Enter Port Panel
        JPanel portPanel = new JPanel(new FlowLayout());
        conPanel.add(portPanel);

        JLabel portLabel = new JLabel("Port");
        portPanel.add(portLabel);

        JTextField portInput = new JTextField(DEFAULT_COLUMN);
        portPanel.add(portInput);

        // Connection Button
        JPanel conBtnPanel = new JPanel(new FlowLayout());
        conPanel.add(conBtnPanel);

        JButton connectBtn = new JButton(HTMLText.textSuccess("Connect"));
        conBtnPanel.add(connectBtn);
        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, HTMLText.textSuccess("OK"));
            }
        });

        JButton disconnectBtn = new JButton(HTMLText.textDanger("Disconnect"));
        conBtnPanel.add(disconnectBtn);
        disconnectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(mainFrame, HTMLText.textDanger("Are You sure to DISCONNECT?"));
            }
        });

        // MessArea setting
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(DEFAULT_EMPTY_BORDER);

        JPanel messPanel = new JPanel(new BorderLayout());
        messPanel.setBorder(new TitledBorder( new EtchedBorder(), "Messages Area"));
        contentPanel.add(messPanel, BorderLayout.CENTER);

        JTextArea messArea = new JTextArea();
        messPanel.add(messArea, BorderLayout.CENTER);
        messArea.setEditable(false);
        messArea.setBackground(Color.WHITE);


        // Type Area + send Panel
        JPanel messScreenPanel = new JPanel();
        contentPanel.add(messScreenPanel, BorderLayout.SOUTH);
        messScreenPanel.setLayout(new BorderLayout());
        messScreenPanel.setBorder(EMPTY_BORDER_TOP);

        // Type Area Panel
        JPanel typeAreaPanel = new JPanel(new BorderLayout());
        typeAreaPanel.setBorder(new TitledBorder( new EtchedBorder(), "Your message"));

        JTextArea typeArea = new JTextArea();
        typeAreaPanel.add(typeArea, BorderLayout.CENTER);
        typeArea.setRows(DEFAULT_TYPE_TA_ROWS);

        JScrollPane scroll = new JScrollPane (typeArea);
        typeAreaPanel.add(scroll);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        typeArea.setBorder(new EmptyBorder(5, 5, 5, 5));
        messScreenPanel.add(typeAreaPanel, BorderLayout.CENTER);

        // Send Panel
        JPanel sendBtnPanel = new JPanel(new BorderLayout());
        sendBtnPanel.setBorder(DEFAULT_EMPTY_BORDER);
        messScreenPanel.add(sendBtnPanel, BorderLayout.EAST);

        JButton sendBtn = new JButton(HTMLText.textPrimary("Send"));
        sendBtnPanel.add(sendBtn, BorderLayout.CENTER);

        // Info
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Members"));
        infoPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH / 6, messScreenPanel.getHeight()));

        String[] mems = {HTMLText.textInfo("Ducky Lark"), "Little Biz", "Sugar Deady"};
        JList<String> members = new JList<>(mems);
        members.setBorder(DEFAULT_EMPTY_BORDER);
        infoPanel.add(members);
        contentPanel.add(infoPanel, BorderLayout.EAST);
    }
}
