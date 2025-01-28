package cycling;

import java.time.LocalTime;

public interface CyclingPortalInterface extends MiniCyclingPortalInterface {
    void removeRaceByName(String name) throws NameNotRecognisedException;
    int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException;
    LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException;
    int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException;
    int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException;
    int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException;
    int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException;
} 