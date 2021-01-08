package hcmus.edu.project02;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class GUIMessage extends JFrame implements Runnable {

    public static int DEFAULT_WIDTH = 650;
    public static int DEFAULT_HEIGHT = 500;
    public static int DEFAULT_COLUMN = 10;
    public static EmptyBorder DEFAULT_EMPTY_BORDER = new EmptyBorder(10, 10, 10, 10);
    public static int DEFAULT_TYPE_TA_ROWS = 3;
    public static EmptyBorder EMPTY_BORDER_TOP = new EmptyBorder(10, 0, 0, 0);

    private Socket clientSocket = null;
    private BufferedReader serverReader = null;
    private BufferedWriter serverSender = null;
    private final GUIMessage mainFrame = this;
    public String user;
    public boolean isConnected = false;
    Thread thread;
    JTextArea messArea = new JTextArea();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> members = new JList<>(listModel);

    public GUIMessage(String user) {
        this.user = user;
        setTitle("Message");
        setLayout(new BorderLayout());
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mainFrame.dispose();
                try {
                    serverSender.write(MessagesServer.EXIT_SIGNAL);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });

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

        // MessArea setting
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(DEFAULT_EMPTY_BORDER);

        JPanel messPanel = new JPanel(new BorderLayout());
        messPanel.setBorder(new TitledBorder( new EtchedBorder(), "Messages Area"));
        contentPanel.add(messPanel, BorderLayout.CENTER);

        messPanel.add(messArea, BorderLayout.CENTER);
        messArea.setEditable(false);
        messArea.setBackground(Color.WHITE);

        // setting connection Panel
        conPanel.setLayout(new FlowLayout());

        // Enter IP panel
        JPanel ipPanel = new JPanel(new FlowLayout());

        JLabel ipLabel = new JLabel("IP");
        ipPanel.add(ipLabel);

        JTextField ipInput = new JTextField(MessagesServer.IP, DEFAULT_COLUMN);
        ipPanel.add(ipInput);
        ipInput.setEditable(false);
        conPanel.add(ipPanel);

        // Enter Port Panel
        JPanel portPanel = new JPanel(new FlowLayout());
        conPanel.add(portPanel);

        JLabel portLabel = new JLabel("Port");
        portPanel.add(portLabel);

        JTextField portInput = new JTextField("3200", DEFAULT_COLUMN);
        portPanel.add(portInput);
        portInput.setEditable(false);

        // Connection Button
        JPanel conBtnPanel = new JPanel(new FlowLayout());
        conPanel.add(conBtnPanel);

        JButton connectBtn = new JButton(HTMLText.textSuccess("Connect"));
        conBtnPanel.add(connectBtn);
        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipInput.getText();
                String inputPort = portInput.getText();

                if (isInteger(inputPort)) {
                    int port = Integer.parseInt(inputPort);
                    if (connectionInputEvaluate(ip, port) && !isConnected) {
                        try {
                            clientSocket = new Socket(ip, port);
                            serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            serverSender = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                            serverSender.write(user);
                            serverSender.newLine();
                            serverSender.flush();
                            isConnected = true;
                            thread = new Thread(mainFrame);
                            thread.start();
                        } catch (IOException socketException) {
                            socketException.printStackTrace();
                        }
                    }
                    else if (isConnected) {
                        String failMess = "You already connected to a server please disconnect before connecting.";
                        JOptionPane.showMessageDialog(mainFrame, failMess);
                    }
                    else {
                        String failMess = "Fail to get port: This port is not serviced now. Please try again!";
                        JOptionPane.showMessageDialog(mainFrame, failMess);
                    }
                }
                else {
                    String failMess = "Fail to get port: Expected integer value. Please try again!";
                    JOptionPane.showMessageDialog(mainFrame, failMess);
                }
            }
        });

        JButton disconnectBtn = new JButton(HTMLText.textDanger("Disconnect"));
        conBtnPanel.add(disconnectBtn);
        disconnectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, HTMLText.textDanger("You are DISCONNECTED from server!!!"));
                if (isConnected) {
                    try {
                        serverSender.write(MessagesServer.EXIT_SIGNAL);
                        serverSender.newLine();
                        serverSender.flush();
                        thread.interrupt();
                        clientSocket.close();
                        isConnected = false;
                        listModel.removeAllElements();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame, "You must connected before disconnect");
                }
            }
        });


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
        scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        typeArea.setBorder(new EmptyBorder(5, 5, 5, 5));
        messScreenPanel.add(typeAreaPanel, BorderLayout.CENTER);

        // Send Panel
        JPanel sendBtnPanel = new JPanel(new BorderLayout());
        sendBtnPanel.setBorder(DEFAULT_EMPTY_BORDER);
        messScreenPanel.add(sendBtnPanel, BorderLayout.EAST);

        JButton sendBtn = new JButton(HTMLText.textPrimary("Send"));
        sendBtnPanel.add(sendBtn, BorderLayout.CENTER);
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isConnected)
                    JOptionPane.showMessageDialog(mainFrame, "You must connect to server before send a message!!!");
                else {
                String message = typeArea.getText();
                    if (!message.isEmpty()) {
                        try {
                            StringTokenizer tokenizer = new StringTokenizer(message, "\n");
                            String line;
                            while (tokenizer.hasMoreTokens()) {
                                line = user + ": " + tokenizer.nextToken().trim();
                                serverSender.write(line);
                                serverSender.newLine();
                                serverSender.flush();
                            }

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        typeArea.setText("");
                    }
                }
            }
        });

        // Info
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Members"));
        infoPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH / 6, messScreenPanel.getHeight()));

        members.setBorder(DEFAULT_EMPTY_BORDER);
        infoPanel.add(members);
        contentPanel.add(infoPanel, BorderLayout.EAST);
    }

    private boolean connectionInputEvaluate(String ip, int port) {
        return ip.equalsIgnoreCase(MessagesServer.IP) && port == MessagesServer.PORT;
    }

    private boolean isInteger(String data) {
        try {
            Integer.parseInt(data);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public void run() {
        String serverMessages = "";
        try {
            while (!clientSocket.isClosed()) {
                if (serverReader != null) {
                    if ((serverMessages = serverReader.readLine()) == null) break;
                    System.out.println(serverMessages);
                    if (serverMessages.equals(MessagesServer.ENTER_CODE)) {
                        if ((serverMessages = serverReader.readLine()) == null) break;
                        listModel.addElement(serverMessages);
                    }
                    else if (serverMessages.equals(MessagesServer.LEFT_CODE)) {
                        if ((serverMessages = serverReader.readLine()) == null) break;
                        listModel.removeElement(serverMessages);
                    }
                    else {
                        messArea.append(serverMessages + "\n");
                    }
                }
            }
        } catch (IOException e) {
            listModel.removeElement(serverMessages);
        }
    }
}
