/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.border.EmptyBorder;

public class ServerUI extends JFrame implements ActionListener {
    private ServerController serverController = new ServerController();
    private JPanel contentPane;
    private JTextArea txtAreaLogs;
    private JButton btnStart;
    private JLabel lblChatServer;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ServerUI frame = new ServerUI();
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                SwingUtilities.updateComponentTreeUI(frame);
                System.setOut(new PrintStream(new TextAreaOutputStream(frame.txtAreaLogs)));
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ServerUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 570, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        lblChatServer = new JLabel("QUAN LY");
        lblChatServer.setHorizontalAlignment(SwingConstants.CENTER);
        lblChatServer.setFont(new Font("Tahoma", Font.PLAIN, 40));
        contentPane.add(lblChatServer, BorderLayout.NORTH);

        btnStart = new JButton("START");
        btnStart.addActionListener(this);
        btnStart.setFont(new Font("Tahoma", Font.PLAIN, 30));
        contentPane.add(btnStart, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        txtAreaLogs = new JTextArea();
        txtAreaLogs.setBackground(Color.BLACK);
        txtAreaLogs.setForeground(Color.WHITE);
        txtAreaLogs.setLineWrap(true);
        scrollPane.setViewportView(txtAreaLogs);
        setLocation(10, 10);
        setSize(450, 550);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            if (btnStart.getText().equals("START")) {
                serverController.getRandomPort();
                serverController.startServer();
                new Login(); // Show the login window
                btnStart.setText("STOP");
            } else {
                serverController.addToLogs("Chat server stopped...");
                try {
                    serverController.stopServer();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                btnStart.setText("START");
            }
        }
        lblChatServer.setText("SERVER" + (btnStart.getText().equals("STOP") ? ": " + serverController.getRandomPort() : ""));
    }
}
