// Created by Melisa Lai Wan Jiun (99663) - Draft 1 (2025-06-01)
// Tested by Albertina Ngu Yan Si (98025), 2025-06-01

// LearningModuleInterface defines the contract for displaying lessons and the main menu in a health and safety learning module GUI.
// Any class implementing this interface must provide concrete behavior for:
/* showing a specific lesson (e.g., with text, image, or video content)
returning to or displaying the main menu that lists available lessons */
interface LearningModuleInterface {
    void showLesson(Lesson lesson);// Displays a specific lesson with its content, image, and video if available
    void showMainMenu(); // Displays the main menu with a list of available lessons
}
