package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Stage implements Serializable {
    private int stageId;
    private String stageName;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type;
    private StageState state;
    private List<Segment> segments;
    private List<RiderResult> results;
    
    public Stage(int stageId, String stageName, String description, double length, 
                 LocalDateTime startTime, StageType type) {
        this.stageId = stageId;
        this.stageName = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        this.state = StageState.PREPARATION;
        this.segments = new ArrayList<>();
        this.results = new ArrayList<>();
    }
    
    // Getters and setters
    public int getStageId() { return stageId; }
    public String getStageName() { return stageName; }
    public double getLength() { return length; }
    public StageType getType() { return type; }
    public StageState getState() { return state; }
    public List<Segment> getSegments() { return segments; }
    public List<RiderResult> getResults() { return results; }
    public LocalDateTime getStartTime() { return startTime; }
    public String getDescription() { return description; }
    
    public void setState(StageState state) {
        this.state = state;
    }
    
    public void addSegment(Segment segment) {
        segments.add(segment);
    }
    
    public void removeSegment(Segment segment) {
        segments.remove(segment);
    }
    
    public void addResult(RiderResult result) {
        results.add(result);
    }
    
    public void removeResult(RiderResult result) {
        results.remove(result);
    }
} 