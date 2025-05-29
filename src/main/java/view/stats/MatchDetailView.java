package view.stats;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import dao.ChessPlayerDAO;
import dao.RoundDAO;
import model.ChessPlayer;
import model.Match;
import model.MatchPlayer;
import model.Round;

public class MatchDetailView extends JFrame {
    private String      matchId;
    private Match       matchData;
    private JPanel      matchInfoPanel;
    private JButton     backButton;
    private JButton     homeButton;
    private JLabel      vsLabel;
    private JLabel      resultBanner;
    private JPanel      headerPanel;
    private ChessPlayer player; // Current player viewing the match

    // Colors
    private final Color PRIMARY_COLOR    = new Color(25, 25, 112); // Dark blue
    private final Color SECONDARY_COLOR  = new Color(70, 130, 180); // Steel blue
    private final Color WIN_COLOR        = new Color(46, 139, 87); // Sea green
    private final Color LOSS_COLOR       = new Color(178, 34, 34); // Firebrick red
    private final Color DRAW_COLOR       = new Color(218, 165, 32); // Goldenrod
    private final Color BACKGROUND_COLOR = new Color(245, 245, 250); // Light grayish blue
    private final Color ACCENT_COLOR     = new Color(255, 215, 0); // Gold

    public MatchDetailView() {
        this(null, null);
    }

    public MatchDetailView(Match matchData) {
        this(matchData, null);
    }

