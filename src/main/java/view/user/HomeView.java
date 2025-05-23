package view.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.stats.EloStatsView;

public class HomeView extends JFrame {
    private JButton eloStatsButton;
    private JButton logoutButton;

    public HomeView() {
        setTitle("Home - Elo Statistics System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo panel chính với GridBagLayout để căn giữa
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Tiêu đề
        JLabel titleLabel = new JLabel("Chess Tournament Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Nút "Elo Statistics"
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        eloStatsButton = new JButton("Elo Statistics");
        eloStatsButton.setBackground(new Color(0, 153, 76));
        eloStatsButton.setForeground(Color.WHITE);
        eloStatsButton.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(eloStatsButton, gbc);

        // Nút "Logout"
        gbc.gridy = 2;
        logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(204, 0, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(logoutButton, gbc);

        // Thêm panel vào frame
        add(mainPanel);

        // Xử lý sự kiện nút "Elo Statistics"
        eloStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EloStatsView().setVisible(true);
            }
        });

        // Xử lý sự kiện nút "Logout"
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginView().setVisible(true);
            }
        });
    }
}