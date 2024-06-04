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

public class Login extends JFrame implements ActionListener {
    JPanel panel;
    JLabel userLabel, passwordLabel, message;
    JTextField userNameText;
    JPasswordField passwordText;
    JButton submit, register;
    UserController userController = new UserController();

    public Login() {
        // Tạo tiêu đề
        JLabel titleLabel = new JLabel("Đăng Nhập", JLabel.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.GRAY);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(400, 30));

        // Tạo form đăng nhập
        userLabel = new JLabel("Tài khoản :");
        userNameText = new JTextField(10);
        passwordLabel = new JLabel("Mật khẩu :");
        passwordText = new JPasswordField(10);
        message = new JLabel();
        message.setForeground(Color.RED);

        submit = new JButton("Đăng Nhập");
        submit.addActionListener(this);
        register = new JButton("Đăng kí");
        register.addActionListener(this);

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
        gbc.gridwidth = 2;
        formPanel.add(message, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        buttonPanel.add(submit);
        buttonPanel.add(register);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Đăng Nhập");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String userName = userNameText.getText();
            String password = new String(passwordText.getPassword());

            if (userController.validateUser(userName, password)) {
                message.setText(" Hello " + userName + "");
                new QLHangHoa("Quản Lý Hàng Hóa Xuất Nhập Kho");
                this.dispose();
            } else {
                message.setText(" Invalid user.. ");
            }
        } else if (ae.getSource() == register) {
            new Register();
        }
    }
}
