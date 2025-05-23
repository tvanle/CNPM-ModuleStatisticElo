package view.stats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import dao.ChessPlayerDAO;
import dao.TournamentDAO;
import model.ChessPlayer;
import model.Tournament;
import view.user.HomeView;

public class EloStatsView extends JFrame {
    private JComboBox<String> inTournament;
    private JTable outsubListPlayers;
    private JTextField inNationality;
    private JComboBox<String> eloChangeFilterType;
    private JButton subFilter;
    private JButton subViewMatches;
    private JButton subExportStats;
    private JButton subBackToHome;
    private List<ChessPlayer> currentPlayers;

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
        controlPanel.add(new JLabel("Sort Elo Change:"), gbc);
        gbc.gridx = 3;
        eloChangeFilterType = new JComboBox<>(new String[]{"Descending (↓)", "Ascending (↑)"});
        controlPanel.add(eloChangeFilterType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        subFilter = new JButton("Filter");
        subFilter.setBackground(new Color(0, 153, 76));
        subFilter.setForeground(Color.WHITE);
        controlPanel.add(subFilter, gbc);

        gbc.gridx = 1;
        subViewMatches = new JButton("View Matches");
        subViewMatches.setBackground(new Color(0, 153, 76));
        subViewMatches.setForeground(Color.WHITE);
        controlPanel.add(subViewMatches, gbc);

        gbc.gridx = 2;
        subExportStats = new JButton("Export Stats");
        subExportStats.setBackground(new Color(255, 153, 0));
        subExportStats.setForeground(Color.WHITE);
        controlPanel.add(subExportStats, gbc);

        gbc.gridx = 3;
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
                currentPlayers = new ChessPlayerDAO().getChessPlayersByTournament(tournamentId);
                updatePlayerTable(currentPlayers);
            }
        });

        // Xử lý sự kiện nút "Filter"
        subFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPlayers == null || currentPlayers.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a tournament first!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String nationalityFilter = inNationality.getText().trim();
                String filterType = (String) eloChangeFilterType.getSelectedItem();

                // Lọc theo quốc tịch trước
                List<ChessPlayer> filteredPlayers = new ArrayList<>();
                for (ChessPlayer player : currentPlayers) {
                    boolean matchesNationality = nationalityFilter.isEmpty() || player.getNationality().equalsIgnoreCase(nationalityFilter);
                    if (matchesNationality) {
                        filteredPlayers.add(player);
                    }
                }

                // Sắp xếp theo mức thay đổi Elo
                if (filterType.equals("Descending (↓)")) {
                    filteredPlayers.sort((p1, p2) -> {
                        int eloChange1 = p1.getFinalElo() - p1.getInitialElo();
                        int eloChange2 = p2.getFinalElo() - p2.getInitialElo();
                        return Integer.compare(eloChange2, eloChange1); // Giảm dần
                    });
                } else if (filterType.equals("Ascending (↑)")) {
                    filteredPlayers.sort((p1, p2) -> {
                        int eloChange1 = p1.getFinalElo() - p1.getInitialElo();
                        int eloChange2 = p2.getFinalElo() - p2.getInitialElo();
                        return Integer.compare(eloChange1, eloChange2); // Tăng dần
                    });
                }

                updatePlayerTable(filteredPlayers);
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
        TournamentDAO    tournamentDAO = new TournamentDAO();
        List<Tournament> tournaments   = tournamentDAO.getAllTournaments();
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