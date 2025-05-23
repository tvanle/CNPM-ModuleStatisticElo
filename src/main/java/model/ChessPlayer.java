package model;

import java.io.Serializable;
import dao.MatchPlayerDAO;
import java.util.List;

public class ChessPlayer implements Serializable {
    private String id;
    private String name;
    private String nationality;
    private String fideId;
    private int birthYear;
    private int initialElo;

    public ChessPlayer() {
        super();
    }

    public ChessPlayer(String id, String name, String nationality, int birthYear, String fideId, int initialElo) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.birthYear = birthYear;
        this.fideId = fideId;
        this.initialElo = initialElo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFideId() {
        return fideId;
    }

    public void setFideId(String fideId) {
        this.fideId = fideId;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getInitialElo() {
        return initialElo;
    }

    public void setInitialElo(int initialElo) {
        this.initialElo = initialElo;
    }

    public int getFinalElo() {
        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        List<MatchPlayer> matchPlayers = matchPlayerDAO.getMatchPlayersByMatch(null); // Lấy tất cả MatchPlayer
        int totalEloChange = 0;
        for (MatchPlayer mp : matchPlayers) {
            if (mp.getChessPlayerId().equals(this.id)) {
                totalEloChange += mp.getEloChange();
            }
        }
        return initialElo + totalEloChange;
    }
}