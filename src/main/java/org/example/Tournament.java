package org.example;

public class Tournament {
    private String id, name, location, description;
    private int year, edition;

    public Tournament(String id, String name, int year, int edition, String location, String description) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.edition = edition;
        this.location = location;
        this.description = description;
    }

    public String getName() { return name; }
    public String getId() { return id; }
}