package view.stats;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        // Tạo panel chính với BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(), new Color(230, 230, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel chọn giải đấu
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JLabel tournamentLabel = new JLabel("Select Tournament:");
        tournamentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(tournamentLabel);

        inTournament = new JComboBox<>();
        inTournament.setPreferredSize(new Dimension(250, 30));
        inTournament.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(inTournament);

        JLabel titleLabel = new JLabel("Chess Tournament Elo Statistics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(topPanel, BorderLayout.SOUTH);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Bảng hiển thị danh sách kỳ thủ
        String[] columnNames = {"ID", "Name", "Birth Year", "Nationality", "Initial Elo", "Final Elo", "Elo Change"};
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        outsubListPlayers = new JTable(model);
        outsubListPlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        outsubListPlayers.setRowHeight(35);
        outsubListPlayers.setFont(new Font("Arial", Font.PLAIN, 14));
        outsubListPlayers.setIntercellSpacing(new Dimension(10, 5));
        outsubListPlayers.setFillsViewportHeight(true);

        // Custom renderer for the Elo Change column
        outsubListPlayers.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int eloChange = (Integer) value;

                // Set text with arrow
                if (eloChange < 0) {
                    label.setText("↓ " + eloChange);
                    label.setForeground(new Color(204, 0, 0)); // Red for negative
                } else if (eloChange > 0) {
                    label.setText("↑ +" + eloChange);
                    label.setForeground(new Color(0, 153, 0)); // Green for positive
                } else {
                    label.setText("― " + eloChange);
                    label.setForeground(Color.DARK_GRAY); // Neutral for zero
                }

                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.BOLD, 14));

                // Keep selection background
                if (isSelected) {
                    label.setBackground(table.getSelectionBackground());
                } else if (row % 2 == 0) {
                    label.setBackground(new Color(240, 248, 255)); // Light blue for even rows
                } else {
                    label.setBackground(Color.WHITE);
                }

                return label;
            }
        });

        // Set column widths
        outsubListPlayers.getColumnModel().getColumn(0).setPreferredWidth(60); // ID
        outsubListPlayers.getColumnModel().getColumn(1).setPreferredWidth(180); // Name
        outsubListPlayers.getColumnModel().getColumn(2).setPreferredWidth(80); // Birth Year
        outsubListPlayers.getColumnModel().getColumn(3).setPreferredWidth(100); // Nationality
        outsubListPlayers.getColumnModel().getColumn(4).setPreferredWidth(90); // Initial Elo
        outsubListPlayers.getColumnModel().getColumn(5).setPreferredWidth(90); // Final Elo
        outsubListPlayers.getColumnModel().getColumn(6).setPreferredWidth(100); // Elo Change

        // Customize the table header
        JTableHeader header = outsubListPlayers.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(25, 25, 112));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        // Set alternating row colors for the table
        outsubListPlayers.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 248, 255) : Color.WHITE);
                }
                ((JLabel) c).setHorizontalAlignment(column == 0 || column >= 4 ? SwingConstants.CENTER : SwingConstants.LEFT);
                return c;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(outsubListPlayers);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 112), 1));
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel lọc và nút chức năng
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(25, 25, 112), 1), "Filters"));

        // Nationality filter
        JLabel nationalityLabel = new JLabel("Filter by Nationality:");
        nationalityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        filterPanel.add(nationalityLabel);

        inNationality = new JTextField(12);
        inNationality.setFont(new Font("Arial", Font.PLAIN, 14));
        inNationality.setPreferredSize(new Dimension(120, 30));
        filterPanel.add(inNationality);

        // Elo change sort
        JLabel sortLabel = new JLabel("Sort Elo Change:");
        sortLabel.setFont(new Font("Arial", Font.BOLD, 14));
        filterPanel.add(sortLabel);

        eloChangeFilterType = new JComboBox<>(new String[]{"Descending (↓)", "Ascending (↑)"});
        eloChangeFilterType.setFont(new Font("Arial", Font.PLAIN, 14));
        eloChangeFilterType.setPreferredSize(new Dimension(150, 30));
        filterPanel.add(eloChangeFilterType);

        // Filter button
        subFilter = new JButton("Apply Filters");
        styleButton(subFilter, new Color(0, 102, 204), Color.WHITE);
        filterPanel.add(subFilter);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setOpaque(false);

        // View Matches button
        subViewMatches = new JButton("View Matches");
        styleButton(subViewMatches, new Color(0, 153, 76), Color.WHITE);
        buttonsPanel.add(subViewMatches);

        // Export Stats button
        subExportStats = new JButton("Export Stats");
        styleButton(subExportStats, new Color(255, 153, 0), Color.WHITE);
        buttonsPanel.add(subExportStats);

        // Back to Home button
        subBackToHome = new JButton("Back to Home");
        styleButton(subBackToHome, new Color(204, 0, 0), Color.WHITE);
        buttonsPanel.add(subBackToHome);

        controlPanel.add(filterPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(buttonsPanel);

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

    // Helper method to style buttons
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
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

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex >= 4) { // Elo columns and birth year
                    return Integer.class;
                }
                return String.class;
            }
        };

        outsubListPlayers.setModel(model);

        // Re-apply the custom renderer for Elo Change column
        outsubListPlayers.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int eloChange = (Integer) value;

                // Set text with arrow
                if (eloChange < 0) {
                    label.setText("↓ " + eloChange);
                    label.setForeground(new Color(204, 0, 0)); // Red for negative
                } else if (eloChange > 0) {
                    label.setText("↑ +" + eloChange);
                    label.setForeground(new Color(0, 153, 0)); // Green for positive
                } else {
                    label.setText("― " + eloChange);
                    label.setForeground(Color.DARK_GRAY); // Neutral for zero
                }

                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.BOLD, 14));

                // Keep selection background
                if (isSelected) {
                    label.setBackground(table.getSelectionBackground());
                } else if (row % 2 == 0) {
                    label.setBackground(new Color(240, 248, 255)); // Light blue for even rows
                } else {
                    label.setBackground(Color.WHITE);
                }

                return label;
            }
        });
    }
}