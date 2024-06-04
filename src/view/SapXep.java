/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
 
import model.HangHoa;
import controller.HangHoaController;
import static controller.ServerController.addToLogs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class SapXep extends JFrame implements ActionListener {
    private static Socket clientSocket;
    private static int PORT;
    private PrintWriter out;

    JPanel p1, p2, pHeader;
    JLabel lPort;
    JTextField tPort;
    JButton bConnect, b1, b2;
    DefaultTableModel dataModel;
    ArrayList<HangHoa> hh;
    String namee;
    HangHoaController controller = new HangHoaController();

    public SapXep(String s, ArrayList<HangHoa> h, String name) {
        super(s);
        hh = h;
        namee = name;
        dataModel = new DefaultTableModel();
        dataModel.addColumn("Id");
        dataModel.addColumn("Ten");
        dataModel.addColumn("Gia");
        dataModel.addColumn("So Luong");
        dataModel.addColumn("Da Nhap");
        dataModel.addColumn("Ngay Nhap");
        dataModel.addColumn("Da Xuat");
        dataModel.addColumn("Ngay Xuat");

        if (name.equals("Ten")) {
            Collections.sort(h, Comparator.comparing(HangHoa::getTen));
        } else if (name.equals("Gia")) {
            Collections.sort(h, Comparator.comparingDouble(HangHoa::getGia));
        } else if (name.equals("So Luong")) {
            Collections.sort(h, Comparator.comparingInt(HangHoa::getSoLuong));
        } else if (name.equals("Ngay Nhap Kho")) {
            Collections.sort(h, Comparator.comparing(o -> {
                try {
                    return new SimpleDateFormat("dd-MM-yyyy").parse(o.getNgayNhap());
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }));
        } else if (name.equals("Ngay Xuat Kho")) {
            Collections.sort(h, Comparator.comparing(o -> {
                try {
                    return new SimpleDateFormat("dd-MM-yyyy").parse(o.getNgayXuat());
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }));
        }

        for (HangHoa e : h) {
            dataModel.addRow(new Object[]{
                    e.getId(), e.getTen(), e.getGia(), e.getSoLuong(),
                    e.getDaNhap(), e.getNgayNhap(), e.getDaXuat(), e.getNgayXuat()
            });
        }

        JTable tb = new JTable(dataModel);
        JScrollPane sp = new JScrollPane(tb);

        lPort = new JLabel("Port");
        lPort.setHorizontalAlignment(SwingConstants.RIGHT);
        tPort = new JTextField();

        bConnect = new JButton("Connect");
        bConnect.addActionListener(this);

        p1 = new JPanel();
        p1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add components to the panel with GridBagConstraints
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        p1.add(lPort, gbc);
        gbc.gridx = 1;
        p1.add(tPort, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        p1.add(bConnect, gbc);

        pHeader = new JPanel();
        pHeader.setLayout(new FlowLayout());
        JLabel ten = new JLabel("Sap Xep Theo " + name);
        ten.setFont(new Font("Sitka Text", Font.BOLD, 20));
        pHeader.add(ten);

        this.add(pHeader, BorderLayout.NORTH);
        this.add(sp, BorderLayout.CENTER);
        this.add(p1, BorderLayout.SOUTH);

        setLocation(400, 200);
        setSize(600, 300);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Connect")) {
            connectToServer();
        }
    }

    public void connectToServer() {
        try {
            PORT = Integer.parseInt(tPort.getText());
            clientSocket = new Socket("localhost", PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            new Thread(new Listener()).start();
            out.println("SapXep"); // Send class name to server
            JOptionPane.showMessageDialog(this, "Connected to server on port " + PORT);
        } catch (Exception err) {
            err.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to server on port " + PORT);
        }
    }

    private static class Listener implements Runnable {
        private BufferedReader in;
		@Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String read;
				for(;;) {
					read = in.readLine();
					if (read != null && !(read.isEmpty())) addToLogs(read);
				}
			} catch (IOException e) {
				return;
			}
        }
    }
}
