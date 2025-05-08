package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatchDetailView extends JFrame {
    private JLabel outsubMatchInfo;
    private JButton subBackToMatches;
    private JButton subPrevMatch;
    private JButton subNextMatch;
    private int currentMatchIndex;

    public MatchDetailView(int matchIndex) {
        this.currentMatchIndex = matchIndex;
        setTitle("Match Detail");
        setSize(400, 250);
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
        gbc.gridwidth = 3;
        mainPanel.add(titleLabel, gbc);

        // Hiển thị thông tin trận đấu
        outsubMatchInfo = new JLabel("<html>ID: 1<br>Opponent: Player 2<br>Date: 2025-05-01<br>Result: W</html>");
        gbc.gridy = 1;
        mainPanel.add(outsubMatchInfo, gbc);

        // Panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        subPrevMatch = new JButton("Previous Match");
        subPrevMatch.setBackground(new Color(0, 102, 204));
        subPrevMatch.setForeground(Color.WHITE);
        buttonPanel.add(subPrevMatch);

        subBackToMatches = new JButton("Back to Matches");
        subBackToMatches.setBackground(new Color(204, 0, 0));
        subBackToMatches.setForeground(Color.WHITE);
        buttonPanel.add(subBackToMatches);

        subNextMatch = new JButton("Next Match");
        subNextMatch.setBackground(new Color(0, 102, 204));
        subNextMatch.setForeground(Color.WHITE);
        buttonPanel.add(subNextMatch);

        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);

        // Thêm panel vào frame
        add(mainPanel);

        // Xử lý sự kiện nút "Previous Match"
        subPrevMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMatchIndex > 0) {
                    dispose();
                    new MatchDetailView(currentMatchIndex - 1).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "This is the first match!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Xử lý sự kiện nút "Next Match"
        subNextMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Giả lập: giả sử có 2 trận đấu (index 0 và 1)
                if (currentMatchIndex < 1) {
                    dispose();
                    new MatchDetailView(currentMatchIndex + 1).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "This is the last match!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

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