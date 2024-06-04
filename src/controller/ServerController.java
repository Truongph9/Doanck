/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class ServerController {
    public static SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm a]");
    private static HashMap<String, PrintWriter> connectedClients = new HashMap<>();
    private static final int MAX_CONNECTED = 50;
    private static int PORT;
    private static ServerSocket server;
    private static volatile boolean exit = false;

    public static void startServer() {
        new Thread(new ServerHandler()).start();
    }

    public static void stopServer() throws IOException {
        if (!server.isClosed()) {
            server.close();
        }
    }

    public static void addToLogs(String message) {
        System.out.printf("%s %s\n", formatter.format(new Date()), message);
    }

    public static int getRandomPort() {
        int port = 8888;
        PORT = port;
        return port;
    }

    private static class ServerHandler implements Runnable {
        @Override
        public void run() {
            try {
                server = new ServerSocket(PORT);
                addToLogs("Bat dau quan ly port: " + PORT);
                addToLogs("Dang cho ket noi...");
                while (!exit) {
                    if (connectedClients.size() <= MAX_CONNECTED) {
                        new Thread(new ClientHandler(server.accept())).start();
                    }
                }
            } catch (Exception e) {
                addToLogs("\nError occured: \n");
                addToLogs(Arrays.toString(e.getStackTrace()));
                addToLogs("\nExiting...");
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String name;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            addToLogs("1 Client da ket noi: " + socket.getInetAddress());
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                while (true) {
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (connectedClients) {
                        if (!name.isEmpty() && !connectedClients.keySet().contains(name)) {
                            break;
                        } else {
                            out.println("INVALIDNAME");
                        }
                    }
                }
                addToLogs(name.toUpperCase() + " STARTED AND CONNECTED");
                connectedClients.put(name, out);
                String message;
                while ((message = in.readLine()) != null && !exit) {
                    if (!message.isEmpty()) {
                        if (message.toLowerCase().equals("/quit")) {
                            break;
                        }
                        addToLogs(String.format("[%s] %s", name, message));
                    }
                }
            } catch (Exception e) {
                addToLogs(e.getMessage());
            } finally {
                if (name != null) {
                    addToLogs(name + " da thoat");
                    connectedClients.remove(name);
                }
            }
            addToLogs("So client la: " + connectedClients.size());
        }
    }
}
