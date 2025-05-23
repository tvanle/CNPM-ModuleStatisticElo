package view.stats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import dao.MatchPlayerDAO;
import dao.RoundDAO;
import model.Match;
import model.MatchPlayer;
import model.Round;

public class MatchesListView extends JFrame {
    private JTable outsubListMatches;
    private JButton subViewMatchDetail;
    private JButton subBackToEloStats;

    public MatchesListView() {
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
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Bảng hiển thị danh sách trận đấu
        String[] columnNames = {"ID", "Date", "Result", "Round Number"};
        Object[][] data = {};
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

        subBackToEloStats = new JButton("Back to Elo Stats");
        subBackToEloStats.setBackground(new Color(204, 0, 0));
        subBackToEloStats.setForeground(Color.WHITE);
        buttonPanel.add(subBackToEloStats);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm panel vào frame
        add(mainPanel);

        // Tải danh sách trận đấu (giả lập kỳ thủ P1, giải đấu T1)
        loadMatches("P1", "T1");

        // Xử lý sự kiện nút "View Match Detail"
        subViewMatchDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = outsubListMatches.getSelectedRow();
                if (selectedRow >= 0) {
                    dispose();
                    new MatchDetailView().setVisible(true);
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
                new EloStatsView().setVisible(true);
            }
        });
    }

    // Tải danh sách trận đấu
    private void loadMatches(String chessPlayerId, String tournamentId) {
        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        List<Match> matches = matchPlayerDAO.getMatches(chessPlayerId, tournamentId);
        List<MatchPlayer> matchPlayers = matchPlayerDAO.getMatchPlayersByMatch(null);

        String[] columnNames = {"ID", "Date", "Result", "Round Number"};
        Object[][] data = new Object[matches.size()][4];
        RoundDAO roundDAO = new RoundDAO();
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            Round round = roundDAO.getRoundById(match.getRoundId());

            // Tìm MatchPlayer tương ứng với kỳ thủ trong trận đấu
            String result = "N/A";
            for (MatchPlayer mp : matchPlayers) {
                if (mp.getMatchId().equals(match.getId()) && mp.getChessPlayerId().equals(chessPlayerId)) {
                    result = mp.getResult();
                    break;
                }
            }

            data[i][0] = match.getId();
            data[i][1] = match.getDate();
            data[i][2] = result;
            data[i][3] = round != null ? round.getRoundNum() : "N/A";
        }
        outsubListMatches.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
}