package cycling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Race implements Serializable {
    private int raceId;
    private String name;
    private String description;
    private List<Stage> stages;
    
    public Race(int raceId, String name, String description) {
        this.raceId = raceId;
        this.name = name;
        this.description = description;
        this.stages = new ArrayList<>();
    }
    
    // Getters and setters
    public int getRaceId() { return raceId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Stage> getStages() { return stages; }
    
    public void addStage(Stage stage) {
        stages.add(stage);
    }
    
    public void removeStage(Stage stage) {
        stages.remove(stage);
    }
} 