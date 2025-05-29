package view.user;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import view.stats.EloStatsView;

public class HomeView extends JFrame {
    private JButton eloStatsButton;
    private JButton tournamentsButton;
    private JButton playersButton;
    private JButton logoutButton;

    // Colors
    private final Color PRIMARY_COLOR = new Color(25, 25, 112); // Dark blue
    private final Color SECONDARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Color ACCENT_COLOR = new Color(255, 215, 0); // Gold
    private final Color BACKGROUND_COLOR = new Color(245, 245, 250); // Light grayish blue
    private final Color TEXT_COLOR = new Color(50, 50, 50); // Dark gray

    public HomeView() {
        setTitle("Chess Tournament Manager - Home");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove default window frame
        setShape(new RoundRectangle2D.Double(0, 0, 900, 600, 20, 20)); // Round corners

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

        // Main container panel with two sections
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header panel with gradient background
        JPanel headerPanel = new JPanel(new BorderLayout()) {
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
        headerPanel.setPreferredSize(new Dimension(900, 80));

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
        btnClose.addActionListener(e -> System.exit(0));
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

        // Title and welcome message
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Chess Tournament Manager");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome back, Administrator");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setForeground(ACCENT_COLOR);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(welcomeLabel);

        headerPanel.add(controlPanel, BorderLayout.NORTH);
        headerPanel.add(titlePanel, BorderLayout.CENTER);

        // Content panel
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(BACKGROUND_COLOR);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Dashboard title
        JLabel dashboardLabel = new JLabel("Dashboard");
        dashboardLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        dashboardLabel.setForeground(PRIMARY_COLOR);
        dashboardLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(dashboardLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Feature cards panel
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardsPanel.setOpaque(false);
        cardsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Elo Statistics card
        JPanel eloStatsCard = createFeatureCard(
            "Elo Statistics", 
            "View and analyze player Elo ratings and performance across tournaments",
            "📈"
        );

        // Tournaments card
        JPanel tournamentsCard = createFeatureCard(
            "Tournaments", 
            "Manage tournaments, rounds, and match schedules",
            "🏆"
        );

        // Players card
        JPanel playersCard = createFeatureCard(
            "Players", 
            "Register new players and manage player profiles",
            "👤"
        );

        cardsPanel.add(eloStatsCard);
        cardsPanel.add(tournamentsCard);
        cardsPanel.add(playersCard);
        contentPanel.add(cardsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Action buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        // Elo Statistics button
        eloStatsButton = createMenuButton("Elo Statistics System", new Color(46, 139, 87));
        buttonsPanel.add(eloStatsButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Tournament Management button
        tournamentsButton = createMenuButton("Tournament Management", new Color(70, 130, 180));
        buttonsPanel.add(tournamentsButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Player Management button
        playersButton = createMenuButton("Player Management", new Color(25, 25, 112));
        buttonsPanel.add(playersButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Logout button
        logoutButton = createMenuButton("Logout", new Color(178, 34, 34));
        buttonsPanel.add(logoutButton);

        contentPanel.add(buttonsPanel);

        // Version info
        JLabel versionLabel = new JLabel("v1.0.0 | © Chess Tournament Manager 2025");
        versionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        versionLabel.setForeground(new Color(120, 120, 120));
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(versionLabel);

        // Add panels to main container
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
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
        addMouseListener(dragAdapter);
        addMouseMotionListener(dragAdapter);

        // Xử lý sự kiện nút "Elo Statistics"
        eloStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EloStatsView().setVisible(true);
            }
        });

        // Handle Tournament Management button (stub for future implementation)
        tournamentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeView.this, 
                    "Tournament Management feature coming soon!", 
                    "Coming Soon", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Handle Player Management button (stub for future implementation)
        playersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeView.this, 
                    "Player Management feature coming soon!", 
                    "Coming Soon", 
                    JOptionPane.INFORMATION_MESSAGE);
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

    // Helper method to create feature cards
    private JPanel createFeatureCard(String title, String description, String icon) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(230, 230, 240), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Description
        JLabel descLabel = new JLabel("<html><div style='width:200px;'>"+description+"</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(TEXT_COLOR);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(iconLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(descLabel);

        return card;
    }

    // Helper method to create styled menu buttons
    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(500, 50));
        button.setPreferredSize(new Dimension(300, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(0, 15, 0, 0));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }
}