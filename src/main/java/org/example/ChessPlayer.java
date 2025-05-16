package org.example;

public class ChessPlayer {
    private String id, name, nationality, fideId;
    private int birthYear, initialElo, finalElo;

    public ChessPlayer(String id, String name, String nationality, int birthYear, String fideId, int initialElo, int finalElo) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.birthYear = birthYear;
        this.fideId = fideId;
        this.initialElo = initialElo;
        this.finalElo = finalElo;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getBirthYear() { return birthYear; }
    public String getNationality() { return nationality; }
    public int getInitialElo() { return initialElo; }
    public int getFinalElo() { return finalElo; }
}