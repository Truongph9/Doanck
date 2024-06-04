/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame implements ActionListener {
    JPanel panel;
    JLabel userLabel, passwordLabel, confirmPasswordLabel, message;
    JTextField userNameText;
    JPasswordField passwordText, confirmPasswordText;
    JButton submit, cancel;
    UserController userController = new UserController();

    public Register() {
        // Tạo tiêu đề
        JLabel titleLabel = new JLabel("Đăng ký tài khoản", JLabel.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.GRAY);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(400, 30));

        // Tạo form đăng ký
        userLabel = new JLabel("Tài khoản :");
        userNameText = new JTextField(10);
        passwordLabel = new JLabel("Mật khẩu :");
        passwordText = new JPasswordField(10);
        confirmPasswordLabel = new JLabel("Nhập lại mật khẩu :");
        confirmPasswordText = new JPasswordField();
        message = new JLabel();
        message.setForeground(Color.RED);

        submit = new JButton("Đăng ký");
        submit.addActionListener(this);
        cancel = new JButton("Hủy");
        cancel.addActionListener(this);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(userNameText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(confirmPasswordText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(message, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(submit);
        buttonPanel.add(cancel);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Đăng ký tài khoản");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String userName = userNameText.getText();
            String password = new String(passwordText.getPassword());
            String confirmPassword = new String(confirmPasswordText.getPassword());

            if (password.equals(confirmPassword)) {
                if (userController.registerUser(userName, password)) {
                    message.setText("Đăng ký thành công!");
                    this.dispose(); // Đóng giao diện đăng ký
                    new Login(); // Mở lại giao diện đăng nhập
                } else {
                    message.setText("Đăng ký thất bại!");
                }
            } else {
                message.setText("Mật khẩu không khớp!");
            }
        } else if (ae.getSource() == cancel) {
            this.dispose(); // Đóng giao diện đăng ký
        }
    }
}
