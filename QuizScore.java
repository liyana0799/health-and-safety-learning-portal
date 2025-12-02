// Created by Puteri Nur Liyana Imanina Abdullah (100804) - Draft 1 (2025-05-06)
// Created by Albertina Ngu Yan Si (98025) - Draft 2 (2025-05-14 )
// Tested by Thian Shing Hui (101214), 2025-06-01

import java.awt.Dimension;
import java.awt.Font;
import java.io.*;
import javax.swing.*;

/* The QuizScore class manages the saving and displaying of quiz scores.
 * It allows saving a score along with a motivational message and displaying the score history from a file
 */
public class QuizScore {

    //The file where quiz scores are saved
    private final String scoreFile = "Quiz_scores.txt";

    //Saves a user's quiz score to a file along with a motivational message based on the percentage
    public void saveScoreToFile(String username, int score, int totalQuestions) {

         //Calculate the percentage of the score
        int percentage = (int) (((double) score / totalQuestions) * 100);

         //Get the appropriate motivational message based on the score percentage
        String message = getMotivationalMessage(percentage);

        String messageContent = "Quiz completed!\nUser: " + username + "\nScore: " + score + " / " + totalQuestions;
        JOptionPane.showMessageDialog(null, messageContent, "Quiz Result", JOptionPane.INFORMATION_MESSAGE);

        try (FileWriter writer = new FileWriter(scoreFile, true)) {

            //Format the result string to save, including username, score, percentage, and message
            String result = "Username: " + username + " | Score: " + score + "/" + totalQuestions + " (" + percentage + "%) - " + message + "\n";

            //Write the result to the file
            writer.write(result);
        } catch (IOException e) {

            //Handle any file writing errors
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    /* Display the history of quiz scores saved in the file. It reads and prints
     * each line from the file containing a saved score
     */
    public void displayScoresFromFile(String username) {
        StringBuilder scoreHistory = new StringBuilder();
        scoreHistory.append("=== Score History for ").append(username).append("===\n\n");

        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line;
            boolean hasScore = false;

            //Read each line of the score file
            while ((line = reader.readLine()) != null) {

                if(line.startsWith("Username: " + username + " "))
                {
                    String cleanedLine = line.substring(("Username: " + username + " | ").length());
                    scoreHistory.append(cleanedLine).append("\n");
                    hasScore = true;
                }
            }

            //If no scores were found, it will display a message
            if (!hasScore) {
                scoreHistory.append("No scores found for this user.");
            }
        } catch (IOException e) {
            //Handle any file reading errors
            scoreHistory.append("Error reading scores: ").append(e.getMessage());
        }

        JTextArea textArea = new JTextArea(scoreHistory.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(280, 545));

        JOptionPane.showMessageDialog(null, scrollPane, "Quiz Score History", JOptionPane.INFORMATION_MESSAGE);
    }

    //return a motivational message based on the score percentage
    private String getMotivationalMessage(int percentage) {

        //return a message depending on the percentage achieved
        if (percentage >= 80) return "Outstanding!";
        else if (percentage >= 60) return "That's good!";
        else if (percentage >= 40) return "Good try!";
        else if (percentage >= 20) return "You can do better!";
        else return "Don't give up!";
    }
}