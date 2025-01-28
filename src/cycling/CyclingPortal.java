package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

/**
 * Implementation of the CyclingPortalInterface for managing cycling races
 */
public class CyclingPortal implements CyclingPortalInterface, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Integer, Race> races;
    private Map<Integer, Team> teams;
    private Map<Integer, Rider> riders;
    private int nextRaceId = 0;
    private int nextTeamId = 0;
    private int nextRiderId = 0;
    private int nextStageId = 0;
    private int nextSegmentId = 0;
    
    /**
     * Constructor initializing empty maps and counters
     */
    public CyclingPortal() {
        races = new HashMap<>();
        teams = new HashMap<>();
        riders = new HashMap<>();
    }
    
    @Override
    public int createRace(String name, String description) 
            throws IllegalNameException, InvalidNameException {
        validateRaceName(name);
        int raceId = nextRaceId++;
        races.put(raceId, new Race(raceId, name, description));
        return raceId;
    }

    private void validateRaceName(String name) 
            throws InvalidNameException, IllegalNameException {
        if (name == null || name.trim().isEmpty() || name.length() > 30) {
            throw new InvalidNameException();
        }
        if (races.values().stream().anyMatch(r -> r.getName().equals(name))) {
            throw new IllegalNameException();
        }
    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        return String.format("Race ID: %d\nName: %s\nDescription: %s", 
            race.getRaceId(), race.getName(), race.getDescription());
    }

    private Race getRaceById(int raceId) throws IDNotRecognisedException {
        Race race = races.get(raceId);
        if (race == null) {
            throw new IDNotRecognisedException();
        }
        return race;
    }

    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        Race race = races.get(raceId);
        if (race == null) {
            throw new IDNotRecognisedException();
        }
        
        Map<Integer, Integer> totalPoints = new HashMap<>();
        race.getStages().stream()
            .flatMap(stage -> stage.getResults().stream())
            .forEach(result -> {
                totalPoints.merge(result.getRiderId(), result.getPoints(), Integer::sum);
            });
        
        return totalPoints.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .mapToInt(Map.Entry::getKey)
            .toArray();
    }

    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        Race race = races.get(raceId);
        if (race == null) {
            throw new IDNotRecognisedException();
        }
        
        Map<Integer, Integer> totalPoints = new HashMap<>();
        race.getStages().stream()
            .flatMap(stage -> stage.getResults().stream())
            .forEach(result -> {
                totalPoints.merge(result.getRiderId(), result.getMountainPoints(), Integer::sum);
            });
        
        return totalPoints.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .mapToInt(Map.Entry::getKey)
            .toArray();
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        if (!races.containsKey(raceId)) {
            throw new IDNotRecognisedException();
        }
        races.remove(raceId);
    }

    @Override
    public void removeRaceByName(String name) throws NameNotRecognisedException {
        Race race = races.values().stream()
            .filter(r -> r.getName().equals(name))
            .findFirst()
            .orElseThrow(NameNotRecognisedException::new);
        races.remove(race.getRaceId());
    }

    @Override
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        Race race = races.get(raceId);
        if (race == null) {
            throw new IDNotRecognisedException();
        }
        
        Map<Integer, Long> totalTimes = new HashMap<>();
        race.getStages().stream()
            .flatMap(stage -> stage.getResults().stream())
            .forEach(result -> {
                totalTimes.merge(result.getRiderId(), 
                    (long)result.getAdjustedElapsedTime().toSecondOfDay(), 
                    Long::sum);
            });
        
        return totalTimes.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .mapToInt(Map.Entry::getKey)
            .toArray();
    }

    @Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
        Race race = races.get(raceId);
        if (race == null) {
            throw new IDNotRecognisedException();
        }
        
        Map<Integer, Long> totalTimes = new HashMap<>();
        race.getStages().stream()
            .flatMap(stage -> stage.getResults().stream())
            .forEach(result -> {
                totalTimes.merge(result.getRiderId(), 
                    (long)result.getAdjustedElapsedTime().toSecondOfDay(), 
                    Long::sum);
            });
        
        return totalTimes.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .map(e -> LocalTime.ofSecondOfDay(e.getValue()))
            .toArray(LocalTime[]::new);
    }

    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
        Race race = races.get(raceId);
        if (race == null) {
            throw new IDNotRecognisedException();
        }
        
        Map<Integer, Integer> totalPoints = new HashMap<>();
        race.getStages().stream()
            .flatMap(stage -> stage.getResults().stream())
            .forEach(result -> {
                totalPoints.merge(result.getRiderId(), result.getPoints(), Integer::sum);
            });
        
        return totalPoints.values().stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, 
            double length, LocalDateTime startTime, StageType type)
            throws IDNotRecognisedException, IllegalNameException, 
            InvalidNameException, InvalidLengthException {
        
        Race race = races.get(raceId);
        if (race == null) {
            throw new IDNotRecognisedException();
        }
        
        if (stageName == null || stageName.trim().isEmpty() || stageName.length() > 30) {
            throw new InvalidNameException();
        }
        
        if (length < 5) {
            throw new InvalidLengthException();
        }
        
        if (race.getStages().stream().anyMatch(s -> s.getStageName().equals(stageName))) {
            throw new IllegalNameException();
        }
        
        int stageId = nextStageId++;
        Stage stage = new Stage(stageId, stageName, description, length, startTime, type);
        race.addStage(stage);
        return stageId;
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        Race race = races.get(raceId);
        if (race == null) {
            throw new IDNotRecognisedException();
        }
        return race.getStages().stream()
            .mapToInt(Stage::getStageId)
            .toArray();
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        return findStageById(stageId).getLength();
    }

    private Stage findStageById(int stageId) throws IDNotRecognisedException {
        return races.values().stream()
            .flatMap(race -> race.getStages().stream())
            .filter(stage -> stage.getStageId() == stageId)
            .findFirst()
            .orElseThrow(() -> new IDNotRecognisedException());
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        Stage stage = findStageById(stageId);
        races.values().forEach(race -> race.getStages().remove(stage));
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, 
            Double averageGradient, Double length) 
            throws IDNotRecognisedException, InvalidLocationException, 
            InvalidStageStateException, InvalidStageTypeException {
        
        Stage stage = findStageById(stageId);
        
        if (stage.getType() == StageType.TT) {
            throw new InvalidStageTypeException();
        }
        
        if (stage.getState() == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException();
        }
        
        if (location > stage.getLength()) {
            throw new InvalidLocationException();
        }
        
        int segmentId = nextSegmentId++;
        Segment segment = new Segment(segmentId, type, location, averageGradient, length);
        stage.addSegment(segment);
        return segmentId;
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) 
            throws IDNotRecognisedException, InvalidLocationException, 
            InvalidStageStateException, InvalidStageTypeException {
        
        Stage stage = findStageById(stageId);
        
        if (stage.getType() == StageType.TT) {
            throw new InvalidStageTypeException();
        }
        
        if (stage.getState() == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException();
        }
        
        if (location > stage.getLength()) {
            throw new InvalidLocationException();
        }
        
        int segmentId = nextSegmentId++;
        Segment segment = new Segment(segmentId, SegmentType.SPRINT, location, 0.0, 0.0);
        stage.addSegment(segment);
        return segmentId;
    }

    @Override
    public void removeSegment(int segmentId) 
            throws IDNotRecognisedException, InvalidStageStateException {
        Stage stage = races.values().stream()
            .flatMap(race -> race.getStages().stream())
            .filter(s -> s.getSegments().stream()
                .anyMatch(segment -> segment.getSegmentId() == segmentId))
            .findFirst()
            .orElseThrow(() -> new IDNotRecognisedException());
        
        if (stage.getState() == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException();
        }
        
        stage.getSegments().removeIf(segment -> segment.getSegmentId() == segmentId);
    }

    @Override
    public void concludeStagePreparation(int stageId) 
            throws IDNotRecognisedException, InvalidStageStateException {
        Stage stage = findStageById(stageId);
        
        if (stage.getState() == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException();
        }
        
        stage.setState(StageState.WAITING_FOR_RESULTS);
    }

    @Override
    public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
        Stage stage = findStageById(stageId);
        
        return stage.getSegments().stream()
            .sorted(Comparator.comparingDouble(Segment::getLocation))
            .mapToInt(Segment::getSegmentId)
            .toArray();
    }

    @Override
    public int createTeam(String name, String description) 
            throws IllegalNameException, InvalidNameException {
        if (name == null || name.trim().isEmpty() || name.length() > 30) {
            throw new InvalidNameException();
        }
        
        if (teams.values().stream().anyMatch(t -> t.getName().equals(name))) {
            throw new IllegalNameException();
        }
        
        int teamId = nextTeamId++;
        Team team = new Team(teamId, name, description);
        teams.put(teamId, team);
        return teamId;
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        if (!teams.containsKey(teamId)) {
            throw new IDNotRecognisedException();
        }
        teams.remove(teamId);
    }

    @Override
    public int[] getTeams() {
        return teams.keySet().stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        Team team = teams.get(teamId);
        if (team == null) {
            throw new IDNotRecognisedException();
        }
        return team.getRiders().stream().mapToInt(Rider::getRiderId).toArray();
    }

    @Override
    public void eraseCyclingPortal() {
        races.clear();
        teams.clear();
        riders.clear();
        nextRaceId = 0;
        nextTeamId = 0;
        nextRiderId = 0;
        nextStageId = 0;
        nextSegmentId = 0;
    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            CyclingPortal loaded = (CyclingPortal) ois.readObject();
            this.races = loaded.races;
            this.teams = loaded.teams;
            this.riders = loaded.riders;
            this.nextRaceId = loaded.nextRaceId;
            this.nextTeamId = loaded.nextTeamId;
            this.nextRiderId = loaded.nextRiderId;
            this.nextStageId = loaded.nextStageId;
            this.nextSegmentId = loaded.nextSegmentId;
        }
    }

    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
        Race race = races.get(raceId);
        if (race == null) {
            throw new IDNotRecognisedException();
        }
        
        Map<Integer, Integer> totalPoints = new HashMap<>();
        race.getStages().stream()
            .flatMap(stage -> stage.getResults().stream())
            .forEach(result -> {
                totalPoints.merge(result.getRiderId(), result.getMountainPoints(), Integer::sum);
            });
        
        return totalPoints.values().stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public int createRider(int teamId, String name, int yearOfBirth) 
            throws IDNotRecognisedException, IllegalArgumentException {
        // Validate team exists
        if (!teams.containsKey(teamId)) {
            throw new IDNotRecognisedException();
        }
        
        // Validate rider details
        if (name == null || yearOfBirth < 1900) {
            throw new IllegalArgumentException();
        }
        
        int riderId = nextRiderId++;
        Team team = teams.get(teamId);
        Rider rider = new Rider(riderId, name, yearOfBirth, team);
        riders.put(riderId, rider);
        team.addRider(rider);
        
        return riderId;
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage stage = findStageById(stageId);
        
        return stage.getResults().stream()
            .sorted(Comparator.comparing(RiderResult::getElapsedTime))
            .mapToInt(RiderResult::getMountainPoints)
            .toArray();
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage stage = findStageById(stageId);
        
        return stage.getResults().stream()
            .sorted(Comparator.comparing(RiderResult::getElapsedTime))
            .mapToInt(RiderResult::getPoints)
            .toArray();
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        Stage stage = findStageById(stageId);
        
        return stage.getResults().stream()
            .sorted(Comparator.comparing(RiderResult::getElapsedTime))
            .map(RiderResult::getAdjustedElapsedTime)
            .toArray(LocalTime[]::new);
    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        Stage stage = findStageById(stageId);
        
        return stage.getResults().stream()
            .sorted(Comparator.comparing(RiderResult::getElapsedTime))
            .mapToInt(RiderResult::getRiderId)
            .toArray();
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage stage = findStageById(stageId);
        if (!riders.containsKey(riderId)) {
            throw new IDNotRecognisedException();
        }
        
        stage.getResults().removeIf(result -> result.getRiderId() == riderId);
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage stage = findStageById(stageId);
        if (!riders.containsKey(riderId)) {
            throw new IDNotRecognisedException();
        }
        
        return stage.getResults().stream()
            .filter(result -> result.getRiderId() == riderId)
            .findFirst()
            .map(RiderResult::getAdjustedElapsedTime)
            .orElse(null);
    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage stage = findStageById(stageId);
        if (!riders.containsKey(riderId)) {
            throw new IDNotRecognisedException();
        }
        
        return stage.getResults().stream()
            .filter(result -> result.getRiderId() == riderId)
            .findFirst()
            .map(result -> {
                LocalTime[] times = result.getCheckpoints();
                LocalTime[] timesWithElapsed = Arrays.copyOf(times, times.length + 1);
                timesWithElapsed[times.length] = result.getElapsedTime();
                return timesWithElapsed;
            })
            .orElse(new LocalTime[0]);
    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) 
            throws IDNotRecognisedException, DuplicatedResultException, 
            InvalidCheckpointsException, InvalidStageStateException {
        
        Stage stage = findStageById(stageId);
        if (!riders.containsKey(riderId)) {
            throw new IDNotRecognisedException();
        }
        
        if (stage.getState() != StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException();
        }
        
        // Check for duplicate results
        if (stage.getResults().stream().anyMatch(r -> r.getRiderId() == riderId)) {
            throw new DuplicatedResultException();
        }
        
        // Validate checkpoints count (start + segments + finish)
        int expectedCheckpoints = stage.getSegments().size() + 2;
        if (checkpoints.length != expectedCheckpoints) {
            throw new InvalidCheckpointsException();
        }
        
        RiderResult result = new RiderResult(riderId, checkpoints);
        stage.addResult(result);
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        if (!riders.containsKey(riderId)) {
            throw new IDNotRecognisedException();
        }
        
        // Get the rider and their team
        Rider rider = riders.get(riderId);
        Team team = rider.getTeam();
        
        // Remove rider from team
        team.removeRider(rider);
        
        // Remove all results for this rider from all stages
        races.values().stream()
            .flatMap(race -> race.getStages().stream())
            .forEach(stage -> {
                stage.getResults().removeIf(result -> result.getRiderId() == riderId);
            });
        
        // Remove rider from riders map
        riders.remove(riderId);
    }

    // ... rest of the implementation
} 