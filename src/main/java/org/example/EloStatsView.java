package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EloStatsView extends JFrame {
    private JComboBox<String> inTournament;
    private JTable outsubListPlayers;
    private JTextField inNationality;
    private JTextField inEloChange;
    private JButton subFilter;
    private JButton subSortByElo;
    private JButton subSortByChange;
    private JButton subViewMatches;
    private JButton subExportStats;

    public EloStatsView() {
        setTitle("Elo Statistics System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo panel chính với BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Panel chọn giải đấu
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select Tournament:"));
        inTournament = new JComboBox<>(new String[]{"Tournament 1", "Tournament 2", "Tournament 3"});
        topPanel.add(inTournament);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Bảng hiển thị danh sách kỳ thủ
        String[] columnNames = {"ID", "Name", "Birth Year", "Nationality", "Initial Elo", "Final Elo", "Elo Change"};
        Object[][] data = {
                {"1", "Player 1", "1990", "USA", "2500", "2520", "20"},
                {"2", "Player 2", "1985", "UK", "2450", "2430", "-20"}
        };
        outsubListPlayers = new JTable(data, columnNames);
        outsubListPlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(outsubListPlayers);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel lọc và nút chức năng
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Filter by Nationality:"), gbc);
        gbc.gridx = 1;
        inNationality = new JTextField(10);
        controlPanel.add(inNationality, gbc);

        gbc.gridx = 2;
        controlPanel.add(new JLabel("Filter by Elo Change:"), gbc);
        gbc.gridx = 3;
        inEloChange = new JTextField(10);
        controlPanel.add(inEloChange, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        subFilter = new JButton("Filter");
        subFilter.setBackground(new Color(0, 153, 76));
        subFilter.setForeground(Color.WHITE);
        controlPanel.add(subFilter, gbc);

        gbc.gridx = 1;
        subSortByElo = new JButton("Sort by Elo");
        subSortByElo.setBackground(new Color(0, 102, 204));
        subSortByElo.setForeground(Color.WHITE);
        controlPanel.add(subSortByElo, gbc);

        gbc.gridx = 2;
        subSortByChange = new JButton("Sort by Change");
        subSortByChange.setBackground(new Color(0, 102, 204));
        subSortByChange.setForeground(Color.WHITE);
        controlPanel.add(subSortByChange, gbc);

        gbc.gridx = 3;
        subViewMatches = new JButton("View Matches");
        subViewMatches.setBackground(new Color(0, 153, 76));
        subViewMatches.setForeground(Color.WHITE);
        controlPanel.add(subViewMatches, gbc);

        gbc.gridx = 4;
        subExportStats = new JButton("Export Stats");
        subExportStats.setBackground(new Color(255, 153, 0));
        subExportStats.setForeground(Color.WHITE);
        controlPanel.add(subExportStats, gbc);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Thêm panel vào frame
        add(mainPanel);

        // Xử lý sự kiện nút "View Matches"
        subViewMatches.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = outsubListPlayers.getSelectedRow();
                if (selectedRow >= 0) {
                    dispose();
                    new MatchesListView().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a player!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Xử lý sự kiện nút "Export Stats"
        subExportStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Stats exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}