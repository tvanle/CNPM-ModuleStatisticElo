package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatchDetailView extends JFrame {
    private JLabel outsubMatchInfo;
    private JButton subBackToMatches;

    public MatchDetailView() {
        setTitle("Match Detail");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo panel chính với GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel titleLabel = new JLabel("Match Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Hiển thị thông tin trận đấu
        outsubMatchInfo = new JLabel("<html>ID: 1<br>Opponent: Player 2<br>Elo Change: +20</html>");
        gbc.gridy = 1;
        mainPanel.add(outsubMatchInfo, gbc);

        // Nút quay lại
        subBackToMatches = new JButton("Back to Matches");
        subBackToMatches.setBackground(new Color(204, 0, 0));
        subBackToMatches.setForeground(Color.WHITE);
        gbc.gridy = 2;
        mainPanel.add(subBackToMatches, gbc);

        // Thêm panel vào frame
        add(mainPanel);

        // Xử lý sự kiện nút "Back to Matches"
        subBackToMatches.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MatchesListView().setVisible(true);
            }
        });
    }
}