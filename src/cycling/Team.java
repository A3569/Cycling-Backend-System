package cycling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private int teamId;
    private String name;
    private String description;
    private List<Rider> riders;

    public Team(int teamId, String name, String description) {
        this.teamId = teamId;
        this.name = name;
        this.description = description;
        this.riders = new ArrayList<>();
    }

    public int getTeamId() { return teamId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Rider> getRiders() { return riders; }

    public void addRider(Rider rider) {
        riders.add(rider);
    }

    public void removeRider(Rider rider) {
        riders.remove(rider);
    }
} 