    public MatchDetailView(Match matchData, ChessPlayer player) {
        this.matchData = matchData;
        this.player = player;
        if (matchData != null) {
            this.matchId = matchData.getId();
        }

        setTitle("Chess Match Details");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove default window frame
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, 900, 650, 20, 20)); // Round corners

        // Set custom look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fall back to default look and feel
        }

        // Main container panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create header panel with gradient background
        createHeaderPanel();

        // Create content panel
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(BACKGROUND_COLOR);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Create match info panel
        matchInfoPanel = new JPanel();
        matchInfoPanel.setLayout(new BoxLayout(matchInfoPanel, BoxLayout.Y_AXIS));
        matchInfoPanel.setOpaque(false);

        // Create navigation panel
        JPanel navigationPanel = createNavigationPanel();

        // Add panels to content panel
        contentPanel.add(matchInfoPanel, BorderLayout.CENTER);
        contentPanel.add(navigationPanel, BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);

        // Make panel draggable
        MouseAdapter dragAdapter = new MouseAdapter() {
            private int x;
            private int y;

            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
            }
        };
        headerPanel.addMouseListener(dragAdapter);
        headerPanel.addMouseMotionListener(dragAdapter);

        // Load match details
        loadMatchDetails();
    }

    // Create header panel with gradient background and controls
    private void createHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create gradient background
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), getHeight(), SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(900, 100));

        // Window control buttons (close)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setOpaque(false);

        JButton btnClose = new JButton("×");
        btnClose.setFont(new Font("Arial", Font.BOLD, 18));
        btnClose.setForeground(Color.WHITE);
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setToolTipText("Close");
        btnClose.addActionListener(e -> dispose());
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnClose.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnClose.setForeground(Color.WHITE);
            }
        });

        controlPanel.add(btnClose);

        // Title and match info
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);

        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
        textContainer.setOpaque(false);
        textContainer.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Chess Match Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        vsLabel = new JLabel("Player vs Player");
        vsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        vsLabel.setForeground(ACCENT_COLOR);
        vsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textContainer.add(titleLabel);
        textContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        textContainer.add(vsLabel);

        titlePanel.add(textContainer);

        headerPanel.add(controlPanel, BorderLayout.NORTH);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
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

    // Create navigation panel with buttons
    private JPanel createNavigationPanel() {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navigationPanel.setOpaque(false);
        navigationPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Back button
        backButton = new JButton("⬅️ Back to Matches");
        styleButton(backButton, SECONDARY_COLOR, Color.WHITE);

        // Home button
        homeButton = new JButton("🏠 Home");
        styleButton(homeButton, PRIMARY_COLOR, Color.WHITE);

        // Add buttons to panel
        navigationPanel.add(backButton);
        navigationPanel.add(homeButton);

        // Add action listeners
        backButton.addActionListener(e -> dispose());
        homeButton.addActionListener(e -> {
            dispose();
            new view.user.HomeView().setVisible(true);
        });

        return navigationPanel;
    }

    // Helper to create icon from emoji
    private Icon createIconFromEmoji(String emoji) {
        JLabel label = new JLabel(emoji);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        // Return text-only icon instead of trying to create an image
        return null; // Return null to use text-only buttons
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

    // Helper method to create enhanced player card
    private JPanel createPlayerCard(MatchPlayer matchPlayer) {
        ChessPlayer player      = matchPlayer.getPlayer();
        String      playerName  = player != null ? player.getName() : "Unknown Player";
        String      playerId    = player != null ? player.getId() : "N/A";
        String      nationality = player != null ? player.getNationality() : "Unknown";

        // Determine result and corresponding color/icon
        String resultText      = "UNKNOWN";
        String resultIcon      = "❓";
        Color  resultColor     = Color.GRAY;
        Color  cardBorderColor = new Color(200, 200, 200);

        if (matchPlayer != null) {
            switch (matchPlayer.getResult()) {
                case "W":
                    resultText = "WINNER";
                    resultIcon = "🏆";
                    resultColor = WIN_COLOR;
                    cardBorderColor = WIN_COLOR;
                    break;
                case "L":
                    resultText = "DEFEATED";
                    resultIcon = "❌";
                    resultColor = LOSS_COLOR;
                    cardBorderColor = LOSS_COLOR;
                    break;
                case "D":
                    resultText = "DRAW";
                    resultIcon = "🤝";
                    resultColor = DRAW_COLOR;
                    cardBorderColor = DRAW_COLOR;
                    break;
            }
        }

        // Calculate Elo change text and icon
        String eloChangeText  = "0";
        String eloChangeIcon  = "―";
        Color  eloChangeColor = Color.GRAY;

        if (matchPlayer != null) {
            int eloChange = matchPlayer.getEloChange();
            if (eloChange > 0) {
                eloChangeText = "+" + eloChange;
                eloChangeIcon = "↑";
                eloChangeColor = WIN_COLOR;
            } else if (eloChange < 0) {
                eloChangeText = Integer.toString(eloChange);
                eloChangeIcon = "↓";
                eloChangeColor = LOSS_COLOR;
            }
        }

        // Create the card with a border matching the result
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(cardBorderColor, 2, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Top section with player name and result banner
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);

        // Player name with icon
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        namePanel.setOpaque(false);

        JLabel playerIcon = new JLabel("👤");
        playerIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));

        JLabel nameLabel = new JLabel(playerName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setForeground(PRIMARY_COLOR);

        namePanel.add(playerIcon);
        namePanel.add(nameLabel);

        // Result banner
        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(resultColor);
        resultPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel resultIconLabel = new JLabel(resultIcon);
        resultIconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        resultIconLabel.setForeground(Color.WHITE);

        JLabel resultTextLabel = new JLabel(" " + resultText);
        resultTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultTextLabel.setForeground(Color.WHITE);

        resultPanel.add(resultIconLabel);
        resultPanel.add(resultTextLabel);

        topSection.add(namePanel, BorderLayout.CENTER);
        topSection.add(resultPanel, BorderLayout.EAST);

        // Center section with player details
        JPanel centerSection = new JPanel();
        centerSection.setLayout(new BoxLayout(centerSection, BoxLayout.Y_AXIS));
        centerSection.setOpaque(false);
        centerSection.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Player ID
        JPanel idRow = createPlayerInfoRow("ID", playerId, null);

        // Nationality with flag emoji
        String flagEmoji      = "🌍"; // Default world flag
        JPanel nationalityRow = createPlayerInfoRow("Nationality", nationality, flagEmoji);

        // Elo change with color
        JPanel eloChangeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        eloChangeRow.setOpaque(false);

        JLabel eloLabel = new JLabel("Elo Change: ");
        eloLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel eloValueLabel = new JLabel(eloChangeIcon + " " + eloChangeText);
        eloValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        eloValueLabel.setForeground(eloChangeColor);

        eloChangeRow.add(eloLabel);
        eloChangeRow.add(eloValueLabel);

        // Add rows to center section
        centerSection.add(idRow);
        centerSection.add(Box.createRigidArea(new Dimension(0, 8)));
        centerSection.add(nationalityRow);
        centerSection.add(Box.createRigidArea(new Dimension(0, 8)));
        centerSection.add(eloChangeRow);

        // Add sections to card
        card.add(topSection, BorderLayout.NORTH);
        card.add(centerSection, BorderLayout.CENTER);

        return card;
    }

    // Helper to create info row for player card
    private JPanel createPlayerInfoRow(String label, String value, String emoji) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        row.setOpaque(false);

        JLabel labelComponent = new JLabel(label + ": ");
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        if (emoji != null) {
            JLabel emojiLabel = new JLabel(emoji + " ");
            emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            row.add(emojiLabel);
        }

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Segoe UI", Font.BOLD, 15));

        row.add(labelComponent);
        row.add(valueComponent);

        return row;
    }

    // Load match details with enhanced UI
    private void loadMatchDetails() {
        // Clear existing content
        matchInfoPanel.removeAll();

        // Use the matchData object passed to the constructor
        Match match = this.matchData;

        if (match == null) {
            JLabel errorLabel = new JLabel("Match not found!", JLabel.CENTER);
            errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
            errorLabel.setForeground(Color.RED);
            matchInfoPanel.add(errorLabel);
            matchInfoPanel.revalidate();
            matchInfoPanel.repaint();
            return;
        }

        // Get players directly from the match object
        List<MatchPlayer> matchPlayers = match.getPlayers();
        RoundDAO          roundDAO     = new RoundDAO();
        Round             round        = roundDAO.getRoundById(match.getRoundId());

        // Create match overview panel
        JPanel overviewPanel = new JPanel();
        overviewPanel.setLayout(new BoxLayout(overviewPanel, BoxLayout.Y_AXIS));
        overviewPanel.setOpaque(false);
        overviewPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Match detail title
        JLabel matchDetailLabel = new JLabel("Match Overview");
        matchDetailLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        matchDetailLabel.setForeground(PRIMARY_COLOR);
        matchDetailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        overviewPanel.add(matchDetailLabel);
        overviewPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Match info in a stylish card
        JPanel matchInfoCard = new JPanel();
        matchInfoCard.setLayout(new GridLayout(2, 2, 15, 15));
        matchInfoCard.setBackground(Color.WHITE);
        matchInfoCard.setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 230, 240), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));
        matchInfoCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Format date better (assuming it's in YYYY-MM-DD format)
        String formattedDate = match.getDate();
        try {
            // Try to format date nicely if possible
            java.time.LocalDate date = java.time.LocalDate.parse(match.getDate());
            formattedDate = date.format(java.time.format.DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        } catch (Exception e) {
            // Keep original format if parsing fails
        }

        // Add match details with icons
        matchInfoCard.add(createDetailItem("🆔", "Match ID", match.getId()));
        matchInfoCard.add(createDetailItem("📅", "Date", formattedDate));
        matchInfoCard.add(createDetailItem("🔄", "Round", round != null ? "Round " + round.getRoundNum() : "N/A"));
        matchInfoCard.add(createDetailItem("🏆", "Tournament", round != null ? round.getTournamentId() : "N/A"));

        overviewPanel.add(matchInfoCard);
        matchInfoPanel.add(overviewPanel);

        // Players section
        if (matchPlayers.size() >= 2) {
            // Determine which player is the current user and which is the opponent
            MatchPlayer currentMatchPlayer  = null;
            MatchPlayer opponentMatchPlayer = null;

            if (player != null) {
                // If we have a current player, find their match data and the opponent's
                for (MatchPlayer mp : matchPlayers) {
                    if (mp.getChessPlayerId() != null && mp.getChessPlayerId().equals(player.getId())) {
                        currentMatchPlayer = mp;
                    } else {
                        opponentMatchPlayer = mp;
                    }
                }
            }

            // If we couldn't identify the current player, use default order
            if (currentMatchPlayer == null) {
                currentMatchPlayer = matchPlayers.get(0);
                opponentMatchPlayer = matchPlayers.get(1);
            }

            // Update header VS label with focus on current player vs opponent
            String vsText = currentMatchPlayer.getPlayerName() + " vs " + opponentMatchPlayer.getPlayerName();
            if (player != null) {
                vsText = "You vs " + opponentMatchPlayer.getPlayerName();
            }
            vsLabel.setText(vsText);

            // Create players title
            JLabel playersLabel = new JLabel("Players");
            playersLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            playersLabel.setForeground(PRIMARY_COLOR);
            playersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            matchInfoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            matchInfoPanel.add(playersLabel);
            matchInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Create a panel for both players with VS label in center
            JPanel playersPanel = new JPanel(new BorderLayout(20, 0));
            playersPanel.setOpaque(false);
            playersPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Player panels
            JPanel currentPlayerPanel = new JPanel(new BorderLayout());
            currentPlayerPanel.setOpaque(false);
            JPanel playerCard = createPlayerCard(currentMatchPlayer);
            // Add a label to indicate this is the current player if applicable
            if (player != null && currentMatchPlayer.getChessPlayerId().equals(player.getId())) {
                JPanel indicatorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                indicatorPanel.setOpaque(false);
                JLabel youLabel = new JLabel("You");
                youLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                youLabel.setForeground(ACCENT_COLOR);
                indicatorPanel.add(youLabel);

                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.setOpaque(false);
                wrapperPanel.add(playerCard, BorderLayout.CENTER);
                wrapperPanel.add(indicatorPanel, BorderLayout.SOUTH);
                currentPlayerPanel.add(wrapperPanel, BorderLayout.CENTER);
            } else {
                currentPlayerPanel.add(playerCard, BorderLayout.CENTER);
            }

            JPanel opponentPanel = new JPanel(new BorderLayout());
            opponentPanel.setOpaque(false);
            opponentPanel.add(createPlayerCard(opponentMatchPlayer), BorderLayout.CENTER);

            // VS label in center
            JPanel vsPanel = new JPanel();
            vsPanel.setOpaque(false);
            vsPanel.setPreferredSize(new Dimension(80, 80));
            vsPanel.setLayout(new GridBagLayout());

            JLabel vsSymbol = new JLabel("VS");
            vsSymbol.setFont(new Font("Segoe UI", Font.BOLD, 28));
            vsSymbol.setForeground(ACCENT_COLOR);
            vsPanel.add(vsSymbol);

            // Add components to players panel
            playersPanel.add(currentPlayerPanel, BorderLayout.WEST);
            playersPanel.add(vsPanel, BorderLayout.CENTER);
            playersPanel.add(opponentPanel, BorderLayout.EAST);

            matchInfoPanel.add(playersPanel);

            // Match result banner
            JPanel resultBannerPanel = new JPanel(new BorderLayout());
            resultBannerPanel.setOpaque(false);
            resultBannerPanel.setBorder(new EmptyBorder(25, 0, 15, 0));
            resultBannerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Determine result
            String currentResult = currentMatchPlayer.getResult();
            String resultText    = "MATCH RESULT UNKNOWN";
            String resultEmoji   = "❓";
            Color  resultColor   = Color.GRAY;

            if ("W".equals(currentResult)) {
                resultText = (player != null ? "YOU" : currentMatchPlayer.getPlayerName()) + " WON THE MATCH";
                resultEmoji = "🏆";
                resultColor = WIN_COLOR;
            } else if ("L".equals(currentResult)) {
                resultText = opponentMatchPlayer.getPlayerName() + " WON THE MATCH";
                resultEmoji = "🏆";
                resultColor = LOSS_COLOR;
            } else if ("D".equals(currentResult)) {
                resultText = "MATCH ENDED IN A DRAW";
                resultEmoji = "🤝";
                resultColor = DRAW_COLOR;
            }

            // Create banner
            JPanel bannerPanel = new JPanel();
            bannerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            bannerPanel.setBackground(resultColor);
            bannerPanel.setBorder(new EmptyBorder(12, 20, 12, 20));

            JLabel emojiLabel = new JLabel(resultEmoji);
            emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            emojiLabel.setForeground(Color.WHITE);

            JLabel resultTextLabel = new JLabel(" " + resultText + " ");
            resultTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            resultTextLabel.setForeground(Color.WHITE);

            bannerPanel.add(emojiLabel);
            bannerPanel.add(resultTextLabel);

            resultBannerPanel.add(bannerPanel, BorderLayout.CENTER);
            matchInfoPanel.add(resultBannerPanel);

            // Add game statistics section
            JLabel statsLabel = new JLabel("Match Statistics");
            statsLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            statsLabel.setForeground(PRIMARY_COLOR);
            statsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            matchInfoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            matchInfoPanel.add(statsLabel);
            matchInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Create statistics panel
            JPanel statsPanel = new JPanel();
            statsPanel.setLayout(new GridLayout(1, 2, 15, 0));
            statsPanel.setOpaque(false);
            statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Elo impact card
            String player1Label = player != null ? "You" : currentMatchPlayer.getPlayerName();
            JPanel eloImpactCard = createStatCard("Elo Impact",
                    player1Label + ": " + (currentMatchPlayer.getEloChange() > 0 ? "+" : "") + currentMatchPlayer.getEloChange(),
                    opponentMatchPlayer.getPlayerName() + ": " + (opponentMatchPlayer.getEloChange() > 0 ? "+" : "") + opponentMatchPlayer.getEloChange(),
                    "📊");

            // Match duration card (placeholder - could be real data in a real app)
            JPanel matchDateCard = createStatCard("Match Date",
                    "Date: " + formattedDate,
                    "Time: 15:00", // Placeholder
                    "🕒");

            statsPanel.add(eloImpactCard);
            statsPanel.add(matchDateCard);

            matchInfoPanel.add(statsPanel);
        } else {
            // Error message for missing player data
            JPanel errorPanel = new JPanel(new BorderLayout());
            errorPanel.setOpaque(false);
            errorPanel.setBorder(new EmptyBorder(50, 0, 0, 0));

            JLabel errorLabel = new JLabel("Player information not available", JLabel.CENTER);
            errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
            errorLabel.setForeground(Color.GRAY);

            errorPanel.add(errorLabel, BorderLayout.CENTER);
            matchInfoPanel.add(errorPanel);
        }

        // Update the UI
        matchInfoPanel.revalidate();
        matchInfoPanel.repaint();
    }

    // Helper method to create detail item with icon
    private JPanel createDetailItem(String emoji, String label, String value) {
        JPanel item = new JPanel(new BorderLayout(10, 5));
        item.setOpaque(false);

        JPanel iconLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        iconLabelPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(emoji);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        textLabel.setForeground(PRIMARY_COLOR);

        iconLabelPanel.add(iconLabel);
        iconLabelPanel.add(textLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        item.add(iconLabelPanel, BorderLayout.NORTH);
        item.add(valueLabel, BorderLayout.CENTER);

        return item;
    }

    // Helper method to create statistic card
    private JPanel createStatCard(String title, String stat1, String stat2, String emoji) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 230, 240), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Title with icon
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        titlePanel.setOpaque(false);

        JLabel iconLabel = new JLabel(emoji);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(PRIMARY_COLOR);

        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Stats
        JLabel stat1Label = new JLabel(stat1);
        stat1Label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        stat1Label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel stat2Label = new JLabel(stat2);
        stat2Label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        stat2Label.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titlePanel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(stat1Label);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(stat2Label);

        return card;
    }
}