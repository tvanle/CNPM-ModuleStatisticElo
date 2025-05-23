package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Round implements Serializable {
    private String id;
    private String tournamentId;
    private int roundNum;
    private String startDate;
    private String endDate;
    private List<Match> listMatch; // Thêm danh sách các trận đấu

    public Round() {
        super();
        this.listMatch = new ArrayList<>();
    }

    public Round(String id, String tournamentId, int roundNum, String startDate, String endDate) {
        this.id = id;
        this.tournamentId = tournamentId;
        this.roundNum = roundNum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listMatch = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Match> getListMatch() {
        return listMatch;
    }

    public void setListMatch(List<Match> listMatch) {
        this.listMatch = listMatch;
    }
}