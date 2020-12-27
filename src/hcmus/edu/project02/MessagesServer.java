package hcmus.edu.project02;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MessagesServer implements Runnable{
    public static String EXIT_SIGNAL = "__EXIT__";
    public static String ENTER_CODE = "13a05b00c";
    public static int PORT = 3200;
    public static String IP = "localhost";
    public static ArrayList<String> enteredUsers = new ArrayList<>();

    Socket clientSocket;

    public static ArrayList<BufferedWriter> clientSenders = new ArrayList<>();

    public MessagesServer(Socket socket) {
        try {
            this.clientSocket = socket;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTo(BufferedWriter clientSender, String mess) throws IOException {
        clientSender.write(mess);
        clientSender.newLine();
        clientSender.flush();
    }

    public void sendToAll(String message) {
        for(BufferedWriter clientSender : clientSenders) {
            try {
                clientSender.write(message);
                clientSender.newLine();
                clientSender.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            BufferedWriter clientSender = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            clientSenders.add(clientSender);

            String user = clientReader.readLine();

            sendTo(clientSender, "Welcome to our room!");

            sendToAll(user + " entered the room!");

            sendToAll(ENTER_CODE);
            sendToAll(user);

            for (String enteredUSer : enteredUsers) {
                sendTo(clientSender, ENTER_CODE);
                sendTo(clientSender, enteredUSer);
            }

            enteredUsers.add(user);


            while (true) {
                String clientMessage;
                while (true) {
                    if ((clientMessage = clientReader.readLine()) == null) break;
                    System.out.println(clientMessage);

                    System.out.println("Received: " + clientMessage);

                    sendToAll(clientMessage);
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket client = serverSocket.accept();
            MessagesServer server = new MessagesServer(client);
            Thread thread = new Thread(server);
            thread.start();
        }
    }
}
