package dao;

import java.util.ArrayList;
import java.util.List;
import model.Match;

public class MatchDAO extends DAO {
    public MatchDAO() {
        super();
    }

    public Match getMatchById(String matchId) {
        try {
            String sql = "SELECT * FROM Match WHERE id = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, matchId);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                Match match = new Match(
                    rs.getString("id"),
                    rs.getString("roundId"),
                    rs.getString("date")
                );
                rs.close();
                pstmt.close();
                return match;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}