package cycling;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Rider implements Serializable {
    private int riderId;
    private String name;
    private int yearOfBirth;
    private Team team;
    private Map<Integer, RiderResult> results; // stageId -> result

    public Rider(int riderId, String name, int yearOfBirth, Team team) {
        this.riderId = riderId;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.team = team;
        this.results = new HashMap<>();
    }

    public int getRiderId() { return riderId; }
    public String getName() { return name; }
    public int getYearOfBirth() { return yearOfBirth; }
    public Team getTeam() { return team; }
    public Map<Integer, RiderResult> getResults() { return results; }

    public void addResult(int stageId, RiderResult result) {
        results.put(stageId, result);
    }

    public void removeResult(int stageId) {
        results.remove(stageId);
    }
} 