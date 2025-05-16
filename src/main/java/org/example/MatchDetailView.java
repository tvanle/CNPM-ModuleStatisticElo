package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MatchDetailView extends JFrame {
    private JLabel outsubMatchInfo;
    private JButton subBackToMatches;

    public MatchDetailView() {
        setTitle("Match Detail");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Hiển thị thông tin trận đấu (giả lập cho match M1)
        String matchId = "M1"; // Giả lập chọn trận M1
        loadMatchDetails(matchId);

        gbc.gridy = 1;
        mainPanel.add(outsubMatchInfo, gbc);

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

    // Tải chi tiết trận đấu
    private void loadMatchDetails(String matchId) {
        MatchDAO matchDAO = new MatchDAO();
        Match match = matchDAO.getMatchById(matchId);
        if (match == null) {
            outsubMatchInfo = new JLabel("Match not found!");
            return;
        }

        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        List<MatchPlayer> matchPlayers = matchPlayerDAO.getMatchPlayersByMatch(matchId);
        ChessPlayerDAO chessPlayerDAO = new ChessPlayerDAO();
        RoundDAO roundDAO = new RoundDAO();
        Round round = roundDAO.getRoundById(match.getRoundId());

        StringBuilder info = new StringBuilder("<html>");
        info.append("ID: ").append(match.getId()).append("<br>");
        info.append("Date: ").append(match.getDate()).append("<br>");
        info.append("Round: ").append(round != null ? round.getRoundNum() : "N/A").append("<br>");

        if (matchPlayers.size() >= 2) {
            // Giả lập Player1 là kỳ thủ được chọn (P1)
            MatchPlayer player1 = matchPlayers.get(0);
            MatchPlayer player2 = matchPlayers.get(1);
            ChessPlayer opponent = chessPlayerDAO.getChessPlayerById(player2.getChessPlayerId());
            info.append("Opponent: ").append(opponent != null ? opponent.getName() : "N/A").append("<br>");
            info.append("Elo Change: ").append(player1.getEloChange());
        } else {
            info.append("Opponent: N/A<br>Elo Change: N/A");
        }
        info.append("</html>");

        outsubMatchInfo = new JLabel(info.toString());
    }
}