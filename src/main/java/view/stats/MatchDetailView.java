package view.stats;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import dao.ChessPlayerDAO;
import dao.MatchDAO;
import dao.MatchPlayerDAO;
import dao.RoundDAO;
import model.ChessPlayer;
import model.Match;
import model.MatchPlayer;
import model.Round;

public class MatchDetailView extends JFrame {
    private JLabel outsubMatchInfo;
    private JButton subBackToMatches;
    private JPanel matchInfoPanel;

    // Colors
    private final Color PRIMARY_COLOR = new Color(25, 25, 112); // Dark blue
    private final Color SECONDARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Color WIN_COLOR = new Color(46, 139, 87); // Sea green
    private final Color LOSS_COLOR = new Color(178, 34, 34); // Firebrick red
    private final Color DRAW_COLOR = new Color(218, 165, 32); // Goldenrod

    public MatchDetailView() {
        setTitle("Match Detail");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        // Create match info panel
        matchInfoPanel = new JPanel();
        matchInfoPanel.setLayout(new BoxLayout(matchInfoPanel, BoxLayout.Y_AXIS));
        matchInfoPanel.setOpaque(false);

        // Hiển thị thông tin trận đấu (giả lập cho match M1)
        String matchId = "M1"; // Giả lập chọn trận M1
        loadMatchDetails(matchId);

        gbc.gridy = 1;
        mainPanel.add(matchInfoPanel, gbc);

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

    // Helper method to style buttons
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

    // Helper method to create styled info sections
    private JPanel createInfoSection(String title, Component content) {
        JPanel section = new JPanel(new BorderLayout(10, 10));
        section.setOpaque(false);
        section.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(new CompoundBorder(
            new LineBorder(new Color(240, 240, 245), 1), 
            new EmptyBorder(0, 0, 5, 0)
        ));

        section.add(titleLabel, BorderLayout.NORTH);
        section.add(content, BorderLayout.CENTER);

        return section;
    }

    // Helper method to create styled info rows
    private JPanel createInfoRow(String label, String value, Color valueColor) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel labelComponent = new JLabel(label + ":");
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        labelComponent.setForeground(new Color(80, 80, 80));

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Segoe UI", Font.BOLD, 15));
        valueComponent.setForeground(valueColor != null ? valueColor : new Color(50, 50, 50));

        row.add(labelComponent, BorderLayout.WEST);
        row.add(valueComponent, BorderLayout.CENTER);

        return row;
    }

