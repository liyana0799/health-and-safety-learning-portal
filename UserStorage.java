// Created by Puteri Nur Liyana Imanina Abdullah (100804) - Draft 1 (2025-05-06)
// Tested by Melisa Lai Wan Jiun (99663), 2025-05-11

import java.io.*;
import java.util.*;

/* The UserStorage class handles all file-based operations related to user data.
 * It provides functionality to save, load, authenticate, and retrieve user information from a local file which is users.txt
 */

public class UserStorage {
    // The name of the file where user data is stored
    private final String userFile = "users.txt";

    // Save a new user to the file by appending their information in CSV format
    public void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write(user.getUserName() + "," + user.getEmail() + "," + user.getPassword());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    // Loads all users from the file and returns them as a list of User objects
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
           //Empty set
        }
        return users;
    }

    // Checks if a given username already exists in the storage
    public boolean isUsernameTaken(String username)
    {
        return loadUsers().stream().anyMatch(user -> user.getUserName().equalsIgnoreCase(username));
    }

    // Check if a given email is already used by a registered user
    public boolean isEmailTaken(String email)
    {
        return loadUsers().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    // Authenticates a user by checking if the given username and password match a stored user
    public User authenticate(String username, String password)
    {
        for (User user : loadUsers())
        {
            if (user.getUserName().equals(username) && user.getPassword().equals(password))
            {
                return user;
            }        
        }
        return null;
    }

    // Recovers a password based on a given email address
    public String recoverPasswordByEmail(String email)
    {
        for (User user: loadUsers())
        {
            if (user.getEmail().equalsIgnoreCase(email))
            {
                return user.getPassword();
            }
        }
        return null;
    }
    
    // Recovers a username based on a given email address
    public String recoverUsernameByEmail(String email)
    {
        for (User user: loadUsers())
        {
            if (user.getEmail().equalsIgnoreCase(email))
            {
                return user.getUserName();
            }
        }
        return null;
    }

    // Loads a user's username and password using their email
    public String[] loadUserByEmail(String email)
    {
        for(User user: loadUsers())
        {
            if (user.getEmail().equalsIgnoreCase(email))
            {
                return new String[] { user.getUserName(), user.getPassword()};
            }
        }
        return null;
    }

    // Finds and returns a User object based on the username
    public User findUserByUsername(String username)
    {
        for (User user: loadUsers())
        {
            if (user.getUserName().equalsIgnoreCase(username))
            {
                return user;
            }
        }
        return null;
    }
}