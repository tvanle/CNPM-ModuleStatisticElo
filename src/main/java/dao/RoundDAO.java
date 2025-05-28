package dao;

import java.util.ArrayList;
import java.util.List;
import model.Match;
import model.Round;

public class RoundDAO extends DAO {
    public RoundDAO() {
        super();
    }

    public Round getRoundById(String roundId) {
        Round round = null;
        try {
            String sql = "SELECT * FROM Round WHERE id = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, roundId);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                round = new Round(
                    rs.getString("id"),
                    rs.getString("tournament_id"),
                    rs.getInt("round_num"),
                    rs.getString("start_date"),
                    rs.getString("end_date")
                );
            }
            rs.close();
            pstmt.close();
            if (round != null) {
                List<Match> roundMatches = new ArrayList<>();
                String sqlMatch = "SELECT * FROM Match WHERE round_id = ?";
                var pstmtMatch = con.prepareStatement(sqlMatch);
                pstmtMatch.setString(1, roundId);
                var rsMatch = pstmtMatch.executeQuery();
                while (rsMatch.next()) {
                    Match match = new Match(
                        rsMatch.getString("id"),
                        rsMatch.getString("round_id"),
                        rsMatch.getString("date")
                    );
                    roundMatches.add(match);
                }
                rsMatch.close();
                pstmtMatch.close();
                round.setListMatch(roundMatches);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return round;
    }

    
}