package view.stats;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import dao.MatchPlayerDAO;
import dao.RoundDAO;
import model.*;

public class MatchesListView extends JFrame {
    private JTable outsubListMatches;
    private JButton subViewMatchDetail;
    private JButton subBackToEloStats;
    private JLabel      playerInfoLabel;
    private ChessPlayer player;
    private Tournament  tournament;

    // Cache data to avoid unnecessary database queries
    private List<Match> cachedMatches;
    private List<MatchPlayer> cachedMatchPlayers;

            public MatchesListView(ChessPlayer player, Tournament tournament) {
        this.player = player;
        this.tournament = tournament;
        setTitle("Matches List - Player");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo panel chính với BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Tiêu đề
        JLabel titleLabel = new JLabel("Matches for Player", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 102, 204));

        // Player info label below title
        playerInfoLabel = new JLabel("", JLabel.CENTER);
        playerInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        playerInfoLabel.setForeground(new Color(60, 60, 60));
        playerInfoLabel.setBorder(new EmptyBorder(5, 0, 10, 0));

        // Add title and player info to a panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(playerInfoLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Bảng hiển thị danh sách trận đấu
        String[] columnNames = {"ID", "Date", "Result", "Round Number", "Tournament"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        outsubListMatches = new JTable(tableModel);
        outsubListMatches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(outsubListMatches);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        subViewMatchDetail = new JButton("View Match Detail");
        subViewMatchDetail.setBackground(new Color(0, 153, 76));
        subViewMatchDetail.setForeground(Color.WHITE);
        buttonPanel.add(subViewMatchDetail);

        subBackToEloStats = new JButton("Back to Elo Stats");
        subBackToEloStats.setBackground(new Color(204, 0, 0));
        subBackToEloStats.setForeground(Color.WHITE);
        buttonPanel.add(subBackToEloStats);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm panel vào frame
        add(mainPanel);

        // Tải danh sách trận đấu với player và tournament objects
        loadMatches(this.player.getId(), this.tournament.getId());

        // Xử lý sự kiện nút "View Match Detail"
        subViewMatchDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = outsubListMatches.getSelectedRow();
                if (selectedRow >= 0) {
                    // Get the selected match ID
                    String matchId = (String) outsubListMatches.getValueAt(selectedRow, 0);

                    // Find the match in our cached data instead of querying again
                    Match selectedMatch = null;
                    for (Match match : cachedMatches) {
                        if (match.getId().equals(matchId)) {
                            selectedMatch = match;
                            break;
                        }
                    }

                    if (selectedMatch != null) {
                        dispose();
                        // Pass both the Match object and player to MatchDetailView
                        new MatchDetailView(selectedMatch, player).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a match!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Xử lý sự kiện nút "Back to Elo Stats"
        subBackToEloStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                EloStatsView eloStatsView = new EloStatsView();
                eloStatsView.setVisible(true);

                // Set the tournament dropdown to the correct tournament name
                eloStatsView.selectTournament(tournament.getName());
            }
        });
    }

    // Style button helper method
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    // Show styled warning dialog
    private void showWarningDialog(String message) {
        JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.WARNING_MESSAGE
        );
        JDialog dialog = optionPane.createDialog(this, "Warning");
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Tải danh sách trận đấu with enhanced data display
    private void loadMatches(String chessPlayerId, String tournamentId) {
        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        // Cache the match data with players already embedded
        this.cachedMatches = matchPlayerDAO.getMatches(chessPlayerId, tournamentId);

        // Use the cached data for the rest of the method
        List<Match> matches = this.cachedMatches;

        // Set player info
        playerInfoLabel.setText("Player: " + player.getName() + " | Tournament: " + tournament.getName() + " | Total Matches: " + matches.size());

        DefaultTableModel model = (DefaultTableModel) outsubListMatches.getModel();
        model.setRowCount(0); // Clear existing rows

        // Pre-load all needed rounds to avoid multiple database queries
        RoundDAO roundDAO = new RoundDAO();
        // Create a map of roundId -> Round to avoid querying the same round multiple times
        java.util.Map<String, Round> roundCache = new java.util.HashMap<>();
        for (Match match : matches) {
            if (match.getRoundId() != null && !roundCache.containsKey(match.getRoundId())) {
                Round round = roundDAO.getRoundById(match.getRoundId());
                if (round != null) {
                    roundCache.put(match.getRoundId(), round);
                }
            }
        }

        int wins = 0, losses = 0, draws = 0;

        for (Match match : matches) {
            // Get round from cache instead of querying database each time
            Round round = roundCache.get(match.getRoundId());

            // Find the player's result in this match (now directly in the match object)
            String result = "N/A";
            for (MatchPlayer mp : match.getPlayers()) {
                if (mp.getChessPlayerId().equals(chessPlayerId)) {
                    result = mp.getResult();

                    // Count results for statistics
                    if ("W".equals(result)) wins++;
                    else if ("L".equals(result)) losses++;
                    else if ("D".equals(result)) draws++;

                    break;
                }
            }

            // Tournament name comes from the tournament object
            String tournamentName = tournament.getName();

            // Format date better (assuming it's in YYYY-MM-DD format)
            String formattedDate = match.getDate();

            model.addRow(new Object[]{
                match.getId(),
                formattedDate,
                result,
                round != null ? "Round " + round.getRoundNum() : "N/A",
                tournamentName
            });
        }

        // Update player info label with statistics
        playerInfoLabel.setText(String.format(
            "<html>Player: <b>%s</b> | Total Matches: <b>%d</b> | Record: <font color='green'><b>%d W</b></font> - <font color='red'><b>%d L</b></font> - <font color='#DAA520'><b>%d D</b></font></html>",
            player.getName(), matches.size(), wins, losses, draws
        ));
    }
}