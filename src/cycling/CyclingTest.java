package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.File;

public class CyclingTest {
    public static void main(String[] args) {
        try {
            // Create res directory if it doesn't exist
            File resDir = new File("res");
            if (!resDir.exists()) {
                resDir.mkdir();
            }

            CyclingPortal portal = new CyclingPortal();
            
            // Test race management
            int raceId = portal.createRace("Tour de France", "Famous cycling race");
            System.out.println("Created race with ID: " + raceId);
            
            // Test stage management
            int stageId = portal.addStageToRace(raceId, "Mountain Stage", "Tough mountain stage", 
                150.0, LocalDateTime.now(), StageType.HIGH_MOUNTAIN);
            System.out.println("Created stage with ID: " + stageId);
            
            // Test segment management
            int segmentId = portal.addCategorizedClimbToStage(stageId, 100.0, SegmentType.HC, 8.5, 20.0);
            System.out.println("Created climb segment with ID: " + segmentId);
            
            int sprintId = portal.addIntermediateSprintToStage(stageId, 75.0);
            System.out.println("Created sprint segment with ID: " + sprintId);
            
            // Test team management
            int teamId = portal.createTeam("Team Sky", "British professional team");
            System.out.println("Created team with ID: " + teamId);
            
            // Test rider management
            int riderId1 = portal.createRider(teamId, "Chris Froome", 1985);
            int riderId2 = portal.createRider(teamId, "Geraint Thomas", 1986);
            System.out.println("Created riders with IDs: " + riderId1 + ", " + riderId2);
            
            // Test stage preparation
            portal.concludeStagePreparation(stageId);
            System.out.println("Stage preparation concluded");
            
            // Test results registration
            LocalTime[] checkpoints1 = {
                LocalTime.of(10, 0), // start
                LocalTime.of(10, 30), // sprint
                LocalTime.of(11, 0), // climb
                LocalTime.of(11, 30)  // finish
            };
            portal.registerRiderResultsInStage(stageId, riderId1, checkpoints1);
            
            LocalTime[] checkpoints2 = {
                LocalTime.of(10, 0),
                LocalTime.of(10, 31),
                LocalTime.of(11, 2),
                LocalTime.of(11, 33)
            };
            portal.registerRiderResultsInStage(stageId, riderId2, checkpoints2);
            
            // Test rankings
            int[] riderRanks = portal.getRidersRankInStage(stageId);
            System.out.println("Stage rankings: " + arrayToString(riderRanks));
            
            // Save state to res directory
            portal.saveCyclingPortal("res/race_data.obj");
            System.out.println("Portal state saved successfully to res/race_data.obj");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
} 