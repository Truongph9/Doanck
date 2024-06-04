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

public class NhapKho extends JFrame implements ActionListener {
    private static Socket clientSocket;
    private static int PORT;
    private PrintWriter out;

    JPanel p1, p2;
    JLabel l1, l2, l3, l4, l5, lPort;
    JTextField t1, t2, t3, t4, tPort;
    JDateChooser ng;
    JButton b1, b2, bConnect;
    DefaultTableModel dataModel;
    ArrayList<HangHoa> hh;
    HangHoaController controller = new HangHoaController();

    public NhapKho(String s, ArrayList<HangHoa> h, DefaultTableModel model) {
        super(s);
        hh = h;
        dataModel = model;
        p1 = new JPanel();
        p1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        l1 = new JLabel("ID");
        l1.setHorizontalAlignment(SwingConstants.RIGHT);
        t1 = new JTextField();
        t1.setText(String.valueOf(h.size() + 1));
        t1.setEnabled(false);

        l2 = new JLabel("Ten");
        l2.setHorizontalAlignment(SwingConstants.RIGHT);
        t2 = new JTextField();

        l3 = new JLabel("Gia");
        l3.setHorizontalAlignment(SwingConstants.RIGHT);
        t3 = new JTextField();

        l4 = new JLabel("SoLuong");
        l4.setHorizontalAlignment(SwingConstants.RIGHT);
        t4 = new JTextField();

        l5 = new JLabel("Ngay Nhap");
        l5.setHorizontalAlignment(SwingConstants.RIGHT);
        ng = new JDateChooser();
        ng.setDateFormatString("dd-MM-yyyy");

        lPort = new JLabel("Port");
        lPort.setHorizontalAlignment(SwingConstants.RIGHT);
        tPort = new JTextField();

        bConnect = new JButton("Connect");
        bConnect.addActionListener(this);

        // Add components to the panel with GridBagConstraints
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        p1.add(l1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p1.add(t1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        p1.add(l2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        p1.add(t2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        p1.add(l3, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        p1.add(t3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        p1.add(l4, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        p1.add(t4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        p1.add(l5, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        p1.add(ng, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        p1.add(lPort, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        p1.add(tPort, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
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
            String id = t1.getText();
            String ten = t2.getText();
            double gia = Double.valueOf(t3.getText());
            int soLuong = Integer.valueOf(t4.getText());
            DateFormat gg = new SimpleDateFormat("dd-MM-yyyy");
            String ngayNhap = gg.format(ng.getDate());

            HangHoa hanghoa = new HangHoa(id, ten, gia, soLuong, soLuong, ngayNhap, 0, "00-00-0000");
            controller.nhapKho(hanghoa);
            hh.add(hanghoa);
            dataModel.addRow(new Object[]{
                    hanghoa.getId(), hanghoa.getTen(), hanghoa.getGia(),
                    hanghoa.getSoLuong(), hanghoa.getDaNhap(), hanghoa.getNgayNhap(),
                    hanghoa.getDaXuat(), hanghoa.getNgayXuat()
            });
            JOptionPane.showMessageDialog(rootPane, "Them hang hoa thanh cong!");
            int clone = Integer.parseInt(t1.getText());
            t1.setText(String.valueOf(clone + 1));

            // Send data to server
            try {
                out.println("Nhap Kho: " + hanghoa.getTen() + " So luong: " + soLuong);
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
            out.println("NhapKho"); // Send class name to server
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
