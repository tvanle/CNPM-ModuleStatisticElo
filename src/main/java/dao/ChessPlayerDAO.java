package dao;

import java.util.ArrayList;
import java.util.List;
import model.ChessPlayer;

public class ChessPlayerDAO extends DAO {
    public ChessPlayerDAO() {
        super();
    }

    public List<ChessPlayer> getChessPlayersByTournament(String tournamentId) {
        List<ChessPlayer> players = new ArrayList<>();
        try {
            String sql = "SELECT * FROM chess_player WHERE tournament_id = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tournamentId);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                ChessPlayer player = new ChessPlayer(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("nationality"),
                    rs.getInt("birth_year"),
                    rs.getString("fide_id"),
                    rs.getInt("initial_elo")
                );
                players.add(player);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    public ChessPlayer getChessPlayerById(String chessPlayerId) {
        try {
            String sql = "SELECT * FROM chess_player WHERE id = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, chessPlayerId);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                ChessPlayer player = new ChessPlayer(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("nationality"),
                    rs.getInt("birth_year"),
                    rs.getString("fide_id"),
                    rs.getInt("initial_elo")
                );
                rs.close();
                pstmt.close();
                return player;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChessPlayer> getAllChessPlayers() {
        List<ChessPlayer> players = new ArrayList<>();
        try {
            String sql = "SELECT * FROM chess_player";
            var stmt = con.createStatement();
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ChessPlayer player = new ChessPlayer(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("nationality"),
                    rs.getInt("birth_year"),
                    rs.getString("fide_id"),
                    rs.getInt("initial_elo")
                );
                players.add(player);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }
}