package view.user;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import dao.ManagerDAO;
import model.Manager;

public class LoginView extends JFrame {
    private JTextField inUsername;
    private JPasswordField inPassword;
    private JButton subLogin;
    private JButton btnClose;
    private ChessBoardAnimation chessAnimation;

    // Colors
    private final Color PRIMARY_COLOR = new Color(25, 25, 112); // Dark blue
    private final Color SECONDARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Color ACCENT_COLOR = new Color(255, 215, 0); // Gold
    private final Color BACKGROUND_COLOR = new Color(245, 245, 250); // Light grayish blue
    private final Color TEXT_COLOR = new Color(50, 50, 50); // Dark gray

    public LoginView() {
        setTitle("Chess Tournament Manager - Login");
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

        // Main container panel with two sides
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // Left panel - animation/logo
        JPanel leftPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create gradient background
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), getHeight(), SECONDARY_COLOR.darker());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Chess animation panel
        chessAnimation = new ChessBoardAnimation();
        leftPanel.add(chessAnimation, BorderLayout.CENTER);

        // System title
        JLabel systemTitle = new JLabel("Chess Tournament");
        systemTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        systemTitle.setForeground(Color.WHITE);
        systemTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subTitle = new JLabel("ELO Statistics System");
        subTitle.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        subTitle.setForeground(ACCENT_COLOR);
        subTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(systemTitle);
        titlePanel.add(subTitle);
        titlePanel.setBorder(new EmptyBorder(20, 20, 30, 20));

        leftPanel.add(titlePanel, BorderLayout.NORTH);

        // Version information at bottom
        JLabel versionInfo = new JLabel("v1.0.0 © 2025 Chess Statistics");
        versionInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        versionInfo.setForeground(new Color(200, 200, 200));
        versionInfo.setHorizontalAlignment(SwingConstants.CENTER);
        versionInfo.setBorder(new EmptyBorder(15, 0, 15, 0));

        leftPanel.add(versionInfo, BorderLayout.SOUTH);

        // Right panel - login form
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(BACKGROUND_COLOR);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Window control buttons (close)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setOpaque(false);

        btnClose = new JButton("×");
        btnClose.setFont(new Font("Arial", Font.BOLD, 18));
        btnClose.setForeground(Color.DARK_GRAY);
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
                btnClose.setForeground(Color.DARK_GRAY);
            }
        });

        controlPanel.add(btnClose);
        rightPanel.add(controlPanel);

        // Login form panel
        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(new EmptyBorder(60, 50, 50, 50));
        loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login header
        JLabel loginHeader = new JLabel("Welcome Back");
        loginHeader.setFont(new Font("Segoe UI", Font.BOLD, 28));
        loginHeader.setForeground(PRIMARY_COLOR);
        loginHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel loginSubheader = new JLabel("Please log in to continue");
        loginSubheader.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loginSubheader.setForeground(TEXT_COLOR);
        loginSubheader.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username field
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        usernamePanel.setOpaque(false);
        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setForeground(TEXT_COLOR);

        inUsername = new JTextField(15);
        styleTextField(inUsername);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        usernamePanel.add(inUsername);

        // Password field
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setOpaque(false);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(TEXT_COLOR);

        inPassword = new JPasswordField(15);
        styleTextField(inPassword);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        passwordPanel.add(inPassword);

        // Spacer panel
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        spacerPanel.setPreferredSize(new Dimension(300, 10));

        // Login button
        subLogin = new JButton("LOG IN");
        styleButton(subLogin, PRIMARY_COLOR, Color.WHITE);
        subLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to login panel with spacing
        loginPanel.add(loginHeader);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(loginSubheader);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        loginPanel.add(usernamePanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(passwordPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(spacerPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        loginPanel.add(subLogin);

        // Make panel draggable
        MouseAdapter dragAdapter = createWindowDragAdapter();
        addMouseListener(dragAdapter);
        addMouseMotionListener(dragAdapter);

        // Add login panel to right panel
        rightPanel.add(loginPanel);

        // Add both panels to main container
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        add(mainPanel);

        // Start animation
        chessAnimation.startAnimation();

        // Handle login button action
        subLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = inUsername.getText();
                String password = new String(inPassword.getPassword());

                // Add visual feedback during login
                subLogin.setText("LOGGING IN...");
                subLogin.setEnabled(false);

                // Use SwingWorker to prevent UI freezing
                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        Thread.sleep(800);
                        Manager manager = new Manager(username, password);
                        ManagerDAO managerDAO = new ManagerDAO();
                        return managerDAO.checkLogin(manager);
                    }

                    @Override
                    protected void done() {
                        try {
                            boolean loginSuccess = get();
                            if (loginSuccess) {
                                dispose();
                                new HomeView().setVisible(true);
                            } else {
                                showLoginErrorAnimation();
                                JOptionPane.showMessageDialog(
                                        LoginView.this,
                                        "Invalid username or password. Please try again.",
                                        "Login Failed",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                subLogin.setText("LOG IN");
                                subLogin.setEnabled(true);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            subLogin.setText("LOG IN");
                            subLogin.setEnabled(true);
                        }
                    }
                };
                worker.execute();
            }
        });

        // Enter key triggers login
        inPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    subLogin.doClick();
                }
            }
        });
    }

    // Helper method to style text fields
    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textField.setMargin(new Insets(5, 10, 5, 10));
        textField.setPreferredSize(new Dimension(300, 35)); // Reduced height
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));

        // Add focus effects
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(SECONDARY_COLOR, 2, true),
                        BorderFactory.createEmptyBorder(3, 8, 3, 8)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                        BorderFactory.createEmptyBorder(3, 8, 3, 8)
                ));
            }
        });
    }

    // Helper method to style buttons
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        button.setMaximumSize(new Dimension(300, 50));
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

    // Create window drag adapter for frameless window
    private MouseAdapter createWindowDragAdapter() {
        return new MouseAdapter() {
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
    }

    // Visual error feedback for login
    private void showLoginErrorAnimation() {
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        inUsername.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.RED, 2, true),
                                BorderFactory.createEmptyBorder(3, 8, 3, 8)
                        ));
                        inPassword.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.RED, 2, true),
                                BorderFactory.createEmptyBorder(3, 8, 3, 8)
                        ));
                    } else {
                        inUsername.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                                BorderFactory.createEmptyBorder(3, 8, 3, 8)
                        ));
                        inPassword.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                                BorderFactory.createEmptyBorder(3, 8, 3, 8)
                        ));
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Inner class for chess board animation
    private class ChessBoardAnimation extends JPanel {
        private final int BOARD_SIZE = 8;
        private final int SQUARE_SIZE = 40;
        private final Color LIGHT_SQUARE = new Color(255, 255, 240);
        private final Color DARK_SQUARE = new Color(119, 149, 86);

        private BufferedImage boardImage;
        private Timer animationTimer;
        private final Random random = new Random();

        // Pieces represented by Unicode characters
        private final String[] pieces = {
                "♔", "♕", "♖", "♗", "♘", "♙",
                "♚", "♛", "♜", "♝", "♞", "♟"
        };

        // Store piece positions and animations
        private class ChessPiece {
            String symbol;
            float x, y;
            float targetX, targetY;
            float speed;
            boolean isMoving;

            ChessPiece(String symbol, float x, float y, float speed) {
                this.symbol = symbol;
                this.x = x;
                this.y = y;
                this.targetX = x;
                this.targetY = y;
                this.speed = speed;
                this.isMoving = false;
            }

            void setTarget(float x, float y) {
                this.targetX = x;
                this.targetY = y;
                this.isMoving = true;
            }

            boolean update() {
                if (!isMoving) return false;

                float dx = targetX - x;
                float dy = targetY - y;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);

                if (distance < speed) {
                    x = targetX;
                    y = targetY;
                    isMoving = false;
                    return true;
                } else {
                    x += dx * speed / distance;
                    y += dy * speed / distance;
                    return false;
                }
            }
        }

        private java.util.List<ChessPiece> activePieces = new java.util.ArrayList<>();

        public ChessBoardAnimation() {
            setPreferredSize(new Dimension(BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE));
            createBoardImage();
            initializePieces();
        }

        private void createBoardImage() {
            boardImage = new BufferedImage(BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = boardImage.createGraphics();

            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    boolean isLightSquare = (row + col) % 2 == 0;
                    g2d.setColor(isLightSquare ? LIGHT_SQUARE : DARK_SQUARE);
                    g2d.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }

            g2d.dispose();
        }

        private void initializePieces() {
            // Add some initial pieces
            for (int i = 0; i < 12; i++) {
                String piece = pieces[random.nextInt(pieces.length)];
                float x = random.nextInt(BOARD_SIZE * SQUARE_SIZE - SQUARE_SIZE);
                float y = random.nextInt(BOARD_SIZE * SQUARE_SIZE - SQUARE_SIZE);
                float speed = 0.05f + random.nextFloat() * 0.1f;
                activePieces.add(new ChessPiece(piece, x, y, speed));
            }
        }

        public void startAnimation() {
            if (animationTimer != null && animationTimer.isRunning()) {
                return;
            }

            animationTimer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePieces();
                    repaint();
                }
            });
            animationTimer.start();
        }

        private void updatePieces() {
            // Move pieces toward their targets
            boolean allStopped = true;
            for (ChessPiece piece : activePieces) {
                if (piece.update() || !piece.isMoving) {
                    // If piece reached target or not moving, give it a new target
                    if (random.nextFloat() < 0.02) { // 2% chance to move
                        float targetX = random.nextInt(BOARD_SIZE * SQUARE_SIZE - SQUARE_SIZE);
                        float targetY = random.nextInt(BOARD_SIZE * SQUARE_SIZE - SQUARE_SIZE);
                        piece.setTarget(targetX, targetY);
                        allStopped = false;
                    }
                } else {
                    allStopped = false;
                }
            }

            // If all pieces stopped, set new targets for some
            if (allStopped && random.nextFloat() < 0.3) { // 30% chance to start new movement
                int numToMove = 1 + random.nextInt(3); // Move 1-3 pieces
                for (int i = 0; i < numToMove; i++) {
                    ChessPiece piece = activePieces.get(random.nextInt(activePieces.size()));
                    float targetX = random.nextInt(BOARD_SIZE * SQUARE_SIZE - SQUARE_SIZE);
                    float targetY = random.nextInt(BOARD_SIZE * SQUARE_SIZE - SQUARE_SIZE);
                    piece.setTarget(targetX, targetY);
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Draw board at the center of the panel
            int x = (getWidth() - boardImage.getWidth()) / 2;
            int y = (getHeight() - boardImage.getHeight()) / 2;
            g2d.drawImage(boardImage, x, y, this);

            // Draw pieces
            g2d.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 32));
            for (ChessPiece piece : activePieces) {
                // Determine piece color based on type (white or black pieces)
                boolean isWhitePiece = piece.symbol.codePointAt(0) <= 9818; // Unicode points for white pieces are lower
                g2d.setColor(isWhitePiece ? Color.WHITE : Color.BLACK);

                // Apply anti-aliasing for smoother text
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Add a subtle shadow for better visibility
                if (isWhitePiece) {
                    g2d.setColor(new Color(0, 0, 0, 60));
                    g2d.drawString(piece.symbol, x + piece.x + 1, y + piece.y + 31);
                    g2d.setColor(Color.WHITE);
                }

                g2d.drawString(piece.symbol, x + piece.x, y + piece.y + 30); // +30 for vertical centering
            }
        }
    }
}