    // Helper method to create player card
    private JPanel createPlayerCard(ChessPlayer player, MatchPlayer matchPlayer) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(250, 250, 255));
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(230, 230, 240), 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));

        // Player name
        JLabel nameLabel = new JLabel(player != null ? player.getName() : "Unknown Player");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(PRIMARY_COLOR);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Player ID
        JLabel idLabel = new JLabel("ID: " + (player != null ? player.getId() : "N/A"));
        idLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        idLabel.setForeground(SECONDARY_COLOR);
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Result with color
        String resultText = "Result: N/A";
        Color resultColor = Color.GRAY;

        if (matchPlayer != null) {
            switch (matchPlayer.getResult()) {
                case "W":
                    resultText = "Result: WIN";
                    resultColor = WIN_COLOR;
                    break;
                case "L":
                    resultText = "Result: LOSS";
                    resultColor = LOSS_COLOR;
                    break;
                case "D":
                    resultText = "Result: DRAW";
                    resultColor = DRAW_COLOR;
                    break;
            }
        }

        JLabel resultLabel = new JLabel(resultText);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultLabel.setForeground(resultColor);
        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Elo change
        JLabel eloChangeLabel = new JLabel();
        if (matchPlayer != null) {
            int eloChange = matchPlayer.getEloChange();
            if (eloChange > 0) {
                eloChangeLabel.setText("Elo Change: ↑ +" + eloChange);
                eloChangeLabel.setForeground(WIN_COLOR);
            } else if (eloChange < 0) {
                eloChangeLabel.setText("Elo Change: ↓ " + eloChange);
                eloChangeLabel.setForeground(LOSS_COLOR);
            } else {
                eloChangeLabel.setText("Elo Change: ― 0");
                eloChangeLabel.setForeground(Color.GRAY);
            }
        } else {
            eloChangeLabel.setText("Elo Change: N/A");
            eloChangeLabel.setForeground(Color.GRAY);
        }
        eloChangeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        eloChangeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Player nationality if available
        if (player != null) {
            JLabel nationalityLabel = new JLabel("Nationality: " + player.getNationality());
            nationalityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            nationalityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            card.add(nameLabel);
            card.add(Box.createRigidArea(new Dimension(0, 5)));
            card.add(idLabel);
            card.add(Box.createRigidArea(new Dimension(0, 15)));
            card.add(resultLabel);
            card.add(Box.createRigidArea(new Dimension(0, 5)));
            card.add(eloChangeLabel);
            card.add(Box.createRigidArea(new Dimension(0, 10)));
            card.add(nationalityLabel);
        } else {
            card.add(nameLabel);
            card.add(Box.createRigidArea(new Dimension(0, 5)));
            card.add(idLabel);
            card.add(Box.createRigidArea(new Dimension(0, 15)));
            card.add(resultLabel);
            card.add(Box.createRigidArea(new Dimension(0, 5)));
            card.add(eloChangeLabel);
        }

        return card;
    }

    // Tải chi tiết trận đấu
    private void loadMatchDetails(String matchId) {
        // Clear existing content
        matchInfoPanel.removeAll();

        MatchDAO matchDAO = new MatchDAO();
        Match match = matchDAO.getMatchById(matchId);

        if (match == null) {
            JLabel errorLabel = new JLabel("Match not found!", JLabel.CENTER);
            errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            errorLabel.setForeground(Color.RED);
            matchInfoPanel.add(errorLabel);
            matchInfoPanel.revalidate();
            matchInfoPanel.repaint();
            return;
        }

        // Get related data
        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        List<MatchPlayer> matchPlayers = matchPlayerDAO.getMatchPlayersByMatch(matchId);
        ChessPlayerDAO chessPlayerDAO = new ChessPlayerDAO();
        RoundDAO roundDAO = new RoundDAO();
        Round round = roundDAO.getRoundById(match.getRoundId());

        // Create match info section
        JPanel matchInfoSection = new JPanel();
        matchInfoSection.setLayout(new BoxLayout(matchInfoSection, BoxLayout.Y_AXIS));
        matchInfoSection.setOpaque(false);

        // Match basic details
        JPanel basicInfoPanel = new JPanel(new GridLayout(0, 1, 0, 8));
        basicInfoPanel.setOpaque(false);

        // Format date for better display
        String formattedDate = match.getDate();
        basicInfoPanel.add(createInfoRow("Match ID", match.getId(), PRIMARY_COLOR));
        basicInfoPanel.add(createInfoRow("Date", formattedDate, null));
        basicInfoPanel.add(createInfoRow("Round", round != null ? "Round " + round.getRoundNum() : "N/A", null));

        if (round != null) {
            basicInfoPanel.add(createInfoRow("Tournament ID", round.getTournamentId(), null));
        }

        // Add match info section
        matchInfoPanel.add(createInfoSection("Match Information", basicInfoPanel));
        matchInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Players section
        if (matchPlayers.size() >= 2) {
            // Create a panel for both players
            JPanel playersPanel = new JPanel(new GridLayout(1, 2, 20, 0));
            playersPanel.setOpaque(false);

            MatchPlayer player1 = matchPlayers.get(0);
            MatchPlayer player2 = matchPlayers.get(1);

            ChessPlayer chessPlayer1 = chessPlayerDAO.getChessPlayerById(player1.getChessPlayerId());
            ChessPlayer chessPlayer2 = chessPlayerDAO.getChessPlayerById(player2.getChessPlayerId());

            // Add player cards
            playersPanel.add(createPlayerCard(chessPlayer1, player1));
            playersPanel.add(createPlayerCard(chessPlayer2, player2));

            // Add versus symbol between players
            matchInfoPanel.add(createInfoSection("Players", playersPanel));

            // Add visual result indicator
            JPanel resultVisualPanel = new JPanel(new BorderLayout());
            resultVisualPanel.setOpaque(false);
            resultVisualPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

            String result1 = player1.getResult();
            String resultText = "UNKNOWN";
            Color resultColor = Color.GRAY;

            if ("W".equals(result1)) {
                resultText = player1.getChessPlayerId() + " WON THE MATCH";
                resultColor = WIN_COLOR;
            } else if ("L".equals(result1)) {
                resultText = player2.getChessPlayerId() + " WON THE MATCH";
                resultColor = LOSS_COLOR;
            } else if ("D".equals(result1)) {
                resultText = "MATCH ENDED IN A DRAW";
                resultColor = DRAW_COLOR;
            }

            JLabel resultLabel = new JLabel(resultText, JLabel.CENTER);
            resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            resultLabel.setForeground(resultColor);
            resultLabel.setBorder(new CompoundBorder(
                new LineBorder(resultColor, 1, true),
                new EmptyBorder(10, 0, 10, 0)
            ));

            resultVisualPanel.add(resultLabel, BorderLayout.CENTER);
            matchInfoPanel.add(resultVisualPanel);
        } else {
            JLabel errorLabel = new JLabel("Player information not available", JLabel.CENTER);
            errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            errorLabel.setForeground(Color.GRAY);
            matchInfoPanel.add(errorLabel);
        }

        // Update the UI
        matchInfoPanel.revalidate();
        matchInfoPanel.repaint();
    }
}