/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.HangHoaController;
import model.HangHoa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import com.toedter.calendar.JDateChooser;
import static controller.ServerController.addToLogs;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChinhSua extends JFrame implements ActionListener {
    private static Socket clientSocket;
    private static int PORT;
    private PrintWriter out;

    JPanel p1, p2;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, lPort;
    JTextField t1, t2, t3, t4, t5, t6, tPort;
    JDateChooser ngNhap, ngXuat;
    JButton b1, b2, bConnect;
    DefaultTableModel dataModel;
    ArrayList<HangHoa> hh;
    HangHoaController controller = new HangHoaController();

    public ChinhSua(String s, ArrayList<HangHoa> h, DefaultTableModel model, int getRow) {
        super(s);
        hh = h;
        dataModel = model;
        p1 = new JPanel();
        p1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        l1 = new JLabel("ID");
        l1.setHorizontalAlignment(SwingConstants.RIGHT);
        t1 = new JTextField();
        t1.setText(h.get(getRow).getId());
        t1.setFont(new Font("Tahoma", Font.BOLD, 12));
        t1.setForeground(Color.RED);

        l2 = new JLabel("Ten");
        l2.setHorizontalAlignment(SwingConstants.RIGHT);
        t2 = new JTextField(h.get(getRow).getTen());

        l3 = new JLabel("Gia");
        l3.setHorizontalAlignment(SwingConstants.RIGHT);
        t3 = new JTextField(String.valueOf(h.get(getRow).getGia()));

        l4 = new JLabel("SoLuong");
        l4.setHorizontalAlignment(SwingConstants.RIGHT);
        t4 = new JTextField(String.valueOf(h.get(getRow).getSoLuong()));

        l5 = new JLabel("DaNhap");
        l5.setHorizontalAlignment(SwingConstants.RIGHT);
        t5 = new JTextField(String.valueOf(h.get(getRow).getDaNhap()));

        l6 = new JLabel("NgayNhap");
        l6.setHorizontalAlignment(SwingConstants.RIGHT);
        ngNhap = new JDateChooser();
        ngNhap.setDateFormatString("dd-MM-yyyy");

        l7 = new JLabel("DaXuat");
        l7.setHorizontalAlignment(SwingConstants.RIGHT);
        t6 = new JTextField(String.valueOf(h.get(getRow).getDaXuat()));

        l8 = new JLabel("NgayXuat");
        l8.setHorizontalAlignment(SwingConstants.RIGHT);
        ngXuat = new JDateChooser();
        ngXuat.setDateFormatString("dd-MM-yyyy");

        lPort = new JLabel("Port");
        lPort.setHorizontalAlignment(SwingConstants.RIGHT);
        tPort = new JTextField();

        bConnect = new JButton("Connect");
        bConnect.addActionListener(this);

        // Add components to the panel with GridBagConstraints
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        p1.add(l1, gbc);
        gbc.gridx = 1;
        p1.add(t1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        p1.add(l2, gbc);
        gbc.gridx = 1;
        p1.add(t2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        p1.add(l3, gbc);
        gbc.gridx = 1;
        p1.add(t3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        p1.add(l4, gbc);
        gbc.gridx = 1;
        p1.add(t4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        p1.add(l5, gbc);
        gbc.gridx = 1;
        p1.add(t5, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        p1.add(l6, gbc);
        gbc.gridx = 1;
        p1.add(ngNhap, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        p1.add(l7, gbc);
        gbc.gridx = 1;
        p1.add(t6, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        p1.add(l8, gbc);
        gbc.gridx = 1;
        p1.add(ngXuat, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        p1.add(lPort, gbc);
        gbc.gridx = 1;
        p1.add(tPort, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        p1.add(bConnect, gbc);

        this.add(p1, BorderLayout.NORTH);

        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        b1 = new JButton("OK");
        b1.addActionListener(this);
        b2 = new JButton("Cancel");
        b2.addActionListener(this);
        p2.add(b1);
        p2.add(b2);
        this.add(p2, BorderLayout.SOUTH);

        setSize(400, 400);
        setLocation(525, 350);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Connect")) {
            connectToServer();
        } else if (e.getActionCommand().equals("OK")) {
            String id = t1.getText();
            String ten = t2.getText();
            double gia = Double.valueOf(t3.getText());
            int soLuong = Integer.valueOf(t4.getText());
            int daNhap = Integer.valueOf(t5.getText());
            DateFormat gg = new SimpleDateFormat("dd-MM-yyyy");
            String ngayNhap = gg.format(ngNhap.getDate());
            int daXuat = Integer.valueOf(t6.getText());
            String ngayXuat = gg.format(ngXuat.getDate());

            HangHoa hanghoa = new HangHoa(id, ten, gia, soLuong, daNhap, ngayNhap, daXuat, ngayXuat);
            controller.chinhSuaHangHoa(hanghoa);
            hh.set(Integer.parseInt(id) - 1, hanghoa);
            dataModel.setValueAt(ten, Integer.parseInt(id) - 1, 1);
            dataModel.setValueAt(gia, Integer.parseInt(id) - 1, 2);
            dataModel.setValueAt(soLuong, Integer.parseInt(id) - 1, 3);
            dataModel.setValueAt(daNhap, Integer.parseInt(id) - 1, 4);
            dataModel.setValueAt(ngayNhap, Integer.parseInt(id) - 1, 5);
            dataModel.setValueAt(daXuat, Integer.parseInt(id) - 1, 6);
            dataModel.setValueAt(ngayXuat, Integer.parseInt(id) - 1, 7);
            JOptionPane.showMessageDialog(rootPane, "Chinh sua hang hoa thanh cong!");

            // Send data to server
            try {
                out.println("Chinh Sua: " + hanghoa.getTen() + " ID: " + id);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getActionCommand().equals("Cancel")) {
            this.dispose();
        }
    }

    public void connectToServer() {
        try {
            PORT = Integer.parseInt(tPort.getText());
            clientSocket = new Socket("localhost", PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            new Thread(new Listener()).start();
            out.println("ChinhSua"); // Send class name to server
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
