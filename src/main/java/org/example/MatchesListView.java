package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatchesListView extends JFrame {
    private JTable outsubListMatches;
    private JButton subViewMatchDetail;
    private JButton subBackToEloStats;
    private JButton subRefresh;

    public MatchesListView() {
        setTitle("Matches List - Player");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo panel chính với BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Tiêu đề
        JLabel titleLabel = new JLabel("Matches for Player 1", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 102, 204));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Bảng hiển thị danh sách trận đấu
        String[] columnNames = {"ID", "Date", "Result", "Round Number"};
        Object[][] data = {
                {"1", "2025-05-01", "W", "1"},
                {"2", "2025-05-02", "D", "2"},
                {"3", "2025-05-03", "L", "3"},
                {"4", "2025-05-04", "W", "4"},
                {"5", "2025-05-05", "D", "5"},
                {"6", "2025-05-06", "L", "6"},
                {"7", "2025-05-07", "W", "7"},
                {"8", "2025-05-08", "D", "8"},
                {"9", "2025-05-09", "L", "9"},
                {"10", "2025-05-10", "W", "10"}
        };
        outsubListMatches = new JTable(data, columnNames);
        outsubListMatches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(outsubListMatches);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        subViewMatchDetail = new JButton("View Match Detail");
        subViewMatchDetail.setBackground(new Color(0, 153, 76));
        subViewMatchDetail.setForeground(Color.WHITE);
        buttonPanel.add(subViewMatchDetail);

        subRefresh = new JButton("Refresh");
        subRefresh.setBackground(new Color(0, 153, 76));
        subRefresh.setForeground(Color.WHITE);
        buttonPanel.add(subRefresh);

        subBackToEloStats = new JButton("Back to Elo Stats");
        subBackToEloStats.setBackground(new Color(204, 0, 0));
        subBackToEloStats.setForeground(Color.WHITE);
        buttonPanel.add(subBackToEloStats);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm panel vào frame
        add(mainPanel);

        // Xử lý sự kiện nút "View Match Detail"
        subViewMatchDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = outsubListMatches.getSelectedRow();
                if (selectedRow >= 0) {
                    dispose();
                    new MatchDetailView(selectedRow).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a match!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Xử lý sự kiện nút "Refresh"
        subRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Matches refreshed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Xử lý sự kiện nút "Back to Elo Stats"
        subBackToEloStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EloStatsView().setVisible(true);
            }
        });
    }
}