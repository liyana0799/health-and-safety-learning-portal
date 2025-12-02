// Created by Thian Shing Hui (101214) - Draft 1 (2025-05-25)
// Tested by Puteri Nur Liyana Imanina Abdullah (100804), 2025-06-13

/**
 * Defines the contract for classes that handle
 * achievement-related functionality such as calculating statistics &
 * displaying rewards for users.
 */

public interface Achievement {
    String[] calculateStats(String username);
    void displayRewards();
}