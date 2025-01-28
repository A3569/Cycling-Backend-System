package cycling;

import java.io.Serializable;

public class Segment implements Serializable {
    private int segmentId;
    private SegmentType type;
    private double location;
    private double averageGradient;
    private double length;

    public Segment(int segmentId, SegmentType type, double location, double averageGradient, double length) {
        this.segmentId = segmentId;
        this.type = type;
        this.location = location;
        this.averageGradient = averageGradient;
        this.length = length;
    }

    public int getSegmentId() { return segmentId; }
    public SegmentType getType() { return type; }
    public double getLocation() { return location; }
    public double getAverageGradient() { return averageGradient; }
    public double getLength() { return length; }
} 