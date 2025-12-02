// Contributed by Thian Shing Hui (101214) - Draft 1 (2025-05-25), Draft 2 (2025-06-08)
// Tested by Puteri Nur Liyana Imanina Abdullah (100804), 2025-06-13

// Represents a single entry in the leaderboard (containing a user username, points and badge)
public class LeaderboardEntry {
    private final String username;
    private final int points;
    private final String badge;
    
    // Constructs a LeaderboardEntry with given username, points and badge
    public LeaderboardEntry(String username, int points, String badge) {
        this.username = username;
        this.points = points;
        this.badge = badge;
    }
    
    // Getters for username, points and badge
    public String getUsername() { return username; }
    public int getPoints() { return points; }
    public String getBadge() { return badge; }
}