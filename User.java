// Created by Puteri Nur Liyana Imanina Abdullah (100804) - Draft 1 (2025-05-06), Draft 2 (2025-05-06)
// Tested by Melisa Lai Wan Jiun (99663), 2025-05-11

/* User class represents a user account in the Health & Safety Learning Portal.
 * This class implements the AuthService interface to provide authentication functionality
 * by including user registration and login validation.
 */
public class User implements AuthService 
{
    // Private fields to store user information
    private String userName;
    private String email;
    private String password;
    private UserStorage storage;
    
    // Default Constructor
    public User( )
    {
        this.storage = new UserStorage(); //Initialize storage in constructor
    }

    // Constructor with parameter to initialize a new User object
    public User(String userName, String email, String password)
    {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.storage = new UserStorage(); 
    }

    // Sign-up method to register a new user. It validates username, email. and password, then stores user information if it passes all conditions. */
    @Override
    public boolean signUp(String userName, String email, String password)
    {
        // Validate username format
        if(!isValidUsername(userName))
        {
            return false;
        }

        // Check if username already exists in storage
        if(storage.isUsernameTaken(userName))
        {
            return false;
        }
        
        // Validate email format
        if(!isValidEmail(email))
        {
            return false;
        }

        // Check if email is already registered
        if (storage.isEmailTaken(email))
        {
            return false;
        }

        // Validate password format
        if(!isValidPassword(password))
        {
            return false;
        }

        // ALL checks passed; set user data
        this.userName = userName;
        this.email = email;
        this.password = password;

        return true;
    }

     // Log-in method to authenticate a user. */
    @Override
     public boolean logIn(String inputUserName, String inputPassword)
     {
        return this.userName != null && this.userName.equals(inputUserName) && this.password.equals(inputPassword);
     }
 
    // Validates the email format: must end with @gmail.com, contain no spaces, and consist of letters and digits
    public boolean isValidEmail(String email)
    {
        // Check for null, proper Gmail domain and no spaces
        if (email == null || !email.endsWith("@gmail.com") || email.contains(" ")) return false;

        // Find the @ symbol position 
        int atIndex = email.indexOf("@");
        if (atIndex == -1 || atIndex == 0) return false;

        String localPart = email.substring(0, atIndex);
        for (char ch: localPart.toCharArray())
        {
            if (!Character.isLetterOrDigit(ch)) return false;
        }

        return true;
    }

    // Validates that the username only contains letters and digits.
    public boolean isValidUsername(String username)
    {
        // Check for null or empty username
        if (username == null || username.isEmpty()) return false;

        // Check each character in the username
        for (int i = 0; i < username.length(); i++)
        {
            char ch = username.charAt(i);
            if(!Character.isLetterOrDigit(ch))
            {
                return false;
            }
        }
        return true;
    }

    // Validates that the password contains only letters, digits, and at least one of the allowed special characters: @, !, &.
    public boolean isValidPassword(String password)
    {
        //Check for null or empty password
        if (password == null || password.isEmpty()) return false;

        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++)
        {
            char ch = password.charAt(i);

            if ((!Character.isLetterOrDigit(ch)))
            {
                if (ch == '@' || ch == '!' || ch == '&')
                {
                    hasSpecialChar = true;
                }
                else
                {
                    return false;
                }
            }
        }
        return hasSpecialChar;
    }

    // Getter for userName
    public String getUserName()
    {
        return this.userName;
    }

    // Getter for email
    public String getEmail()
    {
        return this.email;
    }

    // Getter for password
    public String getPassword()
    {
        return this.password;
    }
}
