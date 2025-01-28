package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;

public class RiderResult implements Serializable {
    private int riderId;
    private LocalTime[] checkpoints;
    private LocalTime elapsedTime;
    private LocalTime adjustedElapsedTime;
    private int points;
    private int mountainPoints;

    public RiderResult(int riderId, LocalTime[] checkpoints) {
        this.riderId = riderId;
        this.checkpoints = Arrays.copyOf(checkpoints, checkpoints.length);
        calculateElapsedTime();
    }

    private void calculateElapsedTime() {
        if (checkpoints.length >= 2) {
            LocalTime startTime = checkpoints[0];
            LocalTime finishTime = checkpoints[checkpoints.length - 1];
            
            // Calculate elapsed time in seconds
            long elapsedSeconds = finishTime.toSecondOfDay() - startTime.toSecondOfDay();
            this.elapsedTime = LocalTime.ofSecondOfDay(elapsedSeconds);
        }
    }

    public int getRiderId() { return riderId; }
    public LocalTime[] getCheckpoints() { return Arrays.copyOf(checkpoints, checkpoints.length); }
    public LocalTime getElapsedTime() { return elapsedTime; }
    public LocalTime getAdjustedElapsedTime() { return adjustedElapsedTime; }
    public int getPoints() { return points; }
    public int getMountainPoints() { return mountainPoints; }

    public void setAdjustedElapsedTime(LocalTime time) {
        this.adjustedElapsedTime = time;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setMountainPoints(int points) {
        this.mountainPoints = points;
    }
} 