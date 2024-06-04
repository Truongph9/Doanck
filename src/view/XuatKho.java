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

public class XuatKho extends JFrame implements ActionListener {
    private static Socket clientSocket;
    private static int PORT;
    private PrintWriter out;

    JPanel p1, p2;
    JLabel l1, l2, l3, lPort;
    JTextField t1, t2, tPort;
    JDateChooser ngXuat;
    JButton b1, b2, bConnect;
    DefaultTableModel dataModel;
    ArrayList<HangHoa> hh;
    HangHoaController controller = new HangHoaController();

    public XuatKho(String s, ArrayList<HangHoa> h, DefaultTableModel model) {
        super(s);
        hh = h;
        dataModel = model;
        p1 = new JPanel();
        p1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        l1 = new JLabel("ID");
        l1.setHorizontalAlignment(SwingConstants.RIGHT);
        t1 = new JTextField();

        l2 = new JLabel("SoLuong");
        l2.setHorizontalAlignment(SwingConstants.RIGHT);
        t2 = new JTextField();

        l3 = new JLabel("NgayXuat");
        l3.setHorizontalAlignment(SwingConstants.RIGHT);
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
        p1.add(ngXuat, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        p1.add(lPort, gbc);
        gbc.gridx = 1;
        p1.add(tPort, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
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

        setSize(350, 300);
        setLocation(525, 350);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Connect")) {
            connectToServer();
        } else if (e.getActionCommand().equals("OK")) {
            int id = Integer.valueOf(t1.getText());
            int soLuong = Integer.valueOf(t2.getText());
            DateFormat gg = new SimpleDateFormat("dd-MM-yyyy");
            String ngayXuat = gg.format(ngXuat.getDate());

            HangHoa hanghoa = hh.get(id - 1);
            controller.xuatKho(hanghoa, soLuong, ngayXuat);
            hanghoa.setSoLuong(hanghoa.getSoLuong() - soLuong);
            hanghoa.setDaXuat(hanghoa.getDaXuat() + soLuong);
            hanghoa.setNgayXuat(ngayXuat);

            dataModel.setValueAt(hanghoa.getSoLuong(), id - 1, 3);
            dataModel.setValueAt(hanghoa.getDaXuat(), id - 1, 6);
            dataModel.setValueAt(hanghoa.getNgayXuat(), id - 1, 7);
            JOptionPane.showMessageDialog(rootPane, "Xuat hang hoa thanh cong!");

            // Send data to server
            try {
                out.println("Xuat Kho: " + hanghoa.getTen() + " So luong: " + soLuong);
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
            out.println("XuatKho"); // Send class name to server
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
