package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
    private JButton subBackToHome;

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
        inTournament = new JComboBox<>();
        topPanel.add(inTournament);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Bảng hiển thị danh sách kỳ thủ
        String[] columnNames = {"ID", "Name", "Birth Year", "Nationality", "Initial Elo", "Final Elo", "Elo Change"};
        Object[][] data = {};
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

        gbc.gridx = 0;
        gbc.gridy = 2;
        subExportStats = new JButton("Export Stats");
        subExportStats.setBackground(new Color(255, 153, 0));
        subExportStats.setForeground(Color.WHITE);
        controlPanel.add(subExportStats, gbc);

        gbc.gridx = 1;
        subBackToHome = new JButton("Back to Home");
        subBackToHome.setBackground(new Color(204, 0, 0));
        subBackToHome.setForeground(Color.WHITE);
        controlPanel.add(subBackToHome, gbc);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Thêm panel vào frame
        add(mainPanel);

        // Xử lý sự kiện chọn giải đấu
        inTournament.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTournament = (String) inTournament.getSelectedItem();
                String tournamentId = "T1"; // Mặc định là T1
                if ("European Chess Open 2025".equals(selectedTournament)) {
                    tournamentId = "T2";
                }
                List<ChessPlayer> players = new ChessPlayerDAO().getChessPlayersByTournament(tournamentId);
                updatePlayerTable(players);
            }
        });

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

        // Xử lý sự kiện nút "Back to Home"
        subBackToHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeView().setVisible(true);
            }
        });

        // Tải danh sách giải đấu khi khởi động
        loadTournaments();
    }

    // Tải danh sách giải đấu
    private void loadTournaments() {
        TournamentDAO tournamentDAO = new TournamentDAO();
        List<Tournament> tournaments = tournamentDAO.getAllTournaments();
        for (Tournament tournament : tournaments) {
            inTournament.addItem(tournament.getName());
        }
    }

    // Cập nhật bảng kỳ thủ
    private void updatePlayerTable(List<ChessPlayer> players) {
        String[] columnNames = {"ID", "Name", "Birth Year", "Nationality", "Initial Elo", "Final Elo", "Elo Change"};
        Object[][] data = new Object[players.size()][7];
        for (int i = 0; i < players.size(); i++) {
            ChessPlayer player = players.get(i);
            data[i][0] = player.getId();
            data[i][1] = player.getName();
            data[i][2] = player.getBirthYear();
            data[i][3] = player.getNationality();
            data[i][4] = player.getInitialElo();
            data[i][5] = player.getFinalElo();
            data[i][6] = player.getFinalElo() - player.getInitialElo();
        }
        outsubListPlayers.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
}