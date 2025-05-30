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
            String sql = "SELECT cp.* FROM TournamentChessPlayer tcp JOIN ChessPlayer cp ON tcp.chessPlayerId = cp.id WHERE tcp.tournamentId = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tournamentId);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                ChessPlayer player = new ChessPlayer(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("nationality"),
                    rs.getInt("birthYear"),
                    rs.getString("icard"),
                    rs.getInt("initialElo")
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
            String sql = "SELECT * FROM ChessPlayer WHERE id = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, chessPlayerId);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                ChessPlayer player = new ChessPlayer(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("nationality"),
                    rs.getInt("birthYear"),
                    rs.getString("icard"),
                    rs.getInt("initialElo")
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
            String sql = "SELECT * FROM ChessPlayer";
            var stmt = con.createStatement();
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ChessPlayer player = new ChessPlayer(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("nationality"),
                    rs.getInt("birthYear"),
                    rs.getString("icard"),
                    rs.getInt("initialElo")
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