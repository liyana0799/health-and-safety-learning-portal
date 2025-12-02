// Created by Thian Shing Hui (101214) - Draft 1 (2025-05-25), Draft 2(2025-06-08)
// Tested by Puteri Nur Liyana Imanina Abdullah (100804), 2025-06-13

// Handles the logic for calculating user achievements, saving gamification data and generating leaderboard rankings
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AchievementDataManager {
    private static final String QUIZ_SCORES_FILE = "Quiz_scores.txt";
    private static final String USER_ACHIEVEMENT_FILE = "UserAchievement.txt";
    
    // Badge priority used to sort users in leaderboard
    private static final Map<String, Integer> BADGE_PRIORITY = Map.of(
        "Quiz Master", 5,
        "Fast Learner", 4,
        "Intermediate Explorer", 3,
        "Rookie", 2,
        "Learning Starter", 1
    );
    
    // Calculates the total points, badge and leaderboard ranl for a given user (based on quiz scores)
    public UserStats calculateUserStats(String username) {
        int totalPoints = 0;
        int countQuizMaster = 0;
        int countFastLearner = 0;
        int countIntermediateExplorer = 0;
        int countRookie = 0;
        int countLearningStarter = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(QUIZ_SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: " + username + " ") && line.contains("/21 (")) {
                    int percentStart = line.indexOf("(") + 1;
                    int percentEnd = line.indexOf("%");
                    if (percentStart > 0 && percentEnd > percentStart) {
                        int percentage = Integer.parseInt(line.substring(percentStart, percentEnd));
                        // Award points and count badges based on quiz percentage
                        if (percentage >= 80) {
                            totalPoints += 100;
                            countQuizMaster++;
                        } else if (percentage >= 60) {
                            totalPoints += 75;
                            countFastLearner++;
                        } else if (percentage >= 40) {
                            totalPoints += 50;
                            countIntermediateExplorer++;
                        } else if (percentage >= 20) {
                            totalPoints += 25;
                            countRookie++;
                        } else {
                            totalPoints += 10;
                            countLearningStarter++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Will return default stats if file cannot be read
            return new UserStats(username, 0, "None", -1);
        }

        // Determine the highest badge earned
        String badge = determineBadge(countQuizMaster, countFastLearner, countIntermediateExplorer, 
                                    countRookie, countLearningStarter);
        
        // Save user achievement data to file
        saveUserGamificationData(username, totalPoints, badge);

        // Get the user's rank from the leaderboard
        int rank = getLeaderboardRank(username);
        
        return new UserStats(username, totalPoints, badge, rank);
    }
    
    // Determines the badge to award (based on number of times each category is earned)
    private String determineBadge(int countQuizMaster, int countFastLearner, int countIntermediateExplorer,
                                int countRookie, int countLearningStarter) {
        if (countQuizMaster >= 3) {
            return "Quiz Master";
        } else if (countFastLearner >= 1) {
            return "Fast Learner";
        } else if (countIntermediateExplorer >= 1) {
            return "Intermediate Explorer";
        } else if (countRookie >= 1) {
            return "Rookie";
        } else if (countLearningStarter >= 1) {
            return "Learning Starter";
        } else {
            return "None";
        }
    }
    
    // Saves or updates the user gamification data (points and badge) in a file
    public void saveUserGamificationData(String username, int points, String badge) {
        try {
            List<String> lines = new ArrayList<>();
            boolean found = false;
            
            // Read existing user data and update if user exists
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_ACHIEVEMENT_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Username: " + username + " ")) {
                        lines.add("Username: " + username + " Points: " + points + " Badge: " + badge);
                        found = true;
                    } else {
                        lines.add(line);
                    }
                }
            } catch (IOException ignored) {}
            
            // Add to the end of file if it is a new user
            if (!found) {
                lines.add("Username: " + username + " Points: " + points + " Badge: " + badge);
            }
            // Write back to the file
            java.nio.file.Files.write(java.nio.file.Paths.get(USER_ACHIEVEMENT_FILE), lines);
        } catch (IOException e) {
            System.out.println("Failed to save user achievement data.");
        }
    }
    
    // Returns the leaderboard rank of a specific user
    public int getLeaderboardRank(String username) {
        List<LeaderboardEntry> allUsers = loadAllUsers();
        allUsers.sort(this::compareLeaderboardEntries);

        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(username)) {
                return i + 1;
            }
        }
        return -1; // Not found
    }
    
    // Returns a sorted list of all leaderboard entries
    public List<LeaderboardEntry> getLeaderboardData() {
        List<LeaderboardEntry> allUsers = loadAllUsers();
        allUsers.sort(this::compareLeaderboardEntries);
        return allUsers;
    }
    
    // Loads all user achievement data from file and returns as a list of LeaderboardEntry
    private List<LeaderboardEntry> loadAllUsers() {
        List<LeaderboardEntry> allUsers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_ACHIEVEMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length < 6) continue; // Skip malformed lines
                String uname = parts[1];
                int pts = Integer.parseInt(parts[3]);
                String badge = line.substring(line.indexOf("Badge: ") + 7);
                allUsers.add(new LeaderboardEntry(uname, pts, badge));
            }
        } catch (IOException e) {
        // Return empty list if error
        }
        return allUsers;
    }
    
    // Compares two leaderboard entries by badge priority first, then points
    private int compareLeaderboardEntries(LeaderboardEntry a, LeaderboardEntry b) {
        int badgeDiff = BADGE_PRIORITY.getOrDefault(b.getBadge(), 0) - 
                       BADGE_PRIORITY.getOrDefault(a.getBadge(), 0);
        if (badgeDiff != 0) return badgeDiff;
        return Integer.compare(b.getPoints(), a.getPoints());
    }
}