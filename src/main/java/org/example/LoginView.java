package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField inUsername;
    private JPasswordField inPassword;
    private JButton subLogin;

    public LoginView() {
        setTitle("Login - Elo Statistics System");
        setSize(400, 250); // Tăng chiều cao để giao diện thoáng hơn
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo panel chính với GridBagLayout để căn giữa
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Tiêu đề
        JLabel titleLabel = new JLabel("Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Ô nhập tên người dùng
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        inUsername = new JTextField(15);
        inUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(inUsername, gbc);

        // Ô nhập mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        inPassword = new JPasswordField(15);
        inPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(inPassword, gbc);

        // Nút đăng nhập
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        subLogin = new JButton("Login");
        subLogin.setBackground(new Color(0, 102, 204));
        subLogin.setForeground(Color.WHITE);
        subLogin.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(subLogin, gbc);

        // Thêm panel vào frame
        add(mainPanel);

        // Xử lý sự kiện nút đăng nhập
        subLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = inUsername.getText();
                String password = new String(inPassword.getPassword());
                boolean loginSuccess = checkLogin(username, password);
                if (loginSuccess) {
                    dispose();
                    new EloStatsView().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean checkLogin(String username, String password) {
        return username.equals("admin") && password.equals("123456");
    }
}