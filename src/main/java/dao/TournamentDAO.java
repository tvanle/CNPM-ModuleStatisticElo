package dao;

import java.util.ArrayList;
import java.util.List;
import model.Tournament;

public class TournamentDAO extends DAO {
    public TournamentDAO() {
        super();
    }

    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tournament";
            var stmt = con.createStatement();
            var rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Tournament tournament = new Tournament(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("year"),
                    rs.getInt("edition"),
                    rs.getString("location"),
                    rs.getString("description")
                );
                tournaments.add(tournament);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tournaments;
    }
}