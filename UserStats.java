// Created by Thian Shing Hui (101214) - Draft 1 (2025-05-25), Draft 2 (2025-06-08)
// Tested by Puteri Nur Liyana Imanina Abdullah (100804), 2025-06-12


// Represents a user achievement-related statistics (including total points, badge earned and leaderboard rank)
public class UserStats {
    private final String username;
    private final int totalPoints;
    private final String badge;
    private final int rank;
    
    // Constructs a UserStats object with all required stats
    public UserStats(String username, int totalPoints, String badge, int rank) {
        this.username = username;
        this.totalPoints = totalPoints;
        this.badge = badge;
        this.rank = rank;
    }
    
    // Getter for username, total points, badge and leaderboard rank
    public String getUsername() { return username; }
    public int getTotalPoints() { return totalPoints; }
    public String getBadge() { return badge; }
    public int getRank() { return rank; }
    
    // Returns the user statistics as a String array (useful for display or exporting)
    public String[] toStringArray() {
        return new String[]{
            String.valueOf(totalPoints),
            badge,
            String.valueOf(rank)
        };
    }
}