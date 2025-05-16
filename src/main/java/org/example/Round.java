package org.example;

public class Round {
    private String id, tournamentId, startDate, endDate;
    private int roundNum;

    public Round(String id, String tournamentId, int roundNum, String startDate, String endDate) {
        this.id = id;
        this.tournamentId = tournamentId;
        this.roundNum = roundNum;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() { return id; }
    public int getRoundNum() { return roundNum; }
}