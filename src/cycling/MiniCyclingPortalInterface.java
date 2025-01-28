package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.IOException;

public interface MiniCyclingPortalInterface {
    int createRace(String name, String description) throws IllegalNameException, InvalidNameException;
    String viewRaceDetails(int raceId) throws IDNotRecognisedException;
    void removeRaceById(int raceId) throws IDNotRecognisedException;
    
    int addStageToRace(int raceId, String stageName, String description, double length, 
            LocalDateTime startTime, StageType type) throws IDNotRecognisedException, 
            IllegalNameException, InvalidNameException, InvalidLengthException;
    
    int[] getRaceStages(int raceId) throws IDNotRecognisedException;
    double getStageLength(int stageId) throws IDNotRecognisedException;
    void removeStageById(int stageId) throws IDNotRecognisedException;
    
    int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, 
            Double averageGradient, Double length) throws IDNotRecognisedException, 
            InvalidLocationException, InvalidStageStateException, InvalidStageTypeException;
    
    int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, 
            InvalidLocationException, InvalidStageStateException, InvalidStageTypeException;
    
    void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException;
    void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException;
    int[] getStageSegments(int stageId) throws IDNotRecognisedException;
    
    int createTeam(String name, String description) throws IllegalNameException, InvalidNameException;
    void removeTeam(int teamId) throws IDNotRecognisedException;
    int[] getTeams();
    int[] getTeamRiders(int teamId) throws IDNotRecognisedException;
    
    int createRider(int teamId, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException;
    void removeRider(int riderId) throws IDNotRecognisedException;
    
    void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) 
            throws IDNotRecognisedException, DuplicatedResultException, 
            InvalidCheckpointsException, InvalidStageStateException;
    
    LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException;
    LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException;
    void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException;
    
    int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException;
    LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException;
    int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException;
    int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException;
    
    void eraseCyclingPortal();
    void saveCyclingPortal(String filename) throws IOException;
    void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException;
} 