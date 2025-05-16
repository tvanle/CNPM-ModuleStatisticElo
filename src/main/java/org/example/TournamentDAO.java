package org.example;

import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {
    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        tournaments.add(new Tournament("T1", "World Chess Championship 2025", 2025, 1, "New York", "Giải vô địch cờ vua thế giới"));
        tournaments.add(new Tournament("T2", "European Chess Open 2025", 2025, 1, "London", "Giải cờ vua mở châu Âu"));
        return tournaments;
    }
}