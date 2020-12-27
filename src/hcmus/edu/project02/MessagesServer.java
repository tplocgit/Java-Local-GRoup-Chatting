package hcmus.edu.project02;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MessagesServer implements Runnable{
    public static String EXIT_SIGNAL = "__EXIT__";
    public static int PORT = 3200;
    public static String IP = "localhost";

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

    @Override
    public void run() {
        try {
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            BufferedWriter clientSender = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            clientSenders.add(clientSender);

            clientSender.write("Welcome to our room!!!");
            clientSender.newLine();
            clientSender.flush();
            while (true) {
                String clientMessage;
                while (true) {
                    if ((clientMessage = clientReader.readLine()) == null) break;
                    System.out.println(clientMessage);

                    System.out.println("Received: " + clientMessage);

                    for(BufferedWriter cs : clientSenders) {
                        try {
                            cs.write(clientMessage);
                            cs.newLine();
                            cs.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
