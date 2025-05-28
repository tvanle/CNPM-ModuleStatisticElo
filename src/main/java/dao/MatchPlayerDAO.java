package dao;

import java.util.ArrayList;
import java.util.List;

import model.ChessPlayer;
import model.Match;
import model.MatchPlayer;

public class MatchPlayerDAO extends DAO {
    public MatchPlayerDAO() {
        super();
    }

    public List<Match> getMatches(String chessPlayerId, String tournamentId) {
        List<Match> matches = new ArrayList<>();
        try {
            String sql = "SELECT m.* FROM match_player mp JOIN match m ON mp.match_id = m.id WHERE mp.chess_player_id = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, chessPlayerId);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                Match match = new Match(
                    rs.getString("id"),
                    rs.getString("round_id"),
                    rs.getString("date")
                );
                matches.add(match);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    public List<MatchPlayer> getMatchPlayersByMatch(String matchId) {
        List<MatchPlayer> result = new ArrayList<>();
        try {
            String sql = matchId == null ? "SELECT * FROM match_player" : "SELECT * FROM match_player WHERE match_id = ?";
            var pstmt = matchId == null ? con.prepareStatement(sql) : con.prepareStatement(sql);
            if (matchId != null) pstmt.setString(1, matchId);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                MatchPlayer mp = new MatchPlayer(
                    rs.getString("id"),
                    rs.getString("match_id"),
                    rs.getString("chess_player_id"),
                    rs.getString("role"),
                    rs.getInt("elo_change"),
                    rs.getString("note"),
                    rs.getString("result")
                );
                result.add(mp);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<MatchPlayer> getMatchPlayersByChessPlayer(String playerId) {
        List<MatchPlayer> matches = new ArrayList<>();
        List<MatchPlayer> allMatchPlayers = getMatchPlayersByMatch(null);
        ChessPlayerDAO chessPlayerDAO = new ChessPlayerDAO();
        for (MatchPlayer mp : allMatchPlayers) {
            if (mp.getChessPlayerId().equals(playerId)) {
                // Find the opponent in the same match
                for (MatchPlayer opponentMp : allMatchPlayers) {
                    if (opponentMp.getMatchId().equals(mp.getMatchId()) && !opponentMp.getChessPlayerId().equals(playerId)) {
                        ChessPlayer opponent    = chessPlayerDAO.getChessPlayerById(opponentMp.getChessPlayerId());
                        MatchPlayer matchPlayer = new MatchPlayer();
                        matchPlayer.setMatchId(mp.getMatchId());
                        matchPlayer.setOpponentName(opponent != null ? opponent.getName() : "N/A");
                        matchPlayer.setEloChange(mp.getEloChange());
                        matches.add(matchPlayer);
                        break;
                    }
                }
            }
        }
        return matches;
    }
}